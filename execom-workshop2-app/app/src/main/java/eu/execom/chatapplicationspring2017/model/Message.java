package eu.execom.chatapplicationspring2017.model;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

public class Message extends BaseModel implements Serializable,Comparable<Message> {

    private String text;

    private Date timestamp;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Message() {
    }

    public Message(String text) {
        this.text = text;
        this.timestamp = new Date();
    }

    public Message(String text, User user) {
        this.text = text;
        this.user = user;
        this.timestamp = new Date();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public int compareTo(@NonNull Message o) {
        return timestamp.compareTo(o.getTimestamp());
    }
}
