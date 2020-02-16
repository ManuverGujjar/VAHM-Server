package core;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request {

    public final String method;
    public final HashMap<String, String> query;
    public final HashMap<String, String> headers;
    public final String path;
    public final String GET = "GET";
    public final String POST = "POST";
    public final String httpVersion;

    private List<String> getRequestString(InputStream inputStream) {
        System.out.println("[+] Request Received...");
        List<String> requestLines = new ArrayList<>(); 
        try {
            StringBuilder data = new StringBuilder();
            while (inputStream.available() > 0) {
                int single = inputStream.read();
                System.out.print((char)single);
                data.append((char)single);
                if (single == '\r') {
                    requestLines.add(data.toString().strip());
                    data = new StringBuilder();
                }
            }
        } catch (Exception e) {

        }
        return requestLines;
    }

    private void parseQuery(String path) {
        String re = "^(.*)?(.*)$";
        Pattern pattern = Pattern.compile(re);
        Matcher matcher = pattern.matcher(path.strip());
        String queries = matcher.group(2);
        try {
            for (var query : queries.split("&")) {
                String key = query.split("=")[0];
                String value = "";
                try {
                    value = query.split("=")[1];
                } catch(Exception e) {

                }
                this.query.put(key, value);
            }
        } catch (ArrayIndexOutOfBoundsException exception) {

        }
    }

    private String getPath(String path) {
        String re = "^(.*)?(.*)$";
        Pattern pattern = Pattern.compile(re);
        Matcher matcher = pattern.matcher(path.strip());
        return matcher.group(1);
    }

    Request(InputStream inputStream) {

        headers = new HashMap<>();
        query = new HashMap<>();

        var lines = getRequestString(inputStream);

        for (int lineCount = 1; lineCount < lines.size(); lineCount++) {
            String [] header = lines.get(lineCount).split(":");
            try {
                headers.put(header[0], header[1]);
            } catch (ArrayIndexOutOfBoundsException exception) {
                
            }
        }
        
        String re = "^(GET|POST)\s(.*)\sHTTP/(1.0|1.1)$";
        Pattern pattern = Pattern.compile(re);
        System.out.println(1);
        Matcher matcher = pattern.matcher(lines.get(0).strip());
        System.out.println(2);
        System.out.println(matcher.find());
        this.method = matcher.group(1);
        String path = matcher.group(2);
        this.httpVersion = matcher.group(3);
        if (path.contains("?")) {
            this.path = this.getPath(path);
            this.parseQuery(path);
        } else {
            this.path = path;
        }

        System.out.printf("[+] %s %s\n", this.method, this.path);
    }
}