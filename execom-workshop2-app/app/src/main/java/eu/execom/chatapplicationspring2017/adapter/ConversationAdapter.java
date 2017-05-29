package eu.execom.chatapplicationspring2017.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

import eu.execom.chatapplicationspring2017.dao.ConversationDao;
import eu.execom.chatapplicationspring2017.eventbus.OttoBus;
import eu.execom.chatapplicationspring2017.eventbus.event.ConversationsUpdatedEvent;
import eu.execom.chatapplicationspring2017.model.Conversation;
import eu.execom.chatapplicationspring2017.view.ConversationItemView;
import eu.execom.chatapplicationspring2017.view.ConversationItemView_;

@EBean
public class ConversationAdapter extends BaseAdapter {

    private List<Conversation> conversations = new ArrayList<>();

    @RootContext
    Context context;

    @Bean
    ConversationDao conversationDao;

    @Bean
    OttoBus bus;

    /**
     * This method is called after this class is injected.
     */
    public void resetConversationFlow(){
        conversationDao.init();
    }
    @AfterInject
    void init() {
        bus.register(this);
    }

    @Override
    public int getCount() {
        return conversations.size();
    }

    @Override
    public Conversation getItem(int position) {
        return conversations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ConversationItemView conversationItemView;

        if (convertView == null) {  // if view item is not created
            conversationItemView = ConversationItemView_.build(context);
        } else {    // if view item was already created
            conversationItemView = (ConversationItemView) convertView;
        }

        // bind data to view
        conversationItemView.bind(getItem(position));

        return conversationItemView;
    }

    private void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;

        // notify that data set changed so that the list is refreshed
        notifyDataSetChanged();
    }

    @Subscribe
    public void conversationsUpdated(ConversationsUpdatedEvent event) {
        setConversations(conversationDao.getConversations());
    }
}
