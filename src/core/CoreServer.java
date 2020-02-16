package core;

import java.net.ServerSocket;
import java.net.Socket;

class CoreServer {

    private ServerSocket mainServer;
    private int port;
    private boolean running;
    private CoreHelper helper;

    public CoreServer() {
        helper = new CoreHelper();
    }

    public void listen(int port) {
        listen(this.port, null);
    }

    public void listen(int port, Callback callback) {
        this.running = true;
        this.port = port;
        
        try {
            mainServer = new ServerSocket(this.port);
            
            if (callback != null) {
                callback.callback(null);
            }

            while (running) {
                Socket socket = mainServer.accept();
                helper.handle(socket);
            }

        } catch(Exception exception) {
            if (callback != null) {
                callback.callback(exception);
            }
        }
    }

    public void stop(Callback callback) {
        this.running = false;
        try {
            this.mainServer.close();
        } catch (Exception e) {
            callback.callback(e);
        }
    }
}