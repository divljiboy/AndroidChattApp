package eu.execom.chatapplicationspring2017.view;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import eu.execom.chatapplicationspring2017.R;
import eu.execom.chatapplicationspring2017.model.Conversation;

@EViewGroup(R.layout.item_view_conversation)
public class ConversationItemView extends LinearLayout {

    @ViewById
    TextView title;

    public ConversationItemView(Context context) {
        super(context);
    }

    public void bind(Conversation conversation) {
        title.setText(conversation.getTitle());
    }
}
