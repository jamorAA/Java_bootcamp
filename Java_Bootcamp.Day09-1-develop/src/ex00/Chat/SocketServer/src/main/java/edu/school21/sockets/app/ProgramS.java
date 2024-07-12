package edu.school21.sockets.app;

import edu.school21.sockets.server.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.io.*;

public class ProgramS {
    public static void main(String[] args) throws IOException {
        if (args.length != 1 || !args[0].startsWith("--server-port=")) {
            System.err.println("Provide port using '--server-port='");
            System.exit(-1);
        }
        int port = Integer.parseInt(args[0].substring("--server-port=".length()));
        ApplicationContext ac = new AnnotationConfigApplicationContext("edu.school21.sockets");
        Server server = ac.getBean(Server.class);
        server.start(port);
    }
}
