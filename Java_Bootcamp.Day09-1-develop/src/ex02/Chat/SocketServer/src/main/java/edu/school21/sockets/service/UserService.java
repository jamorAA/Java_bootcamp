package edu.school21.sockets.service;

import edu.school21.sockets.model.Message;
import java.util.List;

public interface UserService {
    boolean signUp(String username, String password);
    Long signIn(String username, String password);
    void saveMessage(String message, Long userId, Long chatId);
    List<Message> loadMessages(Long chatId);
    String getUsername(Long userId);
}
