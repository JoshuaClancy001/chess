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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    ServerFacade serverFacade;
    private ArrayList<UserData> user = new ArrayList<>();
    private ArrayList<AuthData> auth = new ArrayList<>();
    private ClientChessBoard chessBoard = new ClientChessBoard();
    private final String serverUrl;

    public Main(String serverUrl) {
        serverFacade = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
    }

    public void main(String[] args) throws ResponseException {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        Run();
    }
    public void Run() throws ResponseException {
        Main main = new Main(this.serverUrl);
        Scanner scanner = new Scanner(System.in);
        boolean[] hasExited = {false};
        String[] auth = {""};
        String[] command = {""};
        System.out.println("Welcome to 240 Chess. Press enter to get started");
        scanner.nextLine();
        while(!hasExited[0]) {
            if (!auth[0].equals("")) {
                main.printAuthMenu(scanner, command);
                main.authMenuActions(scanner,command,auth);
            } else {
                main.printNormMenu(scanner, command);
                main.normMenuActions(scanner, command, hasExited, auth);
            }
        }
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

    public void authMenuActions(Scanner scanner, String[] command, String auth[]) throws ResponseException {
        switch (command[0]) {
            case "1" -> handleHelpActionAuth();
            case "2" -> handleLogoutAction(auth);
            case "3" -> handleCreateGameAction(scanner, auth);
            case "4" -> handleListGamesAction(scanner, auth);
            case "5" -> handleJoinGameAction(scanner,auth);
            case "6" -> handleJoinGameObserverAction(scanner,auth);
        }
    }

    private void handleJoinGameAction(Scanner scanner, String[] auth) throws ResponseException{
        String playerColor;
        System.out.print("Player Color (WHITE/BLACK): ");
        playerColor = scanner.nextLine();
        int gameID;
        System.out.println("Which Game Number Do You Want To Join: ");
        gameID = scanner.nextInt();
        JoinGameRequest request = new JoinGameRequest(auth[0],playerColor,gameID);
        try{
            JoinGameResult result = serverFacade.serverJoinGame(request,auth);
            System.out.println("Game: " + result.gameID() + " has been joined as " + playerColor);
            chessBoard.printChessBoard();
        }
        catch (ResponseException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void handleJoinGameObserverAction(Scanner scanner, String[] auth) {
        int gameID;
        System.out.println("Which Game Number Do You Want To Join: ");
        gameID = scanner.nextInt();
        JoinGameRequest request = new JoinGameRequest(auth[0],"",gameID);
        try{
            JoinGameResult result = serverFacade.serverJoinGame(request,auth);
            System.out.println("Game: " + result.gameID() + " has been joined as an observer");
            chessBoard.printChessBoard();
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

    private static void handleLogoutAction(String[] auth) {
        auth[0] = "";
    }

    private static void handleHelpActionAuth() {
        System.out.println("You are being helped");
    }
}