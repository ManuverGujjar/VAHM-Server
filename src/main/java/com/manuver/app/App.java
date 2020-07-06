package com.manuver.app;

import com.manuver.core.*;

public class App {
    public static void main(String[] args) throws Exception {
        Server server = new Server();

        server.get("/", (Request req, Response res) -> {
            res.setContentType("text/html").setStatus(200).send("Hello World");
        });

        server.get("/hello", (Request req, Response res) -> {
            res.setContentType("text/html").setStatus(200).send("this is url hello");
        });


        server.get("/hi", (Request req, Response res) -> {
            res.setContentType("text/html").setStatus(200).send("this is url hi");
        });

        server.listen(1000, (Exception e) -> {
            e.printStackTrace();
        });
    }
}

