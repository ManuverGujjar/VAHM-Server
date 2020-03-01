package core;

import java.util.HashMap;

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



public class Server extends CoreServer {
    
    public void get(String url, Handler handler) {
        URLs.addMaping(url, handler);
    }

    public void post(String url, Handler handler) {
        URLs.addMaping(url, handler);
    }

    static class URLs {
        private static HashMap<String, Handler> urlMap = new HashMap<>();

        static void addMaping(String url, Handler handler) {
            urlMap.put(url, handler);
        }

        static void handleMaping(String url, Request req, Response res) {
            System.out.println(urlMap.keySet());
            if (urlMap.containsKey(url)) {
                
                urlMap.get(url).onRequestReceived(req, res);
                System.out.println(req.method + " " + req.path + " " + res.getStatus());
            }
        }
    }
}