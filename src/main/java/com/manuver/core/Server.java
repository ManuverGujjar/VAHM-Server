package com.manuver.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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

    public void listen(int port, Server.Callback callback) {
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

    public void stop(Server.Callback callback) {
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

    public void serveStaticDir(String path) {
        URLs.setStaticDir(path);
    }

    static class URLs {
        private static HashMap<String, Handler> urlMap = new HashMap<>();
        private static String staticDir;

        static void addMaping(String url, Handler handler) {
            urlMap.put(url, handler);
        }

        static void handleMaping(String url, Request req, Response res) {
            if (!staticDir.isEmpty()) {
                try {
                    File file = new File(staticDir, url);
                    if (file.exists()) {
                        InputStream inputStream = new FileInputStream(file);
                        res.setContentType("*/*").send(inputStream.readAllBytes());
                        inputStream.close();
                        System.out.println("Served");
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            if (urlMap.containsKey(url)) {    
                urlMap.get(url).onRequestReceived(req, res);
                System.out.println(req.method + " " + req.path + " " + res.getStatus());
            }
        }

        static void setStaticDir(String path) {
            staticDir = path;
        }
    }

    public static interface Callback {
        public void callback(Exception e);
    }
}