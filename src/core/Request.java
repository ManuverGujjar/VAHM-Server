package core;

import java.util.HashMap;

public class Request {

    public final String method;
    public final HashMap<String, String> query;
    public final String GET = "GET";
    public final String POST = "POST";

    Request(String requestString) {
        
        method = this.GET;
        query = new HashMap<>();
    }
}