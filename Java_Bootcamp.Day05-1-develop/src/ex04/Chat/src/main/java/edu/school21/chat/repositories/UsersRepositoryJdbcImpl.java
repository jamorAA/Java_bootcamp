package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private final DataSource dataSource;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Override
    public List<User> findAll(int page, int size) {
        List<User> list = new ArrayList<>();
        int offset = page * size;
        String query = "SELECT u1.*, c1.chatroom_id, c1.name, uc.chatroom_id, c2.name, u2.user_id, u2.login, u2.password\n" +
                "FROM (SELECT * FROM chat.user LIMIT ? OFFSET ?) u1\n" +
                "LEFT JOIN chat.chatroom c1 ON u1.user_id = c1.owner\n" +
                "LEFT JOIN chat.user_chatroom uc ON u1.user_id = uc.user_id\n" +
                "LEFT JOIN chat.chatroom c2 ON uc.chatroom_id = c2.chatroom_id\n" +
                "LEFT JOIN chat.user u2 ON c2.owner = u2.user_id\n" +
                "ORDER BY u1.user_id, c1.chatroom_id, uc.chatroom_id;";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
             ps.setLong(1, size);
             ps.setLong(2, offset);
             ResultSet rs = ps.executeQuery();

             while (rs.next()) {
                 final Long userId, createdChatId, socializeChatId;
                 User user;
                 Chatroom chatroom;
                 userId = rs.getLong(1);
                 if (list.stream().noneMatch(u -> userId.equals(u.getUserId()))) {
                     user = new User(userId, rs.getString(2), rs.getString(3), new ArrayList<>(), new ArrayList<>());
                     list.add(user);
                 } else {
                     user = list.stream().filter(u -> userId.equals(u.getUserId())).collect(Collectors.toList()).get(0);
                 }
                 createdChatId = rs.getLong(4);
                 if (createdChatId != 0 && user.getCreatedRooms().stream().noneMatch(c -> createdChatId.equals(c.getChatroomId()))) {
                     chatroom = new Chatroom(createdChatId, rs.getString(5), null, null);
                     user.getCreatedRooms().add(chatroom);
                 }
                 socializeChatId = rs.getLong(6);
                 if (socializeChatId != 0 && user.getSocializeRooms().stream().noneMatch(c -> socializeChatId.equals(c.getChatroomId()))) {
                     chatroom = new Chatroom(socializeChatId, rs.getString(7), null, null);
                     user.getSocializeRooms().add(chatroom);
                 }
             }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
}
