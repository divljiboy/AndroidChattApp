package eu.execom.chatapplicationspring2017.fragment;


import android.support.v4.app.Fragment;
import android.widget.EditText;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import eu.execom.chatapplicationspring2017.R;
import eu.execom.chatapplicationspring2017.dao.MessageDao;
import eu.execom.chatapplicationspring2017.dao.UserDao;
import eu.execom.chatapplicationspring2017.model.Conversation;
import eu.execom.chatapplicationspring2017.model.Message;

@EFragment(R.layout.fragment_create_message)
public class CreateMessageFragment extends Fragment {
    @Bean
    UserDao userDao;


    public CreateMessageFragment() {
    }

    @Bean
    MessageDao messageDao;

    @ViewById
    EditText messageText;

    public void initFor(Conversation conversation) {
        messageDao.initFor(conversation);
    }

    /**
     * Called when button with id=sendMessage is clicked.
     */
    @Click
    void sendMessage() {
        final String text = messageText.getText().toString();
        if (text.isEmpty()) {
            return;
        }

        final Message message = new Message(text,userDao.getCurrentUser());
        messageDao.write(message);
        messageText.setText("");
    }

}
