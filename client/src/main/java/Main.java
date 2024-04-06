import chess.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import dataAccess.DataAccessException;
import model.AuthData;
import model.GameData;
import model.UserData;
import server.Request.*;
import server.Result.*;
import ui.ClientChessBoard;
import ui.Exception.ResponseException;
import ui.ServerFacade;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;
import websocket.ServerMessageHandler;
import websocket.WebSocketFacade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

public class Main implements ServerMessageHandler {

    ServerFacade serverFacade;
    private ArrayList<UserData> user = new ArrayList<>();
    private ArrayList<AuthData> auth = new ArrayList<>();
    private ClientChessBoard chessBoard = new ClientChessBoard();

    private ChessGame game = new ChessGame();
    private final String serverUrl;

    public Main(String serverUrl) {
        serverFacade = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        game.getBoard().resetBoard();
        game.setTeamTurn(ChessGame.TeamColor.WHITE);
    }

    public void main(String[] args) throws ResponseException, InvalidMoveException, IOException {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        Run();
    }
    public void Run() throws ResponseException, InvalidMoveException, IOException {
        Main main = new Main(this.serverUrl);
        Scanner scanner = new Scanner(System.in);
        boolean[] hasExited = {false};
        String[] auth = {""};
        String[] command = {""};
        Boolean[] gamePlayMode = {false};
        System.out.println("Welcome to 240 Chess. Press enter to get started");
        scanner.nextLine();
        while(!hasExited[0]) {
            if (!auth[0].equals("") && (gamePlayMode[0] == false)) {
                main.printAuthMenu(scanner, command);
                main.authMenuActions(scanner,command,auth,gamePlayMode);
            }
            else if (!auth[0].equals("") && (gamePlayMode[0] == true)) {
                String action = main.printGamePlayMenu(scanner,command);
                main.gamePlayActions(scanner,action,auth,gamePlayMode);
            }
            else {
                main.printNormMenu(scanner, command);
                main.normMenuActions(scanner, command, hasExited, auth);
            }

        }
    }

    public String printGamePlayMenu(Scanner scanner, String[] command){

        Scanner scanner1 = new Scanner(System.in);

        System.out.println("1) Help");
        System.out.println("2) Draw Chess Board");
        System.out.println("3) Leave Game");
        System.out.println("4) Make Move");
        System.out.println("5) Resign");
        System.out.println("6) Highlight Legal Moves");
        //command[0] = scanner1.nextLine();

        return scanner1.nextLine();

    }

    public void printNormMenu(Scanner scanner, String[] command){
        System.out.println("1) Help");
        System.out.println("2) Quit");
        System.out.println("3) Register");
        System.out.println("4) Login");
        System.out.println("5) Clear Application\n");
        System.out.println("Type the number for the command you want to perform");
        command[0] = scanner.nextLine();
    }
    public void printAuthMenu(Scanner scanner, String[] command){
        System.out.println("1) Help");
        System.out.println("2) Logout");
        System.out.println("3) Create Game");
        System.out.println("4) List Games");
        System.out.println("5) Join Game");
        System.out.println("6) Join Observer\n");
        System.out.println("Type the number for the command you want to perform");
        command[0] = scanner.nextLine();

    }

    public void normMenuActions(Scanner scanner, String[] command, boolean[] hasExited, String[] auth) throws ResponseException {
        switch (command[0]) {
            case "1" -> handleHelpActionNorm();
            case "2" -> handleQuitAction(hasExited);
            case "3" -> handleRegisterAction(scanner, auth);
            case "4" -> handleLoginAction(scanner, auth);
            case "5" -> handleClearApplicationAction(auth);
        }

    }

    private void handleLoginAction(Scanner scanner, String[] auth) {
        String username;
        String password;
        System.out.print("Username: ");
        username = scanner.nextLine();
        System.out.print("Password: ");
        password = scanner.nextLine();
        LoginRequest loginRequest = new LoginRequest(username,password);
        try{
            LoginResult result = serverFacade.serverLogin(loginRequest, auth);
            auth[0] = result.authToken();
            System.out.println(result.username() + " Just Logged In");
        }
        catch (ResponseException ex){
            System.out.println(ex.getMessage());
            System.out.println("Hit Enter to Continue");
            scanner.nextLine();
        }
    }

