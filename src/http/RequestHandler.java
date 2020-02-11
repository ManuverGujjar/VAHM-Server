package http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import app.Log;
import app.ServerDetails;

public class RequestHandler {
    String requesString;
    public int requestType;
    public final static int GET = 0;
    public final static int POST = 1;
    String path;

    public RequestHandler(String requestString) {
        this.requesString = requestString;
    }

    public void parseRequest()  throws InvalidRequestException {
        if (requesString.startsWith("GET")) {
            requestType = RequestHandler.GET;
        } else if (requesString.startsWith("POST")) {
            requestType = RequestHandler.POST;
        } else throw new InvalidRequestException();

        if (requestType == GET) {
            int start = 5;
            path = "";
            for (; requesString.charAt(start) != '?' && requesString.charAt(start) != ' ' ; start++) {
                path = path + requesString.charAt(start);
            }
            Log.println(path);
        }

    }

    private String readFile() throws Http404Error {
        File file = null;
        if (path.equals("")) {
            file = new File(ServerDetails.ROOTPATH, "index.html");
        } else if (path.endsWith("html")) {
            file = new File(ServerDetails.ROOTPATH, path);
        } else {
            file = new File(new File(ServerDetails.ROOTPATH, path), "index.html");
        }

        try {
            FileInputStream fileReader = new FileInputStream(file);

            String resString = new String(fileReader.readAllBytes());
            fileReader.close();
            return resString;

        } catch (FileNotFoundException ex) {
            throw new Http404Error();
        } catch (IOException e) {
            throw new Http404Error();
        }
    }

    public String HTTPResponse() {
        HashMap<String, String> responseHeader = new HashMap<>();
        String data = "";
        String responseString = "";

        try {
            data = readFile();
            responseString = "HTTP/1.1 OK 200\r\n";
        } catch (Http404Error e) {
            data = "Sorry This Page isnt Available on this Server ";
            responseString = "HTTP/1.1 NOT FOUND 404\r\n";
        } finally {
            responseHeader.put("Content-Type", "text/html");
            responseHeader.put("Content-Length", Integer.toString(10));
            for (var e : responseHeader.keySet()) {
                responseString += e + ": " + responseHeader.get(e) + "\r\n";
            }
            responseString += "\n" + data + "\n";
            String res = new String("HTTP/1.1 200 OK\r\nContent-Length: " + data.length() + "\r\nContent-Type: text/html\r\n\n " + data + "\r\n");
            System.out.println(res);
            return res;
        }
    }

}