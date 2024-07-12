package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class User {
    private Long user_id;
    private String login;
    private String password;
    private List<Chatroom> created_rooms;
    private List<Chatroom> socialize_rooms;

    public User(Long user_id, String login, String password, List<Chatroom> created_rooms, List<Chatroom> socialize_rooms) {
        this.user_id = user_id;
        this.login = login;
        this.password = password;
        this.created_rooms = created_rooms;
        this.socialize_rooms = socialize_rooms;
    }
    public Long getUserId() {
        return user_id;
    }
    public void setUserId(Long user_id) {
        this.user_id = user_id;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public List<Chatroom> getCreatedRooms() {
        return created_rooms;
    }
    public void setCreatedRooms(List<Chatroom> created_rooms) {
        this.created_rooms = created_rooms;
    }
    public List<Chatroom> getSocializeRooms() {
        return socialize_rooms;
    }
    public void setSocializeRooms(List<Chatroom> socialize_rooms) {
        this.socialize_rooms = socialize_rooms;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return Objects.equals(user_id, user.user_id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(user_id);
    }
    @Override
    public String toString() {
        return "User {" + "user_id=" + user_id + ", login='" + login + '\'' + ", password='" + password + '\'' + ", created_rooms=" + created_rooms + ", socialize_rooms=" + socialize_rooms + '}';
    }
}
