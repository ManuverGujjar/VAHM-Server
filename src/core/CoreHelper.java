package core;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
        HelperThread(Socket socket) {
            this.socket = socket;
        }
        
        @Override
        public void start() {
            
        }
    }
}

