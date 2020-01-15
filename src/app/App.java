package app;

public class App {
    public static void main(String[] args) throws Exception {
        /** Create a New Server Instance */
        Server server = new Server();
        /** Put server in listen state for listening for new connections */
        server.acceptConnections();
    }
}