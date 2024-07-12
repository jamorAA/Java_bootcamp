package edu.school21.chat.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Message {
    private Long message_id;
    private User author;
    private Chatroom room;
    private String text;
    private LocalDateTime time;

    public Message(Long message_id, User author, Chatroom room, String text, LocalDateTime time) {
        this.message_id = message_id;
        this.author = author;
        this.room = room;
        this.text = text;
        this.time = time;
    }
    public Long getMessageId() {
        return message_id;
    }
    public void setMessageId(Long message_id) {
        this.message_id = message_id;
    }
    public User getAuthor() {
        return author;
    }
    public void setAuthor(User author) {
        this.author = author;
    }
    public Chatroom getRoom() {
        return room;
    }
    public void setRoom(Chatroom room) {
        this.room = room;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public LocalDateTime getTime() {
        return time;
    }
    public void setTime(LocalDateTime time) {
        this.time = time;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Message message = (Message) o;
        return Objects.equals(message_id, message.message_id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(message_id);
    }
    @Override
    public String toString() {
        return "Message{" + "message_id=" + message_id + ", author='" + author + '\'' + ", room=" + room + ", text='" + text + '\'' + ", time=" + time + '}';
    }
}
