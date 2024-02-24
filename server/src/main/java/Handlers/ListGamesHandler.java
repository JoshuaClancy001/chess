package Handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import service.ListGamesService;
import service.Request.ListGamesRequest;
import service.Result.ListGamesResult;
import spark.Request;
import spark.Response;

import java.awt.*;

public class ListGamesHandler {

    public String handleListGamesRequest(Request req, Response res) throws DataAccessException {
        try {
            Gson gson = new Gson();
            ListGamesRequest request = gson.fromJson(req.body(), ListGamesRequest.class);

            String authToken = req.headers("Authorization");

            ListGamesService service = new ListGamesService(request);
            ListGamesResult result = service.listGames(authToken,request);

            res.body(gson.toJson(result));
            res.status(200);
            return gson.toJson(result);
        }
        catch (DataAccessException e){
            if (e.getMessage().equals("User not Registered")){
                res.status(401);
                return "{ \"message\": \"Error: unauthorized\" }";
            }
            else if (e.getMessage().equals("Wrong Password")){
                res.status(401);
                return "{ \"message\": \"Error: unauthorized\" }";
            }
            else{
                res.status(501);
                return "{ \"message\": \"Error: description\" }";
            }
        }

        //String authTOken = req.headers("Authorization");

    }
}
