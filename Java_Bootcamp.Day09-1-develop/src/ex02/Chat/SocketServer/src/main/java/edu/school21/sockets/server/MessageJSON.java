package edu.school21.sockets.server;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MessageJSON {
    private String messageText;
    private LocalDateTime sendingTime;

    public MessageJSON(String messageText) {
        this.messageText = messageText;
        sendingTime = LocalDateTime.now();
    }
    public MessageJSON() {

    }
}
