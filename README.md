# Vahm Server

[![Generic badge](https://img.shields.io/badge/Status-In%20Development-<COLOR>.svg)](https://shields.io/) [![Generic badge](https://img.shields.io/badge/Java-8.0+-<COLOR>.svg)](https://shields.io/)

Vahm Server is Java based basic web server implementation with almost zero dependancies other than JDK on external libraries.

- Almost Zero dependancies
- Lightweight
- Easy to use
- More features comming soon..

## Example

```java
Server server = new Server();

server.get('/', (Request req, Response res) -> {
   res.send("Hello World");
});

server.listen(8000, (Exception e -> {
    System.out.println(e);
});
```

#### returning 404

```java
Server server = new Server();

server.get('/invalid-url', (Request req, Response res) -> {
   res.setStatus(404).send("url not found on the server");
});

server.listen(8000, (Exception e -> {
    System.out.println(e);
});
```

### Running the project

Vahm requires [JDK](https://google.com/search?q=install%20jdk8) 8+ and [Maven 3.3.6](https://google.com/search?q=install%20maven%203.3.6)

```sh
git clone https://github.com/manuvergujjar/VAHM-Server.git
cd VAHM-Server/
mvn install
cd target/classes
java com/manuver/app/App
```

### Todos

- more Testing
- more fuctionality

## License

[![GitHub license](https://img.shields.io/github/license/Naereen/StrapDown.js.svg)](https://github.com/Naereen/StrapDown.js/blob/master/LICENSE)

MIT
