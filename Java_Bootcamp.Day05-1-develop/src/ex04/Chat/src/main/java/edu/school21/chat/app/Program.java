package edu.school21.chat.app;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.UsersRepository;
import edu.school21.chat.repositories.UsersRepositoryJdbcImpl;
import java.util.List;

public class Program {
    public static void main ( String[] args ) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");
        UsersRepository repository = new UsersRepositoryJdbcImpl(dataSource);

        List<User> list;
        list = repository.findAll(0, 2);
        for (User user : list)
            System.out.println(user.toString());
    }
}
