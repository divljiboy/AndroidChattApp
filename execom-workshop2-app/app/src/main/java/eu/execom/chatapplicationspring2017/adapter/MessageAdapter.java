package eu.execom.chatapplicationspring2017.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import eu.execom.chatapplicationspring2017.dao.MessageDao;
import eu.execom.chatapplicationspring2017.dao.UserDao;
import eu.execom.chatapplicationspring2017.eventbus.OttoBus;
import eu.execom.chatapplicationspring2017.eventbus.event.MessagesUpdatedEvent;
import eu.execom.chatapplicationspring2017.generic.RecyclerViewAdapterBase;
import eu.execom.chatapplicationspring2017.generic.ViewWrapper;
import eu.execom.chatapplicationspring2017.model.Conversation;
import eu.execom.chatapplicationspring2017.model.Message;
import eu.execom.chatapplicationspring2017.model.User;
import eu.execom.chatapplicationspring2017.view.BaseMessageItemView;
import eu.execom.chatapplicationspring2017.view.IncomminMessageItemView_;
import eu.execom.chatapplicationspring2017.view.OutgoingMessageItemView;
import eu.execom.chatapplicationspring2017.view.OutgoingMessageItemView_;

/**
 * Created by Home on 5/20/2017.
 */

@EBean
public class MessageAdapter extends RecyclerViewAdapterBase<Message,BaseMessageItemView> {

    public static final int INCOMMING_MESSAGE=0;
    public static final int OUTGOING_MESSAGE=1;
    @Bean
    UserDao userDao;
    @Bean
    MessageDao messageDao;
    @RootContext
    Context context;
    @Bean
    OttoBus bus;


    @Override
    protected BaseMessageItemView onCreateItemView(ViewGroup parent, int viewType) {
        switch (viewType){
            case OUTGOING_MESSAGE:
                return OutgoingMessageItemView_.build(context);
            case INCOMMING_MESSAGE:
                return IncomminMessageItemView_.build(context);
            default:
                throw new IllegalArgumentException("View type not supported");
        }

    }

    @Override
    public void onBindViewHolder(ViewWrapper<BaseMessageItemView> holder, int position) {
        Message message=items.get(position);
        holder.getView().bind(message);
    }

    @Subscribe
    public void updateMessages(MessagesUpdatedEvent e){
        items=messageDao.getMessages();
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        final Message message=items.get(position);
        return message.getUser().getId().equals(userDao.getCurrentUser().getId())?OUTGOING_MESSAGE:INCOMMING_MESSAGE;
    }
    public void initFor(Conversation conversation){
        messageDao.initFor(conversation);
        bus.register(this);
    }
}
