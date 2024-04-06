package websocket;

import com.google.gson.Gson;
import com.mysql.cj.ServerVersion;
import dataAccess.DataAccessException;
import dataAccess.SQLDAO.SQLAUTHDAO;
import dataAccess.SQLDAO.SQLGAMEDAO;
import dataAccess.SQLDAO.SQLUSERDAO;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.JoinObserver;
import webSocketMessages.userCommands.JoinPlayer;
import webSocketMessages.userCommands.UserGameCommand;

import java.io.IOException;


@WebSocket
public class WebSocketHandler {

    SQLGAMEDAO gamesDatabase = new SQLGAMEDAO();
    SQLAUTHDAO authDatabase = new SQLAUTHDAO();
    SQLUSERDAO userDatabase = new SQLUSERDAO();

    private final ConnectionManager connections = new ConnectionManager();

    public WebSocketHandler() throws DataAccessException {
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String msg) throws Exception {
        UserGameCommand command = new Gson().fromJson(msg, UserGameCommand.class);

        var conn = new Connection(command.getAuthString(),session);
        if (conn != null) {
            switch (command.getCommandType()) {
                case JOIN_PLAYER -> {
                    JoinPlayer joinPlayerCommand = new Gson().fromJson(msg, JoinPlayer.class);
                    join(conn, command, joinPlayerCommand.getPlayerColor(),joinPlayerCommand.getGameID());
                }
                case JOIN_OBSERVER -> {
                    JoinObserver joinObserver = new Gson().fromJson(msg, JoinObserver.class);
                    joinObserver(conn,command,joinObserver.getGameID());
                }
            }
        } else {
            //Connection.sendError(session.getRemote(), "unknown user");
        }
    }

    public void joinObserver(Connection conn, UserGameCommand command,int gameID) throws DataAccessException, IOException {

        try {

            AuthData auth = authDatabase.readAuth(command.getAuthString());
            if (auth == null){
                ServerMessage badAuthError = new ErrorMessage("Bad AuthToken");
                conn.send(new Gson().toJson(badAuthError));
                return;
            }
            GameData gameData = gamesDatabase.readOneGame(gameID);
            ServerMessage gameMessage = new LoadGameMessage(gameData.getGame());
            ServerMessage message = new NotificationMessage(auth.username() + " has joined as an observer");

            connections.add(gameID, command.getAuthString(), conn.session);
            connections.broadcastOthers(gameID, command.getAuthString(), message);
            conn.send(new Gson().toJson(gameMessage));
        }
        catch (DataAccessException ex){
            if (ex.getStatusCode() == 400){
                ServerMessage message2 = new ErrorMessage("gameID doesnt exist");
                conn.send(new Gson().toJson(message2));
            }
        }
    }

    public void join(Connection conn, UserGameCommand command, String clientColor, int gameID) throws IOException, DataAccessException {
        AuthData auth = authDatabase.readAuth(command.getAuthString());
        ServerMessage message2 = null;

        if (auth == null){
            message2 = new ErrorMessage("Bad AuthToken");
            conn.send(new Gson().toJson(message2));
            return;
        }
        try {
            GameData game = gamesDatabase.readOneGame(gameID);
            String whiteUser = game.getWhiteUsername();
            String blackUser = game.getBlackUsername();

            if (clientColor.equals("WHITE")) {
                if (game.getWhiteUsername() == null) {
                    ServerMessage message3 = new ErrorMessage("HTTP not called, WHITE is null");
                    conn.send(new Gson().toJson(message3));
                    return;
                }
            }

            if (clientColor.equals("WHITE") && !whiteUser.equals(auth.username())) {
                message2 = new ErrorMessage("ClientColor is already taken");
                conn.send(new Gson().toJson(message2));
                return;
            } else if (clientColor.equals("BLACK") && !blackUser.equals(auth.username())) {
                message2 = new ErrorMessage("Black is already taken");
                conn.send(new Gson().toJson(message2));
                return;
            } else {

                connections.add(gameID, command.getAuthString(), conn.session);

                ServerMessage message = new NotificationMessage("hi" + " joined the game as " + clientColor);
                ServerMessage message1 = new LoadGameMessage(game.getGame());

                connections.broadcastOthers(gameID, conn.visitorName, message);
                connections.broadcastSelf(gameID, conn.visitorName, message1);
            }
        }
        catch (DataAccessException ex){
            if (ex.getStatusCode() == 400){
                message2 = new ErrorMessage("gameID doesnt exist");
                conn.send(new Gson().toJson(message2));
            }
        }
    }


}