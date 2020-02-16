package core;

import java.util.HashMap;

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