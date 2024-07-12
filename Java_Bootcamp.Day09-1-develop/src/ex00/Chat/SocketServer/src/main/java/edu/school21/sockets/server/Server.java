package edu.school21.sockets.server;

import edu.school21.sockets.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

@Component
public class Server {
    private final UserService userService;
    private ServerSocket serverSocket;
    private final List<ThreadConnection> connectionList = new LinkedList<>();

    @Autowired
    public Server(UserService userService) {
        this.userService = userService;
    }
    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        Thread consoleThread = new Thread(this::readConsoleInput);
        consoleThread.start();
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                ThreadConnection connection = new ThreadConnection(socket, userService);
                connectionList.add(connection);
                connection.start();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.exit(-1);
        }
    }
    private void readConsoleInput() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true) {
                String input = reader.readLine();
                if (input != null && input.equalsIgnoreCase("exit"))
                    refuseAll();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private synchronized void refuseAll() {
        try {
            for (ThreadConnection tc : connectionList) {
                tc.refuseConnection();
            }
            serverSocket.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.exit(-1);
        }
        System.exit(0);
    }
}
