package edu.school21.chat.repositories;

import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.User;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;

import java.util.ArrayList;
import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private final DataSource dataSource;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Message message) {
        checkMessageValidity(message);

        Long userId = message.getAuthor().getUserId();
        Long roomId = message.getRoom().getChatroomId();
        String localDateTime = message.getTime() != null ? "'" + Timestamp.valueOf(message.getTime()) + "'" : "'null'";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet userResultSet = statement.executeQuery("SELECT * FROM Chat.User WHERE user_id = " + userId);
            if (!userResultSet.next())
                throw new NotSavedSubEntityException("Error");
            ResultSet chatroomResultSet = statement.executeQuery("SELECT * FROM Chat.Chatroom WHERE chatroom_id = " + roomId);
            if (!chatroomResultSet.next())
                throw new NotSavedSubEntityException("Error");
            ResultSet messageResultSet = statement.executeQuery("INSERT INTO chat.message (author, room, text, time) VALUES (" + userId + ", " + roomId + ", '" + message.getText() + "', " + localDateTime + ") RETURNING message_id");
            if (!messageResultSet.next())
                throw new NotSavedSubEntityException("Error");
            message.setMessageId(messageResultSet.getLong(1));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void checkMessageValidity(Message message) {
        User author = message.getAuthor();
        Chatroom room = message.getRoom();

        if (author == null || author.getUserId() == null)
            throw new NotSavedSubEntityException("Error");
        if (room == null || room.getChatroomId() == null)
            throw new NotSavedSubEntityException("Error");
        User owner = room.getOwner();
        if (owner == null || owner.getUserId() == null)
            throw new NotSavedSubEntityException("Error");
        String text = message.getText();
        if (text == null || text.isEmpty())
            throw new NotSavedSubEntityException("Error");
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
