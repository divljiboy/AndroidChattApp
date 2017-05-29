package eu.execom.chatapplicationspring2017.dao;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.execom.chatapplicationspring2017.eventbus.OttoBus;
import eu.execom.chatapplicationspring2017.eventbus.event.ConversationsUpdatedEvent;
import eu.execom.chatapplicationspring2017.model.Conversation;

@EBean(scope = EBean.Scope.Singleton)
public class ConversationDao {

    static final String CONVERSATION_TAG = "conversations";

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private List<Conversation> conversations = new ArrayList<>();

    private Map<String, Conversation> conversationMap = new HashMap<>();

    @Bean
    OttoBus bus;

    /**
     * After this class is injected call this method.
     */
    @AfterInject
    public void init() {
        database.getReference(CONVERSATION_TAG).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                conversationMap = dataSnapshot.getValue(
                        new GenericTypeIndicator<Map<String, Conversation>>() {
                        });
                publish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void write(Conversation conversation) {
        final DatabaseReference databaseReference =
                database.getReference(CONVERSATION_TAG).push();

        conversation.setId(databaseReference.getKey());     // set unique key to our conversation
        databaseReference.setValue(conversation);           // push conversation to database
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    private void publish() {
        conversations.clear();
        if (conversationMap != null) {
            conversations.addAll(conversationMap.values());
        }

        // post to event bus
        bus.post(new ConversationsUpdatedEvent());
    }
}
