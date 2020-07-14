package com.manuver.core;

import java.io.InputStream;
import java.util.ArrayList;
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
            int last = 0, current = 0; boolean ended = false;
            StringBuilder data = new StringBuilder();
            while (!ended) {
                last = current;
                current = inputStream.read();
                // System.out.print((char)current);
                data.append((char)current);
                if (current == '\r') {
                    requestLines.add(data.toString().strip());
                    data = new StringBuilder();
                }
                if (last == '\n' && current == '\r') {
                    ended = true;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(requestLines);
        System.out.println("Request parsed");
        return requestLines;
    }

    private void parseQuery(String path) {
        String re = "(.+)\\?(.+)";
        Pattern pattern = Pattern.compile(re);
        Matcher matcher = pattern.matcher(path.strip());
        if (matcher.find()) {
            String queries = matcher.group(2);
            try {
                for (String query : queries.split("&")) {
                    String key = query.split("=")[0];
                    String value = "";
                    try {
                        value = query.split("=")[1];
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                    this.query.put(key, value);
                }
            } catch (ArrayIndexOutOfBoundsException exception) {
                exception.printStackTrace();
            }
        }
    }

    private String getPath(String path) {
        System.out.println("path -" + path + "-");
        String re = "^(.+)\\?(.+)$";
        Pattern pattern = Pattern.compile(re);
        Matcher matcher = pattern.matcher(path);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    Request(InputStream inputStream) {

        headers = new HashMap<>();
        query = new HashMap<>();

        List<String> lines = getRequestString(inputStream);

        for (int lineCount = 1; lineCount < lines.size(); lineCount++) {
            String [] header = lines.get(lineCount).split(":");
            try {
                headers.put(header[0], header[1]);
            } catch (ArrayIndexOutOfBoundsException exception) {
                exception.printStackTrace();
            }
        }
        
        String re = "^(GET|POST)\s(.*)\sHTTP/(1.0|1.1)$";
        Pattern pattern = Pattern.compile(re);
        Matcher matcher = pattern.matcher(lines.get(0).strip());
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
    }
}