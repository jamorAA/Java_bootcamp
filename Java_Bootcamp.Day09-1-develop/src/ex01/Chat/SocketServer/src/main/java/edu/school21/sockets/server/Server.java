package edu.school21.sockets.server;

import edu.school21.sockets.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.*;
import java.util.Objects;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

@Component
public class Server {
    private final UserService userService;
    private ServerSocket serverSocket;
    private static final List<ThreadConnection> connectionList = new LinkedList<>();

    @Autowired
    public Server(UserService userService) {
        this.userService = userService;
    }
    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        Thread thread = new Thread(this::readConsoleInput);
        thread.start();
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                ThreadConnection threadConnection =  new ThreadConnection(socket, userService);
                connectionList.add(threadConnection);
                threadConnection.start();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.exit(-1);
        }
    }
    private void readConsoleInput() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true) {
                String input = bufferedReader.readLine();
                if (input != null && input.equalsIgnoreCase("exit"))
                    refuseAll();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private synchronized void refuseAll() {
        try {
            for (ThreadConnection threadConnection : connectionList)
                threadConnection.refuseConnection();
            serverSocket.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.exit(-1);
        }
        System.exit(0);
    }
    static void sendMessageToChat(String message, String username, ThreadConnection currentConnection) throws IOException {
        Long chatId = currentConnection.getChatId();
        for (ThreadConnection threadConnection : connectionList) {
            if (!threadConnection.equals(currentConnection) && Objects.equals(threadConnection.getChatId(), chatId)) {
                threadConnection.sendMessageClient(username + ": " + message);
                threadConnection.sendMessageClient("> ");
            }
        }
    }
    static void removeConnection(ThreadConnection threadConnection) {
        connectionList.remove(threadConnection);
    }
}
