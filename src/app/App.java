package app;

public class App {
    public static void main(String[] args) throws Exception {
        Server server = new Server();
        server.acceptConnections();
    }
}

