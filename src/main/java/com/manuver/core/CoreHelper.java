package com.manuver.core;


import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.manuver.core.Server.URLs;

class CoreHelper {
    private List<HelperThread> helperThreads;

    CoreHelper() {
        helperThreads = new ArrayList<>();
    }

    void handle(Socket socket) {
        // System.out.println("Free : " + Runtime.getRuntime().freeMemory()/8000000.0);
        // System.out.println("Max Memory: " + Runtime.getRuntime().maxMemory()/8000000.0);
        // System.out.println(Runtime.getRuntime().);
        HelperThread thread = new HelperThread(socket);
        thread.start();
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

