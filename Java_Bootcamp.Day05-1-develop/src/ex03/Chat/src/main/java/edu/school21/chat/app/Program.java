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
        dataSource.setJdbcUrl("jdbc:postgresql://localhost:2222/postgres");
        dataSource.setUsername("ehtelfos");
        dataSource.setPassword("postgres");
        MessagesRepository repository = new MessagesRepositoryJdbcImpl(dataSource);

        User user = new User(1L, "user", "user", new ArrayList<>(), new ArrayList<>());
        Chatroom chatroom = new Chatroom(1L, "chatroom", user, new ArrayList<>());
        System.out.println(Timestamp.valueOf(LocalDateTime.now()));
        Message message = new Message(1L , user, chatroom, "Bye", Timestamp.valueOf(LocalDateTime.now()).toLocalDateTime());
        repository.update(message);
    }
}
