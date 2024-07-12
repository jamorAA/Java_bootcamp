package edu.school21.chat.app;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.util.ArrayList;
import java.time.LocalDateTime;
import edu.school21.chat.models.User;
import edu.school21.chat.models.Chatroom;
import java.sql.*;

public class Program {
    public static void main ( String[] args ) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");

        User author = new User(1L, "user", "user", new ArrayList<>(), new ArrayList<>());
        Chatroom room = new Chatroom(1L, "room", author, new ArrayList<>());
        Message message = new Message(null, author, room, "Hello!", Timestamp.valueOf(LocalDateTime.now()).toLocalDateTime());
        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(dataSource);
        messagesRepository.save(message);
        System.out.println(message.getMessageId());
    }
}
