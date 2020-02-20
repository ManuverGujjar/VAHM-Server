package core;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import core.Server.URLs;

class CoreHelper {
    private List<HelperThread> helperThreads;

    CoreHelper() {
        helperThreads = new ArrayList<>();
    }

    void handle(Socket socket) {
        HelperThread thread = new HelperThread(socket);
        thread.start();
        helperThreads.add(thread);
    }

    private class HelperThread extends Thread {

        private Socket socket;
        private Request request;
        private Response response;

        HelperThread(Socket socket) {
            this.socket = socket;
            
        }
        
        @Override
        public void start() {
            try {
                
                request = new Request(socket.getInputStream());
                response = new Response(socket.getOutputStream());

                URLs.handleMaping(request.path.strip(), request, response);
                
                // socket.getOutputStream().write(new String("HTTP/1.1 200 OK\r\nContent-Length: 5\r\nConnection: close\r\n\nHello").getBytes());
            } catch(Exception e) {
                response.setStatus(404).send("url not found on server");
                e.printStackTrace();
            }
        }
    }
}

