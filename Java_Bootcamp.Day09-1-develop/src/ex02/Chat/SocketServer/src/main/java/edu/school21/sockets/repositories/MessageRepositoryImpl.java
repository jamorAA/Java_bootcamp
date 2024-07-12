package edu.school21.sockets.repositories;

import edu.school21.sockets.model.Message;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;

@Component
public class MessageRepositoryImpl implements MessageRepository {
    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public MessageRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        createTable();
    }
    private void createTable() {
        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS Chat.Message (
                    id SERIAL PRIMARY KEY,
                    sender INT NOT NULL,
                    room INT NOT NULL,
                    messageText TEXT NOT NULL,
                    sendingTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                );""");
    }
    @Override
    public Message findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM chat.message WHERE id=?", new Object[]{id}, new int[]{Types.INTEGER}, new BeanPropertyRowMapper<>(Message.class)).stream().findAny().orElse(null);
    }
    @Override
    public List<Message> findAll() {
        return jdbcTemplate.query("SELECT * FROM chat.message", new BeanPropertyRowMapper<>(Message.class));
    }
    @Override
    public void save(Message entity) {
        int result = jdbcTemplate.update("INSERT INTO chat.message(sender, messageText, room) VALUES(?, ?, ?)", entity.getSender(), entity.getMessageText(), entity.getRoom());
        if (result == 0)
            System.err.println("Entity can't save");
    }
    @Override
    public void update(Message entity) {
        if (entity == null || entity.getId() == null)
            System.err.println("Entity is null");
        int result = jdbcTemplate.update("UPDATE chat.message SET sender = ?, messageText = ?, sendingTime = ?, room = ? WHERE id = ?", entity.getSender(), entity.getMessageText(), entity.getSendingTime(), entity.getRoom(), entity.getId());
        if (result == 0)
            System.err.println("Entity can't update");
    }
    @Override
    public void delete(Long id) {
        int result = jdbcTemplate.update("DELETE FROM chat.message WHERE id = ?", id);
        if (result == 0)
            System.err.println("Entity can't delete");
    }
    @Override
    public List<Message> loadMessages(Long chatId) {
        return jdbcTemplate.query("SELECT * FROM chat.message WHERE room = ? LIMIT 30", new Object[]{chatId}, new int[]{Types.INTEGER}, new BeanPropertyRowMapper<>(Message.class));
    }
}
