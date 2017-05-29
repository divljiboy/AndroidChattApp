package eu.execom.chatapplicationspring2017.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

import eu.execom.chatapplicationspring2017.eventbus.OttoBus;

/**
 * Created by Home on 5/20/2017.
 */

@EActivity
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bus.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }

    @Bean
    OttoBus bus;

}

