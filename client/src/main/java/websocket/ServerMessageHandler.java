package websocket;


import webSocketMessages.serverMessages.ServerMessage;

public interface ServerMessageHandler {
    void notify(String serverMessage);
}