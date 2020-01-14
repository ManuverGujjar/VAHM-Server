package app;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

class ClientThread extends Thread {
    Log log;
    Socket clientSocket;
    OutputStream outputStream;
    InputStream inputStream;

    ClientThread(Socket clientSocket) {
        log = new Log("ClientThread.java");
        try {

            this.clientSocket = clientSocket;
            this.outputStream = clientSocket.getOutputStream();
            this.inputStream = clientSocket.getInputStream();

        } catch(Exception ex) {
            log.log(ex.getMessage(), 20);
        }
    }

    @Override
    public void run() {
        try {
            this.logHttpRequest();
            this.sendHttpResponse();

        } catch(Exception ex) {
            log.log(ex.getMessage(), 33);
        }
    }

    public void logHttpRequest() throws Exception {
        String req = new String(inputStream.readAllBytes());
        log.log(req);
    }

    public void sendHttpResponse() throws Exception {
        String res = new String("HTTP/1.1 200 OK\r\nContent-Type: text/html\r\nContent-Length: 5\r\n\nHello\n\n");
        outputStream.write(res.getBytes());
        outputStream.flush();
    }
}