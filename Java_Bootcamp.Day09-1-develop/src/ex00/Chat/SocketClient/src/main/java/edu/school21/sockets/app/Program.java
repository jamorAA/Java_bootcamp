package edu.school21.sockets.app;

import edu.school21.sockets.client.Client;
import java.io.IOException;

public class Program {
    public static void main(String[] args) throws IOException {
        if (args.length != 1 || !args[0].startsWith("--port=")) {
            System.err.println("Provide port using '--port='");
            System.exit(-1);
        }
        int port = Integer.parseInt(args[0].substring("--port=".length()));
        Client client = new Client();
        client.start(port);
    }
}