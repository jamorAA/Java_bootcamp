package edu.school21.sockets.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@ToString
public class Message {
    private Long id;
    private Long sender;
    private String messageText;
    private LocalDateTime sendingTime;

    public Message(String messageText, Long sender) {
        this.messageText = messageText;
        this.sender = sender;
    }
    public Message() {

    }
}
