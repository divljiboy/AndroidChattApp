package eu.execom.chatapplicationspring2017.view;

import android.content.Context;

import org.androidannotations.annotations.EViewGroup;

import eu.execom.chatapplicationspring2017.R;

/**
 * Created by Home on 5/20/2017.
 */

@EViewGroup(R.layout.item_view_message_incoming)
public class IncomminMessageItemView extends BaseMessageItemView {
    public IncomminMessageItemView(Context context) {
        super(context);
    }

}
