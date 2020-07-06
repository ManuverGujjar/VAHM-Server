package com.manuver.core;


import java.io.OutputStream;
import java.util.HashMap;

public class Response {
    private OutputStream outputStream;
    private int responseCode;
    private String contentType;
    private HashMap<String, String> responseHeaders;
    private byte [] content;
    
    Response(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.responseHeaders = new HashMap<>();
        content = new String("").getBytes();
        contentType = "text/html";
        responseCode = 200;
    }

    public Response setStatus(int status) {
        this.responseCode = status;
        return this;
    }

    public int getStatus() {
        return this.responseCode;
    }

    public Response setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }



    public void send(String content) {
        send(content.getBytes());
    }

    public void send(byte [] content) {
        this.content = content;
        sendResponse();
    }

    private void setResponseHeaders() {
        responseHeaders.put("Connection", "close");
        responseHeaders.put("Content-Type", this.contentType);
        responseHeaders.put("Content-Length",  Integer.toString(content.length));
    }

    private void sendResponse() {
        setResponseHeaders();
        StringBuilder responseData = new StringBuilder();
        
        responseData.append("HTTP/1.1 " + this.responseCode + " " + ResponseCodes.getResponseCodeName(this.responseCode) + "\n\r");
        for (String headerKey : this.responseHeaders.keySet()) {
            responseData.append(headerKey + ":" + this.responseHeaders.get(headerKey) + "\n\r");
        }

        responseData.append("\n");
        try {
            this.outputStream.write(responseData.toString().getBytes());
            this.outputStream.write(content);
            this.outputStream.write('\n');


            System.out.println("response sent");
            System.out.println(responseData);
            System.out.println(new String(content));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private static class ResponseCodes {
        static String getResponseCodeName(int code) {
            switch (code) {
                case 200: return "OK";
                case 301: return "Moved Permanently";
                case 400: return "Bad Request";
                case 404: return "Not Found";
                case 501: return "Bad Gateway";
                default: return "Internel Server Error";
            }
        }
    }
}