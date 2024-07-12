package edu.school21.sockets.service;

import edu.school21.sockets.model.ChatRoom;
import edu.school21.sockets.repositories.ChatRoomsRepository;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;

@Component
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomsRepository chatRoomsRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ChatRoomServiceImpl(ChatRoomsRepository chatRoomsRepository, PasswordEncoder passwordEncoder) {
        this.chatRoomsRepository = chatRoomsRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public boolean saveRoom(String name, Long ownerId) {
        ChatRoom chatRoom = new ChatRoom(name, ownerId);
        if (chatRoomsRepository.findByName(chatRoom.getChatName()).isPresent())
            return false;
        chatRoomsRepository.save(chatRoom);
        return true;
    }
    @Override
    public boolean saveRoom(String name, String password, Long ownerId) {
        ChatRoom chatRoom = new ChatRoom(name, ownerId, password);
        chatRoom.setPassword(passwordEncoder.encode(chatRoom.getPassword()));
        chatRoomsRepository.save(chatRoom);
        return true;
    }
    @Override
    public boolean signIn(Long id, String password) {
        ChatRoom chatRoom = chatRoomsRepository.findById(id);
        return passwordEncoder.matches(password, chatRoom.getPassword());
    }
    @Override
    public List<ChatRoom> findAll() {
        return chatRoomsRepository.findAll();
    }
}
