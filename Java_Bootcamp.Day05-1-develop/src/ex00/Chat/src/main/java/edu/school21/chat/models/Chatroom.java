package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class Chatroom {
    private Long chatroom_id;
    private String name;
    private User owner;
    private List<Message> messages;

    public Chatroom(Long chatroom_id, String name, User owner, List<Message> messages) {
        this.chatroom_id = chatroom_id;
        this.name = name;
        this.owner = owner;
        this.messages = messages;
    }
    public Long getChatroomId() {
        return chatroom_id;
    }
    public void setChatroomId(Long chatroom_id) {
        this.chatroom_id = chatroom_id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }
    public List<Message> getMessages() {
        return messages;
    }
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Chatroom chatRoom = (Chatroom) o;
        return Objects.equals(chatroom_id, chatRoom.chatroom_id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(chatroom_id);
    }
    @Override
    public String toString() {
        return "Chatroom {" + "chatroom_id=" + chatroom_id + ", name='" + name + '\'' + ", owner='" + owner + '\'' + ", messages=" + messages + '}';
    }
}
