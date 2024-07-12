package edu.school21.sockets.repositories;

import edu.school21.sockets.model.Message;
import java.util.List;

public interface MessageRepository extends CrudRepository<Message> {
    List<Message> loadMessages(Long chatId);
}
