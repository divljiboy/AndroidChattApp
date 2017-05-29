package eu.execom.chatapplicationspring2017.dao;


import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.Map;

import eu.execom.chatapplicationspring2017.eventbus.OttoBus;
import eu.execom.chatapplicationspring2017.eventbus.event.UsersLoadedEvent;
import eu.execom.chatapplicationspring2017.model.User;


@EBean(scope = EBean.Scope.Singleton)
public class UserDao {

    private static final String USERS_TAG = "users";

    private FirebaseDatabase db = FirebaseDatabase.getInstance();

    private Map<String, User> users;

    private User currentUser;

    @Bean
    OttoBus bus;

    public void init() {
        final DatabaseReference usersRef = db.getReference(USERS_TAG);
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users = dataSnapshot.getValue(new GenericTypeIndicator<Map<String, User>>() {
                });
                bus.post(new UsersLoadedEvent());
                Log.d("users loaded", "users loaded");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("users cancelled", "users cancelled");
            }
        });
    }

    public void write(User user) {
       db.getReference(USERS_TAG).child(user.getId()).setValue(user);

       }

    public boolean userExists(String userId) {
        return users != null && users.containsKey(userId);
    }

    public User getUserById(String userId) {
        if (users == null || users.isEmpty()) {
            return null;
        }
        return users.get(userId);
    }

    public User getUserByUsername(String username) {
        if (users == null || users.isEmpty()) {
            return null;
        }
        for (User user : users.values()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        return null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
