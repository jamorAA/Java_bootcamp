package edu.school21.models;

import java.util.Objects;

public class User {
    private Long user_id;
    private String login;
    private String password;
    private boolean authentication;

    public User(Long user_id, String login, String password, boolean authentication) {
        this.user_id = user_id;
        this.login = login;
        this.password = password;
        this.authentication = authentication;
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
    public boolean isAuthentication() {
        return authentication;
    }
    public void setAuthentication(boolean authentication) {
        this.authentication = authentication;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return authentication == user.authentication && Objects.equals(user_id, user.user_id) && Objects.equals(login, user.login) && Objects.equals(password, user.password);
    }
    @Override
    public int hashCode() {
        return Objects.hash(user_id, login, password, authentication);
    }
    @Override
    public String toString() {
        return "User: {" + "user_id=" + user_id + ", Login='" + login + '\'' + ", password='" + password + '\'' + ", authentication=" + authentication + '}';
    }
}
