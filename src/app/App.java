package app;

import core.Request;
import core.Response;
import core.Server;

public class App {
    public static void main(String[] args) throws Exception {
        Server server = new Server();

        server.get("/", (Request req, Response res) -> {
            res.setContentType("text/html").setStatus(200).send("Hello World");
        });

        server.listen(1000, (Exception e) -> {
            System.out.println(e);
        });
    }
}

