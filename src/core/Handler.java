package core;

public interface Handler {
    public void onRequestReceived(Request req, Response res);
}