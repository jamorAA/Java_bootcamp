package edu.school21.sockets.repositories;

import edu.school21.sockets.model.ChatRoom;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Component
public class ChatRoomsRepositoryImpl implements ChatRoomsRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ChatRoomsRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        createTable();
    }
    private void createTable() {
        jdbcTemplate.execute("""
                CREATE SCHEMA IF NOT EXISTS Chat;
                CREATE TABLE IF NOT EXISTS Chat.ChatRoom (
                    id SERIAL PRIMARY KEY,
                    chatName VARCHAR(70) NOT NULL UNIQUE,
                    chatOwner INT NOT NULL,
                    password VARCHAR(100) DEFAULT NULL
                );""");
    }
    @Override
    public ChatRoom findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM chat.chatroom WHERE id=?", new Object[]{id}, new int[]{Types.INTEGER}, new BeanPropertyRowMapper<>(ChatRoom.class)).stream().findAny().orElse(null);
    }
    @Override
    public List<ChatRoom> findAll() {
        return jdbcTemplate.query("SELECT * FROM chat.chatroom", new BeanPropertyRowMapper<>(ChatRoom.class));
    }
    @Override
    public void save(ChatRoom entity) {
        int result = jdbcTemplate.update("INSERT INTO chat.chatroom(chatName, chatOwner, password) VALUES(?, ?, ?)", entity.getChatName(), entity.getChatOwner(), entity.getPassword());
        if (result == 0)
            System.err.println("Entity can't save");
    }
    @Override
    public void update(ChatRoom entity) {
        if (entity == null || entity.getId() == null)
            System.err.println("Entity is null");
        int result = jdbcTemplate.update("UPDATE chat.chatroom SET chatName = ?, chatOwner = ?, password = ? WHERE id = ?", entity.getChatName(), entity.getChatOwner(), entity.getPassword(), entity.getId());
        if (result == 0)
            System.err.println("Entity can't update");
    }
    @Override
    public void delete(Long id) {
        int result = jdbcTemplate.update("DELETE FROM chat.chatroom WHERE id = ?", id);
        if (result == 0)
            System.err.println("Entity can't delete");
    }
    @Override
    public Optional<ChatRoom> findByName(String name) {
        if (name == null || name.isEmpty())
            return Optional.empty();
        return Optional.ofNullable(jdbcTemplate.query("SELECT * FROM chat.chatroom WHERE chatname=?", new Object[]{name}, new int[]{Types.VARCHAR}, new BeanPropertyRowMapper<>(ChatRoom.class)).stream().findAny().orElse(null));
    }
}
