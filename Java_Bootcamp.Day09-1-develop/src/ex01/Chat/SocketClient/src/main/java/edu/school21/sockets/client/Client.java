package edu.school21.sockets.client;

import java.util.Scanner;
import java.net.Socket;
import java.io.*;

public class Client {
    private Scanner reader;
    private Scanner bufferedReader;
    private BufferedWriter bufferedWriter;
    private boolean active = true;

    public void start(int port) {
        try {
            Socket socket = new Socket("localhost", port);
            reader = new Scanner(System.in);
            bufferedReader = new Scanner(socket.getInputStream());
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            Thread threadRead = new ReadMessage();
            Thread threadWrite = new WriteMessage();
            threadRead.start();
            threadWrite.start();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.exit(-1);
        }
    }
    private class ReadMessage extends Thread {
        @Override
        public void run() {
            try {
                getMessageFromServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        private void getMessageFromServer() throws IOException {
            while (bufferedReader.hasNextLine()) {
                String serverMessage = bufferedReader.nextLine();
                if (serverMessage.equals("> "))
                    System.out.print(serverMessage);
                else
                    System.out.println(serverMessage);
                if (serverMessage.equals("You have left the chat!"))
                    break;
            }
        }
    }
    private class WriteMessage extends Thread {
        @Override
        public void run() {
            try {
                sendMessageToServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        private void sendMessageToServer() throws IOException {
            while (active) {
                try {
                    String message = reader.nextLine();
                    bufferedWriter.write(message);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    if (message.equalsIgnoreCase("exit"))
                        active = false;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
