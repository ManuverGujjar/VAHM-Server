package app;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

class ClientThread extends Thread {
    Socket clientSocket;
    DataOutputStream outputStream;
    DataInputStream inputStream;

    ClientThread(Socket clientSocket) throws Exception {
        this.clientSocket = clientSocket;
        this.outputStream = new DataOutputStream(clientSocket.getOutputStream()); 
        this.inputStream = new DataInputStream(clientSocket.getInputStream());
    }

    @Override
    public void run() {
        try {
            /** Receive HTTP Request and then Send HTTP Response */
            receiveHttpRequest();
            sendHttpResponse();

        } catch(Exception ex) {
            Log.println(ex.getMessage());
        }
    }

    public void receiveHttpRequest() throws Exception {
        String req = new String(inputStream.readAllBytes());
        Log.println(req);
    }

    public void sendHttpResponse() throws Exception {
        String htmlString = "<html> Hello World </html>";
        String res = new String("HTTP/1.1 200 OK\r\nContent-Length: " + htmlString.length() + "\r\nContent-Type: text/html\r\n\n " + htmlString + "\r\n");
        outputStream.write(res.getBytes());
        outputStream.flush();
    }
}