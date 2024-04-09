import Request.*;
import Result.*;
import chess.*;
import com.google.gson.Gson;
import model.GameData;
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
import java.util.Collection;
import java.util.Scanner;

public class Main implements ServerMessageHandler {

    WebSocketFacade webSocketFacade;
    ServerFacade serverFacade;
    private ClientChessBoard chessBoard = new ClientChessBoard();
    private ChessGame game = new ChessGame();
    String[] user = {""};
    int[] gameIDTemp = {1};
    private final String serverUrl;
    public Main(String serverUrl) throws ResponseException {
        webSocketFacade = new WebSocketFacade(serverUrl, serverMessage -> notify(serverMessage));
        serverFacade = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
        game.getBoard().resetBoard();
        game.setTeamTurn(ChessGame.TeamColor.WHITE);
    }
    public void main(String[] args) throws ResponseException, InvalidMoveException, IOException {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        run();
    }
    public void run() throws ResponseException, InvalidMoveException, IOException {
        Main main = new Main(this.serverUrl);
        Scanner scanner = new Scanner(System.in);
        boolean[] hasExited = {false};
        String[] auth = {""};
        String[] command = {""};
        boolean[] gamePlayMode = {false};
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
            this.user[0] = username;
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
        this.user[0] = username;
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
        System.out.println("Quit -> Exit the Application");
        System.out.println("Register -> Provide a username,password, and email to register");
        System.out.println("Login -> Provide a username and password to login");
    }
    public void handleHelpActionGamePlay(){
        System.out.println("Draw Chess Board -> Draws the Current Chess Board");
        System.out.println("Leave Game -> Exit the Current Game");
        System.out.println("Make Move -> Provide a Piece to move(row,column) and a end square(row,column)");
        System.out.println("Resign -> Resign from the game");
        System.out.println("Highlight Legal Moves -> Provide a Piece(row,column) and see where that piece can move");
    }

    public void gamePlayActions(Scanner scanner, String command, String[] auth, boolean[] gamePlayMode) throws InvalidMoveException, ResponseException, IOException {

        switch (command) {
            case "1" -> handleHelpActionGamePlay();
            case "2" -> redrawChessBoard();
            case "3" -> leave(game,scanner,auth,gamePlayMode);
            case "4" -> makeMove(game,scanner,auth);
            case "5" -> resign(game,auth,gamePlayMode);
            case "6" -> highlightLegalMoves(game,scanner);
        }
    }


    public void authMenuActions(Scanner scanner, String[] command, String auth[], boolean[] gamePlayMode) throws ResponseException, IOException {
        switch (command[0]) {
            case "1" -> handleHelpActionAuth();
            case "2" -> handleLogoutAction(auth);
            case "3" -> handleCreateGameAction(scanner, auth);
            case "4" -> handleListGamesAction(scanner, auth);
            case "5" -> handleJoinGameAction(scanner,auth, gamePlayMode);
            case "6" -> handleJoinGameObserverAction(scanner,auth, gamePlayMode);
        }
    }

