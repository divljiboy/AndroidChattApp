package eu.execom.chatapplicationspring2017.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentByTag;
import org.androidannotations.annotations.ViewById;

import eu.execom.chatapplicationspring2017.R;
import eu.execom.chatapplicationspring2017.adapter.MessageAdapter;
import eu.execom.chatapplicationspring2017.fragment.CreateMessageFragment;
import eu.execom.chatapplicationspring2017.model.Conversation;

@EActivity(R.layout.activity_conversation)
public class ConversationActivity extends AppCompatActivity {

    @Extra
    Conversation conversation;

    @FragmentByTag
    CreateMessageFragment createMessageFragment;

    @Bean
    MessageAdapter messageAdapter;
    @ViewById
    RecyclerView recyclerView;

    @AfterViews
    void init() {
        createMessageFragment.initFor(conversation);

        initRecyclerView();
        recyclerView.setAdapter(messageAdapter);
        messageAdapter.initFor(conversation);
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));

    }

}
