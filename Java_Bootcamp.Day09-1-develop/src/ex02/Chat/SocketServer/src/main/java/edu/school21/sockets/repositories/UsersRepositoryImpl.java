package edu.school21.sockets.repositories;

import edu.school21.sockets.model.User;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

@Component
public class UsersRepositoryImpl implements UsersRepository {
    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public UsersRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        createTable();
    }
    private void createTable() {
        jdbcTemplate.execute(
                """
                        CREATE SCHEMA IF NOT EXISTS Chat;

                        CREATE TABLE IF NOT EXISTS Chat.User (
                            id SERIAL PRIMARY KEY,
                            login VARCHAR(30) NOT NULL UNIQUE,
                            password VARCHAR(100) NOT NULL
                        );""");
    }
    @Override
    public User findById(Long id) {
        return jdbcTemplate.query("SELECT * FROM chat.user WHERE id=?", new Object[]{id}, new int[]{Types.INTEGER}, new BeanPropertyRowMapper<>(User.class)).stream().findAny().orElse(null);
    }
    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM chat.user", new BeanPropertyRowMapper<>(User.class));
    }
    @Override
    public void save(User entity) {
        int result = jdbcTemplate.update("INSERT INTO chat.user(login, password) VALUES(?, ?)", entity.getLogin(), entity.getPassword());
        if (result == 0)
            System.err.println("Entity can't save");
    }
    @Override
    public void update(User entity) {
        if (entity == null || entity.getId() == null)
            System.err.println("Entity is null");
        int result = jdbcTemplate.update("UPDATE chat.user SET login = ?, password = ? WHERE id = ?", entity.getLogin(), entity.getPassword(), entity.getId());
        if (result == 0)
            System.err.println("Entity can't update");
    }
    @Override
    public void delete(Long id) {
        int result = jdbcTemplate.update("DELETE FROM chat.user WHERE id = ?", id);
        if (result == 0)
            System.err.println("Entity can't delete");
    }
    @Override
    public Optional<User> findByLogin(String login) {
        if (login == null || login.isEmpty())
            return Optional.empty();
        return Optional.ofNullable(jdbcTemplate.query("SELECT * FROM chat.user WHERE login=?", new Object[]{login}, new int[]{Types.VARCHAR}, new BeanPropertyRowMapper<>(User.class)).stream().findAny().orElse(null));
    }
}
