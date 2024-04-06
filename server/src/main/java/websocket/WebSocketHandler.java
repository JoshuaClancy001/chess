package websocket;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.SQLDAO.SQLAUTHDAO;
import dataAccess.SQLDAO.SQLGAMEDAO;
import dataAccess.SQLDAO.SQLUSERDAO;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.ErrorMessage;
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

        var conn = new Connection(command.getAuthString(),session);
        if (conn != null) {
            switch (command.getCommandType()) {
                case JOIN_PLAYER -> {
                    JoinPlayer joinPlayerCommand = new Gson().fromJson(msg, JoinPlayer.class);
                    join(conn, command, joinPlayerCommand.getPlayerColor(),joinPlayerCommand.getGameID());
                }
            }
        } else {
            //Connection.sendError(session.getRemote(), "unknown user");
        }
    }

    public void join(Connection conn, UserGameCommand command, String clientColor, int gameID) throws IOException, DataAccessException {
        ;
        SQLGAMEDAO gamesDatabase = new SQLGAMEDAO();
        SQLAUTHDAO authDatabase = new SQLAUTHDAO();
        SQLUSERDAO userDatabase = new SQLUSERDAO();
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
    private String getOppUser(UserGameCommand command, int gameID) {
        String excludedUser = "";
        for (Connection connection: connections.connectionsMap.get(gameID)){
            if (connection.visitorName != command.getAuthString()){
                excludedUser = connection.visitorName;
            }
        }
        return excludedUser;
    }

}