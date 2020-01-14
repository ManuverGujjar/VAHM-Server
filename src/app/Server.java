package app;

import java.net.ServerSocket;

public class Server {

    ServerSocket mainServer;

    Server() {
        try {
            mainServer = new ServerSocket(ServerDetails.PORT);
        } catch(Exception ex) {
            
        }
    }
}