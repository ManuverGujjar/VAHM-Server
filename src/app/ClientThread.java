package app;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;

import http.RequestHandler;

class ClientThread extends Thread {
    Socket clientSocket;
    BufferedOutputStream outputStream;
    BufferedInputStream inputStream;

    ClientThread(Socket clientSocket) throws Exception {
        this.clientSocket = clientSocket;
        this.outputStream = new BufferedOutputStream(clientSocket.getOutputStream()); 
        this.inputStream = new BufferedInputStream(clientSocket.getInputStream());
    }

    @Override
    public void run() {
        try {
            /** Receive HTTP Request and then Send HTTP Response */
            receiveHttpRequest();
            // sendHttpResponse();

        } catch(Exception ex) {
            Log.println(ex.getMessage());
        }
    }

    public void receiveHttpRequest() throws Exception {
        String s = "";
        while (inputStream.available() > 0) {
            s = s + (char) inputStream.read();
        }
        System.out.println(s);
        RequestHandler requestHandler = new RequestHandler(s);
        requestHandler.parseRequest();
        Log.println("Done");
        outputStream.write(requestHandler.HTTPResponse().getBytes());
        outputStream.flush();
    }

    // public void sendHttpResponse() throws Exception {
    //     String time = LocalDateTime.now().toString();
        
    //     String htmlString = "<html> <body> <br><br> Server Time : " + time + " </body> </html>";
    //     String res = new String("HTTP/1.1 200 OK\r\nContent-Length: " + htmlString.length() + "\r\nContent-Type: text/html\r\n\n " + htmlString + "\r\n");
    //     outputStream.write(res.getBytes());

    //     outputStream.flush();
    // }
}