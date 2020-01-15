package app;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    ServerSocket mainServer;
    
    Server() throws Exception {
        /** Creates a new Server socket on PORT specified in ServerDetails class */
        this.mainServer = new ServerSocket(ServerDetails.PORT);
    }

    void acceptConnections() throws Exception {
        /** Put ServerSocket in listening state by calling accept method whenever new client is connected
             * Create a new ClientThread 
            */
        while (true) {
            Socket clientSocket = mainServer.accept();
            Log.println("[+] new connection from " + clientSocket.getInetAddress());
            ClientThread clientThread = new ClientThread(clientSocket);
            clientThread.start();
        }
    }
}