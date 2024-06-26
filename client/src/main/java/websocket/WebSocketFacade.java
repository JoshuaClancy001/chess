package websocket;

import chess.ChessMove;
import com.google.gson.Gson;
import ui.Exception.ResponseException;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class WebSocketFacade extends Endpoint {
    Session session;
    ServerMessageHandler serverMessageHandler;

    public WebSocketFacade(String url, ServerMessageHandler serverMessageHandler) throws ResponseException{

        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/connect");
            this.serverMessageHandler = serverMessageHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    serverMessageHandler.notify(message);
                }

            });

        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    public void joinGame(Integer gameID, String[] auth, String clientColor) throws ResponseException, IOException {
        System.out.println("In Join Game");
        JoinPlayer command = new JoinPlayer(auth[0], UserGameCommand.CommandType.JOIN_PLAYER,gameID,clientColor );

        this.session.getBasicRemote().sendText(new Gson().toJson(command));
    }
    public void joinObserver(int gameID, String[] auth) throws IOException {
        JoinObserver command = new JoinObserver(auth[0], UserGameCommand.CommandType.JOIN_OBSERVER,gameID);
        this.session.getBasicRemote().sendText(new Gson().toJson(command));
    }
    public void leave(int gameID,String[] auth) throws IOException {
        Leave command = new Leave(auth[0],gameID, UserGameCommand.CommandType.LEAVE);
        this.session.getBasicRemote().sendText(new Gson().toJson(command));
    }

    public void makeMove(int gameID, String[] auth, ChessMove chessMove) throws IOException {
        MakeMove command = new MakeMove(auth[0],gameID,chessMove, UserGameCommand.CommandType.MAKE_MOVE);
        this.session.getBasicRemote().sendText(new Gson().toJson(command));
    }

    public void resign(int gameID, String[] auth) throws IOException {
        Resign command = new Resign(auth[0],gameID, UserGameCommand.CommandType.RESIGN);
        this.session.getBasicRemote().sendText(new Gson().toJson(command));
    }

}
