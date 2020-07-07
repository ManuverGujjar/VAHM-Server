package com.manuver.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.manuver.core.*;

public class App {
    public static void main(String[] args) throws Exception {
        Server server = new Server();

        server.get("/get", (Request req, Response res) -> {
            res.setContentType("text/html").setStatus(200).send("Hello World");
        });

        server.get("/", (Request req, Response res) -> {
            res.setContentType("text/html").setStatus(400).send("not found");
        });

        server.get("/favicon.ico", (Request req, Response res) -> {
            try {
                InputStream in = new FileInputStream(new File("/Users/manuver/Downloads/pin.ico"));
                res.setContentType("image/x-icon").setStatus(200).send(in.readAllBytes());
            } catch (Exception e) {
                e.printStackTrace();
                res.setStatus(404).send("not present on server");
            }
        });


        server.listen(2000, (Exception e) -> {
            e.printStackTrace();
        });
    }
}

