package edu.school21.sockets.server;

import edu.school21.sockets.model.ChatRoom;
import edu.school21.sockets.model.Message;
import edu.school21.sockets.service.ChatRoomService;
import edu.school21.sockets.service.UserService;
import lombok.EqualsAndHashCode;
import java.io.*;
import java.net.Socket;
import java.util.List;

@EqualsAndHashCode
public class ThreadConnection extends Thread {
    private final Socket socket;
    private final UserService userService;
    private final ChatRoomService chatRoomService;
    private final BufferedReader bufferedReader;
    private final BufferedWriter bufferedWriter;
    private String username;
    private boolean isFinished;
    private Long chatId;
    private Long userId;

    public ThreadConnection(Socket socket, UserService userService, ChatRoomService chatRoomService) throws IOException {
        this.socket = socket;
        this.userService = userService;
        this.chatRoomService = chatRoomService;
        bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
    }
    @Override
    public void run() {
        System.out.println("Client connected");
        try {
            while(!isFinished) {
                serverMenu();
                getClientAction();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.exit(-1);
        }
    }
    private void serverMenu() throws IOException {
        sendMessageClient("Hello from Server!");
        sendMessageClient("1. SignIn");
        sendMessageClient("2. SignUp");
        sendMessageClient("3. Exit");
        sendMessageClient("> ");
    }
    private void getClientAction() throws IOException {
        String input = ConverterJSON.parseStringToObject(bufferedReader.readLine()).getMessageText();
        if (input != null) {
            switch (input) {
                case "2" -> signUp();
                case "1" -> signIn();
                case "3" -> refuseConnection();
            }
        }
    }
    private void startMessaging() {
        try {
            sendMessageClient("Start messaging:");
            List<Message> messages = userService.loadMessages(chatId);
            for (Message message : messages) {
                String username = userService.getUsername(message.getSender());
                sendMessageClient(username + ": " + message.getMessageText());
            }
            String message;
            sendMessageClient("> ");
            while (!(message = ConverterJSON.parseStringToObject(bufferedReader.readLine()).getMessageText()).equalsIgnoreCase("exit")) {
                userService.saveMessage(message, userId, chatId);
                Server.sendMessageToChat(message, username, this);
                sendMessageClient("> ");
            }
            sendMessageClient("You have left the chat!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
    private void signUp() {
        try {
            sendMessageClient("Enter username:");
            sendMessageClient("> ");
            username = ConverterJSON.parseStringToObject(bufferedReader.readLine()).getMessageText();
            sendMessageClient("Enter password:");
            sendMessageClient("> ");
            String password = ConverterJSON.parseStringToObject(bufferedReader.readLine()).getMessageText();
            if ((username == null || username.isEmpty()) || (password == null || password.isEmpty())) {
                sendMessageClient("Wrong data for signUp");
            } else {
                if (!userService.signUp(username, password))
                    sendMessageClient("Failed! User with this login already exists");
                else
                    sendMessageClient("Successful!");
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
    private void signIn() {
        try {
            sendMessageClient("Enter username:");
            sendMessageClient("> ");
            username = ConverterJSON.parseStringToObject(bufferedReader.readLine()).getMessageText();
            sendMessageClient("Enter password:");
            sendMessageClient("> ");
            String password = ConverterJSON.parseStringToObject(bufferedReader.readLine()).getMessageText();
            if ((userId = userService.signIn(username, password)) != null)
                roomMenu();
            else
                sendMessageClient("Failed!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
    private void roomMenu() throws IOException {
        sendMessageClient("1. Create room");
        sendMessageClient("2. Choose room");
        sendMessageClient("3. Exit");
        sendMessageClient("> ");
        String input = ConverterJSON.parseStringToObject(bufferedReader.readLine()).getMessageText();
        if (input != null) {
            switch (input) {
                case "1" -> createRoom();
                case "2" -> chooseRoomMenu();
                case "3" -> sendMessageClient("You have left the chat menu!");
            }
        }
    }
    private void createRoom() {
        try {
            sendMessageClient("Enter room name:");
            String roomName = ConverterJSON.parseStringToObject(bufferedReader.readLine()).getMessageText();
            if (roomName == null || roomName.isEmpty()) {
                sendMessageClient("Wrong data for creating room");
                return;
            }
            sendMessageClient("Do you want to create a password for your room? Yes(y)/No(n)?");
            String input = ConverterJSON.parseStringToObject(bufferedReader.readLine()).getMessageText();
            if (input.equalsIgnoreCase("Yes") || input.equalsIgnoreCase("y")) {
                sendMessageClient("Enter room password:");
                String password = ConverterJSON.parseStringToObject(bufferedReader.readLine()).getMessageText();
                if (password == null) {
                    sendMessageClient("Wrong password!");
                } else {
                    if (chatRoomService.saveRoom(roomName, password, userId)) {
                        sendMessageClient("Successful!");
                        chooseRoomMenu();
                    } else {
                        sendMessageClient("Failed! Chat with this name already exists");
                    }
                }
            } else {
                if (chatRoomService.saveRoom(roomName, userId)) {
                    sendMessageClient("Successful!");
                    chooseRoomMenu();
                } else {
                    sendMessageClient("Failed! Chat with this name already exists");
                }
            }
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }
    private void chooseRoomMenu() {
        try {
            int count = 0;
            List<ChatRoom> chatRoomList = chatRoomService.findAll();
            if (chatRoomList == null) {
                sendMessageClient("There are no any rooms!");
            } else {
                sendMessageClient("Rooms:");
                for (ChatRoom room : chatRoomList)
                    sendMessageClient(++count + ". " + room.getChatName());
                sendMessageClient(++count + ". Back");
                sendMessageClient("> ");
                int room = chooseRoom();
                if (room < 0 || room > count) {
                    sendMessageClient("Choose correct room");
                } else {
                    if (room == count) {
                        sendMessageClient("You have left the chat menu!");
                    } else {
                        ChatRoom chatRoom = chatRoomList.get(room - 1);
                        if (chatRoom.getPassword() != null) {
                            sendMessageClient("Enter password for chat:");
                            String password = ConverterJSON.parseStringToObject(bufferedReader.readLine()).getMessageText();
                            if (chatRoomService.signIn(chatRoom.getId(), password)) {
                                sendMessageClient("Successful!");
                                startChatRoom((long) room, chatRoom);
                            } else {
                                sendMessageClient("Failed!");
                            }
                        } else {
                            startChatRoom((long) room, chatRoom);
                        }
                    }
                }
            }
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }
    private void startChatRoom(long room, ChatRoom chatRoom) throws IOException {
        chatId = chatRoom.getId();
        sendMessageClient(chatRoom.getChatName() + " --- ");
        startMessaging();
    }
    private int chooseRoom() {
        try {
            try {
                return Integer.parseInt(ConverterJSON.parseStringToObject(bufferedReader.readLine()).getMessageText());
            } catch (NumberFormatException exception) {
                System.out.println(exception.getMessage());
            }
        } catch (IOException ioException) {
            System.out.println(ioException);
        }
        return -1;
    }
    void sendMessageClient(String message) throws IOException {
        try {
            String jsonMessage = ConverterJSON.makeJSON(message);
            bufferedWriter.write(jsonMessage);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void refuseConnection() {
        try {
            Server.removeConnection(this);
            bufferedReader.close();
            bufferedWriter.close();
            socket.close();
        } catch(IOException ioException) {
            ioException.printStackTrace();
            System.exit(-1);
        }
        isFinished = true;
    }
    public Long getChatId() {
        return chatId;
    }
}
