package app;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    ServerSocket mainServer;

    Server() {
        try {
            mainServer = new ServerSocket(ServerDetails.PORT);
        } catch(Exception ex) {
            
        }
    }

    void acceptConnections() throws Exception {
        Socket clientSocket = mainServer.accept();
        ClientThread clientThread = new ClientThread(clientSocket);
        clientThread.start();
    }
}