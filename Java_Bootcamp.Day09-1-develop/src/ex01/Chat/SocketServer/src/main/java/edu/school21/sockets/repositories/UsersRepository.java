package edu.school21.sockets.repositories;

import edu.school21.sockets.model.User;
import java.util.Optional;

public interface UsersRepository extends CrudRepository<User> {
    Optional<User> findByLogin(String email);
}
