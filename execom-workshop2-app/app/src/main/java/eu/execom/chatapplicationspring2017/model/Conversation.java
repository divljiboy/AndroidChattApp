package eu.execom.chatapplicationspring2017.model;

import java.io.Serializable;
import java.util.Map;

public class Conversation extends BaseModel implements Serializable {

    private String title;

    private Map<String, Message> messages;

    public Conversation() {
    }

    public Conversation(String id, String title) {
        super(id);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, Message> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "title='" + title + '\'' +
                ", messages=" + messages +
                '}';
    }
}
