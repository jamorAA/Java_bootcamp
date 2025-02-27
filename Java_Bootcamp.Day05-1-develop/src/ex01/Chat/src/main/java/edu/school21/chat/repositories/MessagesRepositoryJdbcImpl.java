package edu.school21.chat.repositories;

import edu.school21.chat.models.User;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private final DataSource dataSource;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) {
        String mQuery = "SELECT * FROM Chat.Message WHERE message_id = " + id;

        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
             ResultSet rs = st.executeQuery(mQuery);

             if (!rs.next())
                 return Optional.empty();

             Long userId = rs.getLong(2);
             Long roomId = rs.getLong(3);
             User user = findUser(userId);
             Chatroom room = findChatroom(roomId);
             return Optional.of(new Message(rs.getLong(1), user, room,
                     rs.getString(4), rs.getTimestamp(5).toLocalDateTime()));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    private User findUser(Long id) throws SQLException {
        String uQuery = "SELECT * FROM Chat.User WHERE user_id = " + id;

        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
             ResultSet rs = st.executeQuery(uQuery);
             if (!rs.next())
                 return null;

             return new User(id, rs.getString(2), rs.getString(3), new ArrayList<>(), new ArrayList<>());
        }
    }

    private Chatroom findChatroom(Long id) throws SQLException {
        String cQuery = "SELECT * FROM Chat.Chatroom WHERE chatroom_id = " + id;

        try (Connection con = dataSource.getConnection();
             Statement st = con.createStatement()) {
             ResultSet rs = st.executeQuery(cQuery);
             if (!rs.next())
                 return null;

             return new Chatroom(id, rs.getString(2));
        }
    }
}
