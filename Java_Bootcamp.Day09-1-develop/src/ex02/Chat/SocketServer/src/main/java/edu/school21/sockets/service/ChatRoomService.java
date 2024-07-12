package edu.school21.sockets.service;

import edu.school21.sockets.model.ChatRoom;
import java.util.List;

public interface ChatRoomService {
    boolean saveRoom(String name, Long ownerId);
    boolean saveRoom(String name, String password, Long ownerId);
    boolean signIn(Long id, String password);
    List<ChatRoom> findAll();
}
