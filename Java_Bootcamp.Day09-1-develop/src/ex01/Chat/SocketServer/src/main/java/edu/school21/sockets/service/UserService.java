package edu.school21.sockets.service;

import edu.school21.sockets.model.Message;
import edu.school21.sockets.model.User;

public interface UserService {
    boolean signUp(String username, String password);
    Long signIn(String username, String password);
    void saveMessage(String message, Long userId);
}
