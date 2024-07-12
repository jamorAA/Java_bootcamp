package edu.school21.services;

import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import edu.school21.exceptions.AlreadyAuthenticatedException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

class EntityNotFoundException extends IllegalArgumentException {}

public class UsersServiceImpl implements UsersRepository {
    private static final String FIND_BY_LOGIN = "SELECT * FROM shop.user WHERE login=?";
    private static final String UPDATE = "UPDATE shop.user SET login=?, password=?, authentication=? WHERE user_id=?";
    private final Connection connection;

    public UsersServiceImpl(DataSource dataSource) throws SQLException {
        connection = dataSource.getConnection();
    }
    @Override
    public User findByLogin(String login) {
        try(PreparedStatement ps = connection.prepareStatement(FIND_BY_LOGIN)) {
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            if (!rs.next())
                throw new EntityNotFoundException();
            return new User(rs.getLong("user_id"), rs.getString("login"), rs.getString("password"), rs.getBoolean("authentication"));
        } catch(SQLException sqlException) {
            System.out.println(sqlException);
        }
        return null;
    }
    @Override
    public void update(User user) {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setBoolean(3, user.isAuthentication());
            ps.setLong(4, user.getUserId());
            ps.execute();
        } catch(SQLException sqlException) {
            System.out.println(sqlException);
        }
    }
    public boolean authenticate(String login, String password) {
        User user = findByLogin(login);
        if (user.getPassword().equals(password)) {
            if (user.isAuthentication())
                throw new AlreadyAuthenticatedException("Error");
            user.setAuthentication(true);
            update(user);
            return user.isAuthentication();
        }
        return false;
    }
}
