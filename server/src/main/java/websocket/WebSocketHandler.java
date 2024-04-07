package websocket;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
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
import webSocketMessages.userCommands.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;


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

        Connection conn = new Connection(command.getAuthString(), session);
        switch (command.getCommandType()) {
            case JOIN_PLAYER -> {
                JoinPlayer joinPlayerCommand = new Gson().fromJson(msg, JoinPlayer.class);
                join(conn, command, joinPlayerCommand.getPlayerColor(),joinPlayerCommand.getGameID());
            }
            case JOIN_OBSERVER -> {
                JoinObserver joinObserver = new Gson().fromJson(msg, JoinObserver.class);
                joinObserver(conn,command,joinObserver.getGameID());
            }
            case MAKE_MOVE -> {
                MakeMove makeMove = new Gson().fromJson(msg, MakeMove.class);
                makeMove(conn,command,makeMove.getGameID(),makeMove.getMove());
            }
            case LEAVE -> {
                Leave leave = new Gson().fromJson(msg,Leave.class);
                leave(conn,command,leave.getGameID());
            }
            case RESIGN -> {
                Resign resign = new Gson().fromJson(msg, Resign.class);
                resign(conn,command,resign.getGameID());
            }
        }
    }
    public void leave(Connection conn, UserGameCommand command, int gameID){
        try{
            GameData gameData = gamesDatabase.readOneGame(gameID);
            AuthData authData = authDatabase.readAuth(command.getAuthString());
            ChessGame chessGame = gameData.getGame();

            if (authData.username().equalsIgnoreCase(gameData.getWhiteUsername())){
                gamesDatabase.updateGame("WHITE",null,gameID);
            }
            else if (authData.username().equalsIgnoreCase(gameData.getBlackUsername())){
                gamesDatabase.updateGame("BLACK",null,gameID);
            }
            ServerMessage notiMessage = new NotificationMessage(authData.username() + "Just Left the Game");
            connections.broadcastOthers(gameID, conn.visitorName, notiMessage);
            conn.session.close();
            connections.connectionsMap.remove(conn);
        }
        catch (DataAccessException ex){
            //err
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void resign(Connection conn, UserGameCommand command, int gameID) throws DataAccessException, IOException {
        try {
            GameData gameData = gamesDatabase.readOneGame(gameID);
            AuthData authData = authDatabase.readAuth(command.getAuthString());
            ChessGame chessGame = gameData.getGame();
            if (chessGame.getTeamTurn() == null){
                ServerMessage someoneAlreadyResignedMessage = new ErrorMessage("You can't resign, someone already did");
                conn.send(new Gson().toJson(someoneAlreadyResignedMessage));
                return;
            }
            if (authData.username().equalsIgnoreCase(gameData.getWhiteUsername()) || authData.username().equalsIgnoreCase(gameData.getBlackUsername())){
                chessGame.setTeamTurn(null);
            }
            else {
                ServerMessage observerCantResignMessage = new ErrorMessage("You are an observer, you can't resign");
                conn.send(new Gson().toJson(observerCantResignMessage));
                return;
            }

            gamesDatabase.updateChessGame(gameID, chessGame);
            ServerMessage notiMessage = new NotificationMessage(authData.username() + "Just Resigned");
            connections.broadcastOthers(gameID, "fake", notiMessage);
            conn.session.close();
        }
        catch (DataAccessException ex){
            //errr
        }
    }

    public void makeMove(Connection conn, UserGameCommand command, int gameID, ChessMove move) throws DataAccessException, InvalidMoveException, IOException {
        try {
            GameData gameData = gamesDatabase.readOneGame(gameID);
            AuthData authData = authDatabase.readAuth(command.getAuthString());
            ChessGame game = gameData.getGame();
            if (game.getTeamTurn() == null){
                ServerMessage errorMessage = new ErrorMessage("Invalid Move");
                conn.send(new Gson().toJson(errorMessage));
            }
            boolean isValid = game.validMoves(move.getStartPosition()).contains(move);
            for (ChessMove chessMove : game.validMoves(move.getStartPosition())){
                if (!authData.username().equalsIgnoreCase(game.getTeamTurn().toString())){
                    isValid = false;
                    break;
                }
            }

            if (isValid) {
                try {
                    game.makeMove(move);
                    gamesDatabase.updateChessGame(gameID, game);
                    ServerMessage message = new LoadGameMessage(game);
                    ServerMessage notiMessage = new NotificationMessage(authData.username() + "Just Moved");
                    connections.broadcastOthers(gameID, "fake", message);
                    connections.broadcastOthers(gameID, conn.visitorName, notiMessage);
                } catch (InvalidMoveException e) {
                    ServerMessage notYourTurnError = new ErrorMessage("Error: Invalid Move");
                    conn.send(new Gson().toJson(notYourTurnError));

                } catch (DataAccessException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                ServerMessage errorMessage = new ErrorMessage("Invalid Move");
                conn.send(new Gson().toJson(errorMessage));
            }
        }
        catch (DataAccessException ex){
            System.out.println("error");
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
            game.getGame().setTeamTurn(ChessGame.TeamColor.WHITE);
            gamesDatabase.updateChessGame(gameID,game.getGame());
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