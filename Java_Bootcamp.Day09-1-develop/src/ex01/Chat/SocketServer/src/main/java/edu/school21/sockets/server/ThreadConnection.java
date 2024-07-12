package edu.school21.sockets.server;

import edu.school21.sockets.model.Message;
import edu.school21.sockets.model.User;
import edu.school21.sockets.service.UserService;
import lombok.EqualsAndHashCode;
import java.io.*;
import java.net.Socket;

@EqualsAndHashCode
public class ThreadConnection extends Thread {
    private final Socket socket;
    private final UserService userService;
    private final BufferedReader bufferedReader;
    private final BufferedWriter bufferedWriter;
    private String username;
    private boolean isFinished;
    private Long chatId;
    private Long userId;

    public ThreadConnection(Socket socket, UserService userService) throws IOException {
        this.socket = socket;
        this.userService = userService;
        bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
    }
    @Override
    public void run() {
        System.out.println("Client connected");
        try {
            while(!isFinished) {
                sendMessageClient("Hello from Server!");
                sendMessageClient("> ");
                getClientAction();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.exit(-1);
        }
    }
    private void getClientAction() throws IOException {
        String clientInput = bufferedReader.readLine();
        if (clientInput != null) {
            if (clientInput.equalsIgnoreCase("signUp")) {
                signUp();
            } else if (clientInput.equalsIgnoreCase("signIn")) {
                signIn();
            } else if (clientInput.equalsIgnoreCase("exit")) {
                refuseConnection();
            }
        }
    }
    private void startMessaging() {
        try {
            chatId = 1L;
            sendMessageClient("Start messaging");
            sendMessageClient("> ");
            String message;
            while (!(message = bufferedReader.readLine()).equalsIgnoreCase("exit")) {
                userService.saveMessage(message, userId);
                Server.sendMessageToChat(message, username, this);
                sendMessageClient("> ");
            }
            sendMessageClient("You have left the chat!");
            refuseConnection();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
    private void signUp() {
        try {
            sendMessageClient("Enter username:");
            sendMessageClient("> ");
            username = bufferedReader.readLine();
            sendMessageClient("Enter password:");
            sendMessageClient("> ");
            String password = bufferedReader.readLine();
            if ((username == null || username.isEmpty()) || (password == null || password.isEmpty())) {
                sendMessageClient("Wrong data for signUp");
            } else {
                if(!userService.signUp(username, password))
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
            username = bufferedReader.readLine();
            sendMessageClient("Enter password:");
            sendMessageClient("> ");
            String password = bufferedReader.readLine();
            if ((userId = userService.signIn(username, password)) != null)
                startMessaging();
            else
                sendMessageClient("Failed!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
    void sendMessageClient(String message) throws IOException {
        try {
            bufferedWriter.write(message);
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
