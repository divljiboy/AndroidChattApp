package eu.execom.chatapplicationspring2017.dao;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.execom.chatapplicationspring2017.eventbus.OttoBus;
import eu.execom.chatapplicationspring2017.eventbus.event.MessagesUpdatedEvent;
import eu.execom.chatapplicationspring2017.model.Conversation;
import eu.execom.chatapplicationspring2017.model.Message;

@EBean
public class MessageDao {

    private static final String MESSAGE_TAG = "messages";

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();

    private Conversation conversation;
    private Map<String,Message> messageMap= new HashMap<String, Message>();
    private List<Message> messages= new ArrayList<>();
    @Bean
    OttoBus bus;


    public void initFor(Conversation conversation) {
        this.conversation = conversation;

        database.getReference(ConversationDao.CONVERSATION_TAG + "/" +
                conversation.getId()).child(MESSAGE_TAG).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               messageMap=dataSnapshot.getValue(new GenericTypeIndicator<Map<String, Message>>() {
               });
                publish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void write(Message message) {
        final DatabaseReference reference =
                database.getReference(ConversationDao.CONVERSATION_TAG)
                        .child(conversation.getId())
                        .child(MESSAGE_TAG).push();

        message.setId(reference.getKey());      // get unique key for new message
        reference.setValue(message);            // push message to database
    }
    private void publish(){
        if(messageMap==null)
            return;
        messages=new ArrayList<>(messageMap.values());
        Collections.sort(messages);
       bus.post(new MessagesUpdatedEvent());

    }

    public List<Message> getMessages() {
        return messages;
    }
}