    private void handleClearApplicationAction(String[] auth){
        try{
             serverFacade.serverClearApplication(auth);
            System.out.println("Successfully Cleared the Application");
        }
        catch (ResponseException ex){
            System.out.println(ex.getMessage());
        }
    }
    private String handleRegisterAction(Scanner scanner, String[] auth) throws ResponseException{
        String username;
        String password;
        String email;
        System.out.print("Username: ");
        username = scanner.nextLine();
        System.out.print("Password: ");
        password = scanner.nextLine();
        System.out.print("Email: ");
        email = scanner.nextLine();
        RegisterRequest registerRequest = new RegisterRequest(username,password,email);
        try{
        RegisterResult result = serverFacade.serverRegister(registerRequest,auth);
        auth[0] = result.authToken();
            System.out.println(result.username() + " Just Registered");
        }
        catch (ResponseException ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }

    private static void handleQuitAction(boolean[] hasExited) {
        hasExited[0] = true;
    }

    private static void handleHelpActionNorm() {
        System.out.println("You are being Helped");
    }

    public void gamePlayActions(Scanner scanner, String command, String[] auth, Boolean[] gamePlayMode) throws InvalidMoveException {

        switch (command) {
            case "1" -> System.out.println("GamePlay Help");
            case "2" -> redrawChessBoard(game);
            case "3" -> System.out.println("Leave");
            case "4" -> makeMove(game,scanner);
            case "5" -> System.out.println("Resign");
            case "6" -> highlightLegalMoves(game,scanner);
        }
    }

    public void authMenuActions(Scanner scanner, String[] command, String auth[], Boolean[] gamePlayMode) throws ResponseException, IOException {
        switch (command[0]) {
            case "1" -> handleHelpActionAuth();
            case "2" -> handleLogoutAction(auth);
            case "3" -> handleCreateGameAction(scanner, auth);
            case "4" -> handleListGamesAction(scanner, auth);
            case "5" -> handleJoinGameAction(scanner,auth, gamePlayMode);
            case "6" -> handleJoinGameObserverAction(scanner,auth, gamePlayMode);
        }
    }

    private void handleJoinGameAction(Scanner scanner, String[] auth, Boolean[] gamePlayMode) throws ResponseException, IOException {
        String playerColor;
        System.out.print("Player Color (WHITE/BLACK): ");
        playerColor = scanner.nextLine();
        int gameID;
        System.out.println("Which Game Number Do You Want To Join: ");
        gameID = scanner.nextInt();
        JoinGameRequest request = new JoinGameRequest(auth[0],playerColor,gameID);
        try{
            WebSocketFacade webSocketFacade = new WebSocketFacade(serverUrl, new ServerMessageHandler() {
                @Override
                public void notify(String message) {
                    NotificationMessage newMessage = new Gson().fromJson(message, NotificationMessage.class);
                    LoadGameMessage newMessage1 = new Gson().fromJson(message, LoadGameMessage.class);
                    ErrorMessage newMessage2 = new Gson().fromJson(message, ErrorMessage.class);
                    String messageString = newMessage.getNotificationMessage();
                    String messageString1 = new Gson().toJson(newMessage1);
                    String messageString2 = new Gson().toJson(newMessage2);
                    if (messageString != null) {
                        displayNotification(messageString);
                    }
                    if (messageString2 != null){
                        displayError(messageString2);
                    }
                    if (newMessage1.getGame() != null) {
                        displayLoadGame(messageString1);
                    }
                }
            });
            webSocketFacade.joinGame(gameID,auth,"a",playerColor);
            JoinGameResult result = serverFacade.serverJoinGame(request,auth);
            System.out.println("Game: " + result.gameID() + " has been joined as " + playerColor);
            boolean[][] validMoves = validMoveInit();
            chessBoard.printChessBoard(this.game.getBoard(),validMoves);
            gamePlayMode[0] = true;
        }
        catch (ResponseException ex){

            WebSocketFacade webSocketFacade = new WebSocketFacade(serverUrl, new ServerMessageHandler() {
                @Override
                public void notify(String message) {
                    NotificationMessage newMessage = new Gson().fromJson(message, NotificationMessage.class);
                    LoadGameMessage newMessage1 = new Gson().fromJson(message, LoadGameMessage.class);
                    ErrorMessage newMessage2 = new Gson().fromJson(message, ErrorMessage.class);
                    String messageString = newMessage.getNotificationMessage();
                    String messageString1 = new Gson().toJson(newMessage1);
                    String messageString2 = new Gson().toJson(newMessage2);
                    if (messageString != null) {
                        displayNotification(messageString);
                    }
                    if (messageString2 != null){
                        displayError(messageString2);
                    }
                    if (newMessage1.getGame() != null) {
                        displayLoadGame(messageString1);
                    }
                }
            });

            webSocketFacade.joinGame(gameID,auth,"a",playerColor);

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleJoinGameObserverAction(Scanner scanner, String[] auth, Boolean[] gamePlayMode) {
        int gameID;
        System.out.println("Which Game Number Do You Want To Join: ");
        gameID = scanner.nextInt();
        JoinGameRequest request = new JoinGameRequest(auth[0],"",gameID);
        try{
            JoinGameResult result = serverFacade.serverJoinGame(request,auth);
            System.out.println("Game: " + result.gameID() + " has been joined as an observer");
            boolean[][] validMoves = validMoveInit();
            chessBoard.printChessBoard(game.getBoard(),validMoves);
            gamePlayMode[0] = true;
        }
        catch (ResponseException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void handleListGamesAction(Scanner scanner, String[] auth) {
        ListGamesRequest listGamesRequest = new ListGamesRequest(auth[0]);
        try{
            ListGamesResult result = serverFacade.serverListGames(listGamesRequest, auth);
            for (GameData game : result.games()){
                System.out.println("Game ID: " + game.getGameID());
                System.out.println("Black User: " + game.getBlackUsername());
                System.out.println("White User: " + game.getWhiteUsername());
                System.out.println("Game Name: " + game.getGameName());
                System.out.println("Game: " + game.getGame());
            }
        }
        catch (ResponseException ex){
            System.out.println(ex.getMessage());
            System.out.println("Hit Enter to Continue");
            scanner.nextLine();
        }
    }

    private void handleCreateGameAction(Scanner scanner, String[] auth) throws ResponseException {
        String gameName;
        System.out.print("Game Name: ");
        gameName = scanner.nextLine();
        CreateGameRequest request = new CreateGameRequest(auth[0],gameName);
        try{
            CreateGameResult result = serverFacade.serverCreateGame(request,auth);
            System.out.println("Game " + gameName + " with Game ID : " + result.gameID() + " has been created");
        }
        catch (ResponseException ex){
            System.out.println(ex.getMessage());
        }

    }

    public void redrawChessBoard(ChessGame game){
        boolean[][] validMoves = validMoveInit();
        chessBoard.printChessBoard(game.getBoard(),validMoves);
    }

    public void makeMove(ChessGame game, Scanner scanner) throws InvalidMoveException {
        System.out.println("Starting Row?");
        int startRow = scanner.nextInt();
        System.out.println("Starting Column?");
        int startCol = scanner.nextInt();
        System.out.println("Ending Row");
        int endRow = scanner.nextInt();
        System.out.println("Ending Column");
        int endCol = scanner.nextInt();
        ChessPosition startPosition = new ChessPosition(startRow,startCol);
        ChessPosition endPosition = new ChessPosition(endRow,endCol);

        ChessMove newMove = new ChessMove(startPosition,endPosition,null);

        boolean[][] validMoves = validMoveInit();

        Collection<ChessMove> moves = game.validMoves(startPosition);

        if (moves.isEmpty()){
            System.out.println("No Legal Moves");
        }
        else {
            for (ChessMove move : moves) {
                validMoves[move.getEndPosition().getRow()][move.getEndPosition().getColumn()] = true;
            }
            //serverFacade.
            //game.makeMove(newMove);
        }


    }
    public void highlightLegalMoves(ChessGame game, Scanner scanner){
        System.out.println("What Row?");
        int row = scanner.nextInt();
        System.out.println("What Column?");
        int col = scanner.nextInt();
        ChessPosition position = new ChessPosition(row,col);
        boolean[][] validMoves = validMoveInit();

        Collection<ChessMove> moves = game.validMoves(position);

        for (ChessMove move : moves){
            validMoves[move.getEndPosition().getRow()][move.getEndPosition().getColumn()] = true;
        }
        chessBoard.printChessBoard(game.getBoard(),validMoves);





    }

    private static boolean[][] validMoveInit() {
        boolean[][] validMoves = new boolean[9][9];
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                validMoves[i][j] = false;
            }
        }
        return validMoves;
    }

    private static void handleLogoutAction(String[] auth) {
        auth[0] = "";
    }

    private static void handleHelpActionAuth() {
        System.out.println("You are being helped");
    }

    public void displayNotification(String message){
        System.out.println(message);
    }

    public void displayError(String message){
        ErrorMessage errorMessage = new Gson().fromJson(message, ErrorMessage.class);

        System.out.println(errorMessage.getErrorMessage());
    }
    public void displayLoadGame(String message){
        LoadGameMessage loadGameMessage = new Gson().fromJson(message, LoadGameMessage.class);
        System.out.println(loadGameMessage.getGame());
    }

    @Override
    public void notify(String serverMessage) {

    }
}