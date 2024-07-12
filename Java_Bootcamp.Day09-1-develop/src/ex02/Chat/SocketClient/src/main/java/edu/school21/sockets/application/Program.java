package edu.school21.sockets.application;

import edu.school21.sockets.client.Client;

public class Program {
    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--port=")) {
            System.err.println("Provide port using '--port='");
            System.exit(-1);
        }
        int port = Integer.parseInt(args[0].substring("--port=".length()));
        Client client = new Client();
        client.start(port);
    }
}
