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

                response = new Response(socket.getOutputStream());
                request = new Request(socket.getInputStream());

                URLs.handleMaping(request.path.strip(), request, response);
                
            } catch(Exception e) {
                response.setStatus(404).send("url not found on server");
                e.printStackTrace();
            }
        }
    }
}

