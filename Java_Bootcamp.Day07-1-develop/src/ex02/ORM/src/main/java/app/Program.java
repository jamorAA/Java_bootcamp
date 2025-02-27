package app;

import com.zaxxer.hikari.HikariDataSource;
import models.User;
import orm.OrmManager;

import java.sql.SQLException;

public class Program {
    public static void main(String[] args) {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        ds.setUsername("postgres");
        ds.setPassword("postgres");
        try {
            OrmManager manager = new OrmManager(ds.getConnection());
            manager.createTable();
            User user1 = new User(null, "kevin", "took", 21);
            manager.save(user1);
            User user2 = new User(1, "big", "L", 44);
            manager.update(user2);
            User user3 = manager.findById(1L, User.class);
            System.out.println(user3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
