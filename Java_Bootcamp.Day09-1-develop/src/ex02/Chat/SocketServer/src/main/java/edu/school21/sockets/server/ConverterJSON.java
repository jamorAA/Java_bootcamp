package edu.school21.sockets.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ConverterJSON {
    private static final String TAG_MSG = "message";
    private static final String TAG_TIME = "time";

    public static MessageJSON parseStringToObject(String message) {
        MessageJSON newMessage = new MessageJSON(message);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            newMessage = objectMapper.readValue(message, MessageJSON.class);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return newMessage;
    }
    public static String makeJSON(String ms) {
        try {
            MessageJSON message = new MessageJSON(ms);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    private ConverterJSON() {

    }
}