    private void handleJoinGameAction(Scanner scanner, String[] auth, boolean[] gamePlayMode) throws ResponseException, IOException {
        String playerColor;
        System.out.print("Player Color (WHITE/BLACK): ");
        playerColor = scanner.nextLine();
        int gameID;
        System.out.println("Which Game Number Do You Want To Join: ");
        gameID = scanner.nextInt();
        JoinGameRequest request = new JoinGameRequest(auth[0],playerColor,gameID);
        gameIDTemp[0] = gameID;
        try{
            JoinGameResult result = serverFacade.serverJoinGame(request,auth);
            webSocketFacade.joinGame(gameID,auth,user[0]);
            gamePlayMode[0] = true;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleJoinGameObserverAction(Scanner scanner, String[] auth, boolean[] gamePlayMode) {
        int gameID;
        System.out.println("Which Game Number Do You Want To Join: ");
        gameID = scanner.nextInt();
        JoinGameRequest request = new JoinGameRequest(auth[0],"",gameID);
        gameIDTemp[0] = gameID;
        try{
            JoinGameResult result = serverFacade.serverJoinGame(request,auth);
            webSocketFacade.joinObserver(gameID,auth);
            System.out.println("Game: " + result.gameID() + " has been joined as an observer");
            //chessBoard.printChessBoard(game.getBoard(),validMoves);
            gamePlayMode[0] = true;
        }
        catch (ResponseException ex){
            System.out.println(ex.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void resign(ChessGame game, String[] auth, boolean[] gamePlayMode) throws IOException {
        webSocketFacade.resign(gameIDTemp[0],auth);
        gamePlayMode[0] = false;

    }
    public void makeMove(ChessGame game, Scanner scanner, String[] auth) throws InvalidMoveException, IOException {
        Scanner scanner1 = new Scanner(System.in);
        String startPosition;
        String endPosition;
        System.out.println("Starting Position? (letter-number");
        startPosition = scanner1.nextLine();
        System.out.println("Ending Position? (letter-number)");
        endPosition = scanner1.nextLine();
        int startCol =0;
        int endCol = 0;
        switch (startPosition.substring(0,1)){
            case "a"  -> startCol = 1;case "b" -> startCol = 2;case "c" -> startCol = 3;case "d" -> startCol = 4;
            case "e" -> startCol = 5;case "f" -> startCol = 6;case "g" -> startCol = 7;case "h" -> startCol = 8;
        }
        switch (endPosition.substring(0,1)){
            case "a" -> endCol = 1;case "b" -> endCol = 2;case "c" -> endCol = 3;case "d" -> endCol = 4;
            case "e" -> endCol = 5;case "f" -> endCol = 6;case "g" -> endCol = 7;case "h" -> endCol = 8;
        }

        ChessPosition startChessPosition = new ChessPosition(Integer.parseInt(startPosition.substring(1, 2)),startCol);
        ChessPosition endChessPosition = new ChessPosition(Integer.parseInt(endPosition.substring(1,2)),endCol);

        ChessMove newMove = new ChessMove(startChessPosition,endChessPosition,null);
        webSocketFacade.makeMove(gameIDTemp[0],auth,newMove);
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

    public void redrawChessBoard(){
        boolean[][] validMoves = validMoveInit();
        chessBoard.printChessBoard(this.game.getBoard(),validMoves);
    }
    public void highlightLegalMoves(ChessGame game, Scanner scanner){
        Scanner scanner1 = new Scanner(System.in);
        String startPosition;
        System.out.println("Starting Position? (letter-number");
        startPosition = scanner1.nextLine();
        int startCol =0;
        switch (startPosition.substring(0,1)){
            case "a"  -> startCol = 1;case "b" -> startCol = 2;case "c" -> startCol = 3;case "d" -> startCol = 4;
            case "e" -> startCol = 5;case "f" -> startCol = 6;case "g" -> startCol = 7;case "h" -> startCol = 8;
        }

        ChessPosition startChessPosition = new ChessPosition(Integer.parseInt(startPosition.substring(1, 2)),startCol);
        boolean[][] validMoves = validMoveInit();

        Collection<ChessMove> moves = game.validMoves(startChessPosition);

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
        System.out.println("Logout -> Logs You Out");
        System.out.println("Create Game -> provide a game name to create a game");
        System.out.println("List Games -> Lists all of the games in the application");
        System.out.println("Join Game -> Enter a color and a game number to join");
        System.out.println("Join Observer -> Enter a game number to join as an observer\n");
    }

    public void leave(ChessGame game, Scanner scanner, String[] auth, boolean[] gamePlayMode) throws ResponseException, IOException {
        Scanner scanner1 = new Scanner(System.in);
        webSocketFacade.leave(gameIDTemp[0],auth);
        gamePlayMode[0] = false;
    }

    public void displayNotification(String message){
        if (message != null) {
            System.out.println(message);
        }
    }

    public void displayError(String message){
        ErrorMessage errorMessage = new Gson().fromJson(message, ErrorMessage.class);

        System.out.println(errorMessage.getErrorMessage());
    }
    public void displayLoadGame(String message){
        ChessGame chessGame = new Gson().fromJson(message, ChessGame.class);
        boolean[][] validMoves = validMoveInit();
        this.game = chessGame;
        chessBoard.printChessBoard(this.game.getBoard(),validMoves);
    }

    @Override
    public void notify(String message) {
        ServerMessage serverMessage = new Gson().fromJson(message,ServerMessage.class);
        String messageString;
        if (serverMessage.getServerMessageType().equals(ServerMessage.ServerMessageType.NOTIFICATION)){
            NotificationMessage newMessage = new Gson().fromJson(message, NotificationMessage.class);
            messageString = newMessage.getNotificationMessage();
            displayNotification(messageString);
        }
        else if (serverMessage.getServerMessageType().equals(ServerMessage.ServerMessageType.LOAD_GAME)){
            LoadGameMessage newMessage1 = new Gson().fromJson(message, LoadGameMessage.class);

            messageString = new Gson().toJson(newMessage1.getGame());
            displayLoadGame(messageString);
        }
        else {
            ErrorMessage newMessage2 = new Gson().fromJson(message, ErrorMessage.class);
            messageString = new Gson().toJson(newMessage2);
            displayError(messageString);

        }
    }
}