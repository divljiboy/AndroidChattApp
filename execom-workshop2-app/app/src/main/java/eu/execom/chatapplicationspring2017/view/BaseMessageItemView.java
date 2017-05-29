package eu.execom.chatapplicationspring2017.view;


import android.content.Context;
import android.text.format.DateFormat;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import eu.execom.chatapplicationspring2017.R;
import eu.execom.chatapplicationspring2017.model.Message;

import static eu.execom.chatapplicationspring2017.R.id.messageText;

@EViewGroup
public class BaseMessageItemView extends RelativeLayout {
    @ViewById(R.id.messageUser)
    TextView mesageUser;

    @ViewById(messageText)
    TextView mesageText;

    @ViewById(R.id.messageTime)
    TextView mesageTime;

    @ViewById(R.id.userPhoto)
    SimpleDraweeView userPhoto;


    public BaseMessageItemView bind (Message message){
        mesageText.setText(message.getText());
        mesageTime.setText(DateFormat.format("HH:mm dd MMMM",message.getTimestamp()));
        mesageUser.setText(message.getUser().getUsername());
        userPhoto.setImageURI(message.getUser().getPhotoUrl());
        return this;
    }


    public BaseMessageItemView(Context context) {
        super(context);

    }
}
