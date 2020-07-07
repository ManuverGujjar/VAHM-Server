package com.manuver.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.manuver.core.*;

public class App {
    public static void main(String[] args) throws Exception {
        Server server = new Server();

        server.serveStaticDir("/Users/manuver/Desktop/");

        server.listen(2000, (Exception e) -> {
            e.printStackTrace();
        });
    }
}

