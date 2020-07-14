package com.manuver.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;

class RequestTest {
    
    @Test
    void testRequestUrl() {
        String req = "GET /url?name=manuver HTTP/1.1\n\r";
        InputStream inputStream = new ByteArrayInputStream(req.getBytes());
        Request request; //= new Request(inputStream);
        // assertEquals("GET", request.method);
        // assertEquals("/url", request.path);

        // req = "GET /favicon HTTP/1.1\n\r";
        // inputStream = new ByteArrayInputStream(req.getBytes());
        // request = new Request(inputStream);
        // assertEquals("GET", request.method);
        // assertEquals("/favicon", request.path);

        req = "GET /favicon HTTP/1.1\n\r";
        inputStream = new ByteArrayInputStream(req.getBytes());
        request = new Request(inputStream);
        assertEquals("GET", request.method);
        assertEquals("/favicon", request.path);
    }  

    @Test 
    void testQueryString() {
        String req = "GET /url?name=manuver HTTP/1.1\n\r";
        InputStream inputStream = new ByteArrayInputStream(req.getBytes());
        Request request = new Request(inputStream);
        HashMap<String, String> expected = new HashMap<>();
        expected.put("name", "manuver");
        assertEquals(expected, request.query);
        for (String key : expected.keySet()){
            assertEquals(expected.get(key), request.query.get(key));
        }
    }

}