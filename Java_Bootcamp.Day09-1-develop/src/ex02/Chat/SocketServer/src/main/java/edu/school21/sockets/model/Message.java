package edu.school21.sockets.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@ToString
public class Message {
    private Long id;
    private Long sender;
    private Long room;
    private String messageText;
    private LocalDateTime sendingTime;

    public Message(String messageText, Long sender, Long room) {
        this.messageText = messageText;
        this.sender = sender;
        this.room = room;
    }
    public Message() {

    }
}
