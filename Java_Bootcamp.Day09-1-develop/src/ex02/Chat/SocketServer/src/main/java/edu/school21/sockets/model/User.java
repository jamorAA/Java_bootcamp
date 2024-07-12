package edu.school21.sockets.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class User {
    private Long id;
    private String login;
    private String password;

    public User() {

    }
}
