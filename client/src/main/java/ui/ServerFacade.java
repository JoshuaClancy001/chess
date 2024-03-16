package ui;

import dataAccess.DataAccessException;
import server.Request.CreateGameRequest;
import server.Request.ListGamesRequest;
import server.Request.LoginRequest;
import server.Request.RegisterRequest;
import server.Result.CreateGameResult;
import server.Result.ListGamesResult;
import server.Result.LoginResult;
import server.Result.RegisterResult;
import ui.Exception.ResponseException;

public class ServerFacade {

    private final String serverUrl;

    public ServerFacade(String url) {
        serverUrl = url;
    }

    public RegisterResult serverRegister(RegisterRequest request,String[] auth) throws ResponseException {
        try {
            ClientCommunicator clientCommunicator = new ClientCommunicator();
            var path = "/user";
            return clientCommunicator.doPost(serverUrl, path, request, RegisterResult.class, auth);
        }
        catch (ResponseException ex){
            throw new ResponseException(ex.StatusCode(), ex.getMessage());

        }
    }

    public LoginResult serverLogin(LoginRequest request, String[] auth) throws ResponseException {
        try{
            ClientCommunicator clientCommunicator = new ClientCommunicator();
            var path = "/session";
            return clientCommunicator.doPost(serverUrl,path,request, LoginResult.class, auth);
        }
        catch (ResponseException ex){
            throw new ResponseException(ex.StatusCode(),ex.getMessage());
        }
    }

    public void serverLogout() throws ResponseException{}

    public CreateGameResult serverCreateGame(CreateGameRequest request,String[] auth) throws ResponseException{
        try{
            ClientCommunicator clientCommunicator = new ClientCommunicator();
            var path = "/game";
            return clientCommunicator.doPost(serverUrl,path,request, CreateGameResult.class, auth);
        }
        catch (ResponseException ex){
            throw new ResponseException(ex.StatusCode(), ex.getMessage());
        }
    }

    public ListGamesResult serverListGames(ListGamesRequest request,String[] auth) throws ResponseException{
        try{
            ClientCommunicator clientCommunicator = new ClientCommunicator();
            var path = "/game";
            return ClientCommunicator.doGet(serverUrl,path,request, ListGamesResult.class, auth);
        }
        catch (ResponseException ex){
            throw new ResponseException(ex.StatusCode(),ex.getMessage());
        }
    }
    public void serverClearApplication() throws ResponseException{
        try {
            ClientCommunicator clientCommunicator = new ClientCommunicator();
            var path = "/db";
            clientCommunicator.doDelete(serverUrl, path);
        }
        catch (ResponseException ex){
            throw new ResponseException(ex.StatusCode(),ex.getMessage());
        }
    }
    public void serverJoinGame() throws ResponseException{}



}
