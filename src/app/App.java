package app;

import java.io.File;
import java.io.FileReader;
import java.net.ServerSocket;

public class App {
    public static void main(String[] args) {
        try {
            create();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static String getHtml(String filePath) throws Exception{
        StringBuilder htmlString = new StringBuilder();
        var fr =  new FileReader(new File(filePath));
        int c = fr.read();
        while (c != -1) {
            htmlString.append((char)c);
            c = fr.read();
        }
        return htmlString.toString();
    }

    public static void create() throws Exception{
        
        ServerSocket serverSocket = new ServerSocket(2222);
        
        var s = serverSocket.accept();
        System.out.println("[+] new conn from " + s.getInetAddress());
        System.out.println(new String(s.getInputStream().readAllBytes()));
        var o = s.getOutputStream();
        try {
            // var htmlString = getHtml("/Users/manuver/code/code/index.html");
            o.write(new String("HTTP/1.1 200 OK\r\nContent-Length: 5\r\nContent-Type: text/html\r\n\nHello\r\n").getBytes());
        } catch (Exception e) {
            o.write(new String("HTTP/1.1 404 Not Found\r\nContent-Length: 0\r\n\n").getBytes());
        }
        o.flush();
        while (true);
    }
}