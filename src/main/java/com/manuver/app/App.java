package com.manuver.app;

import com.manuver.core.Server;

public class App {
    public static void main(String[] args) {
        Server server = new Server();

        server.serveStaticDir("/Users/manuver/Desktop/");

        server.listen(2000, Exception::printStackTrace);
    }
}

