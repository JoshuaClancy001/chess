package websocket;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.SQLDAO.SQLGAMEDAO;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import server.Server;
import service.Services;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.JoinPlayer;
import webSocketMessages.userCommands.UserGameCommand;

import java.io.IOException;


@WebSocket
public class WebSocketHandler {

    private final ConnectionManager connections = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String msg) throws Exception {
        UserGameCommand command = new Gson().fromJson(msg, UserGameCommand.class);
        JoinPlayer joinPlayerCommand = new Gson().fromJson(msg, JoinPlayer.class);

        var conn = new Connection(command.getAuthString(),session);
        if (conn != null) {
            switch (command.getCommandType()) {
                case JOIN_PLAYER -> join(conn, command,joinPlayerCommand.getUsername(), joinPlayerCommand.getClientColor(),joinPlayerCommand.getGameID());
            }
        } else {
            //Connection.sendError(session.getRemote(), "unknown user");
        }
    }

    public void join(Connection conn, UserGameCommand command, String user, String clientColor, int gameID) throws IOException, DataAccessException {
        connections.add(gameID,command.getAuthString(), conn.session);
        SQLGAMEDAO gamesDatabase = new SQLGAMEDAO();
        ChessGame game = gamesDatabase.readOneGame(gameID).getGame();
        ServerMessage message = new NotificationMessage(user + " joined the game as " + clientColor);
        ServerMessage message1 = new LoadGameMessage(game);
        String excludedUser = "";
        for (Connection connection: connections.connectionsMap.get(gameID)){
            if (connection.visitorName != command.getAuthString()){
                excludedUser = connection.visitorName;
            }
        }

        connections.broadcast(gameID,conn.visitorName,message);
        connections.broadcast(gameID,excludedUser,message1);
    }

}