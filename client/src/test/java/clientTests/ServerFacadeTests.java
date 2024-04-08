package clientTests;

import Request.*;
import Result.*;
import model.GameData;
import org.junit.jupiter.api.*;
import server.Server;
import ui.Exception.ResponseException;
import ui.ServerFacade;

import java.util.ArrayList;


public class ServerFacadeTests {

    private static Server server;
    private String[] auth = {""};

    ServerFacade serverFacade = new ServerFacade("http://localhost:8080");

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @BeforeEach
    void setUp() throws ResponseException {
        serverFacade.serverClearApplication(auth);
    }

// Normal Register
    @Test
    public void RegisterFacadePositive() throws ResponseException {
        String actual;
        String expected = "username";
        RegisterRequest request = new RegisterRequest("username","password","email");
        RegisterResult result = serverFacade.serverRegister(request,auth);
        actual = result.username();
        auth[0] = result.authToken();
        Assertions.assertEquals(expected,actual);

    }
    // Register Duplicate User
    @Test
    public void RegisterFacadeNegative() throws ResponseException{
        RegisterRequest request = new RegisterRequest("username","password","email");
        RegisterResult result = serverFacade.serverRegister(request,auth);
        Assertions.assertThrows(ResponseException.class, ()->serverFacade.serverRegister(request,auth));
    }
    // Login Registered User
    @Test
    public void LoginFacadePositive() throws ResponseException{
        String expected = "username";
        RegisterRequest request = new RegisterRequest("username","password","email");
        RegisterResult result = serverFacade.serverRegister(request,auth);
        LoginRequest request1 = new LoginRequest("username","password");
        LoginResult result1 = serverFacade.serverLogin(request1,auth);
        String actual = result1.username();
        Assertions.assertEquals(expected,actual);
    }

    // Login with wrong password
    @Test
    public void LoginFacadeNegative() throws ResponseException{
        RegisterRequest request = new RegisterRequest("username","password","email");
        RegisterResult result = serverFacade.serverRegister(request,auth);
        LoginRequest request1 = new LoginRequest("username","wrong_password");
        Assertions.assertThrows(ResponseException.class,()->serverFacade.serverLogin(request1,auth));
    }

    @Test
    public void LogoutFacadePositive() throws ResponseException {
        RegisterRequest request = new RegisterRequest("username", "password", "email");
        RegisterResult result = serverFacade.serverRegister(request, auth);
        auth[0] = result.authToken();
        String expected = "";
        LogoutRequest request1 = new LogoutRequest(auth[0]);
        Assertions.assertDoesNotThrow(() -> serverFacade.serverLogout(request1, auth));
    }
    @Test
    void LogoutFacadeNegative() throws ResponseException {
        RegisterRequest request = new RegisterRequest("username", "password", "email");
        RegisterResult result = serverFacade.serverRegister(request, auth);
        auth[0] = result.authToken();
        String expected = "";
        String[] wrong = {""};
        LogoutRequest request1 = new LogoutRequest(auth[0]);
        Assertions.assertThrows(ResponseException.class, ()->serverFacade.serverLogout(request1,wrong));
    }
    // create game successfully
    @Test
    void CreateGamePositive() throws ResponseException {
        int expected = 1;
        RegisterRequest request = new RegisterRequest("username", "password", "email");
        RegisterResult result = serverFacade.serverRegister(request, auth);
        auth[0] = result.authToken();
        CreateGameRequest request1 = new CreateGameRequest(auth[0],"game1");
        CreateGameResult result1 = serverFacade.serverCreateGame(request1, auth);
        int actual = result1.gameID();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void CreateGameNegative() throws ResponseException {
        RegisterRequest request = new RegisterRequest("username", "password", "email");
        RegisterResult result = serverFacade.serverRegister(request, auth);
        auth[0] = result.authToken();
        String[] wrong = {"wrong"};
        CreateGameRequest request1 = new CreateGameRequest(auth[0],"game1");
        Assertions.assertThrows(ResponseException.class,()->serverFacade.serverCreateGame(request1,wrong));
    }

    @Test
    void ListGamesPositive() throws ResponseException {
        RegisterRequest request = new RegisterRequest("username", "password", "email");
        RegisterResult result = serverFacade.serverRegister(request, auth);
        auth[0] = result.authToken();
        String expected = "game1";
        CreateGameRequest request1 = new CreateGameRequest(auth[0],"game1");
        CreateGameResult  result1 = serverFacade.serverCreateGame(request1,auth);
        ListGamesRequest request2 = new ListGamesRequest(auth[0]);
        ListGamesResult result2 = serverFacade.serverListGames(request2,auth);
        ArrayList<GameData> games = result2.games();
        String actual = games.get(0).getGameName();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void ListGamesNegative() throws ResponseException {
        RegisterRequest request = new RegisterRequest("username", "password", "email");
        RegisterResult result = serverFacade.serverRegister(request, auth);
        auth[0] = result.authToken();
        String expected = "game1";
        String[] wrong = {"wrong"};
        CreateGameRequest request1 = new CreateGameRequest(auth[0],"game1");
        CreateGameResult  result1 = serverFacade.serverCreateGame(request1,auth);
        ListGamesRequest request2 = new ListGamesRequest(auth[0]);
        Assertions.assertThrows(ResponseException.class,()->serverFacade.serverListGames(request2,wrong));
    }

    @Test
    void JoinGamePositive() throws ResponseException {
        RegisterRequest request = new RegisterRequest("username", "password", "email");
        RegisterResult result = serverFacade.serverRegister(request, auth);
        auth[0] = result.authToken();
        int expected = 1;
        CreateGameRequest request1 = new CreateGameRequest(auth[0],"game1");
        CreateGameResult  result1 = serverFacade.serverCreateGame(request1,auth);
        JoinGameRequest request2 = new JoinGameRequest(auth[0],"WHITE",1);
        JoinGameResult result2 = serverFacade.serverJoinGame(request2,auth);
        int actual = result1.gameID();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void JoinGameNegative() throws ResponseException{
        RegisterRequest request = new RegisterRequest("username", "password", "email");
        RegisterResult result = serverFacade.serverRegister(request, auth);
        auth[0] = result.authToken();
        int expected = 1;
        CreateGameRequest request1 = new CreateGameRequest(auth[0],"game1");
        CreateGameResult  result1 = serverFacade.serverCreateGame(request1,auth);
        JoinGameRequest request2 = new JoinGameRequest(auth[0],"WHITE",2);
        Assertions.assertThrows(ResponseException.class,()->serverFacade.serverJoinGame(request2,auth));
    }

    @Test
    void ClearApplicationPositive() throws ResponseException {
        String actual;
        String expected = "";
        RegisterRequest request = new RegisterRequest("username","password","email");
        RegisterResult result = serverFacade.serverRegister(request,auth);
        auth[0] = result.authToken();
        serverFacade.serverClearApplication(auth);
        actual = auth[0];
        Assertions.assertEquals(expected, actual);
    }


}
