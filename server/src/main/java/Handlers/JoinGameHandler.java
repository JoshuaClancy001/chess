package Handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import service.JoinGamesService;
import Request.JoinGameRequest;
import Result.JoinGameResult;
import spark.Request;
import spark.Response;

public class JoinGameHandler {

    public String handleJoinGameRequest(Request req, Response res) throws DataAccessException {
        try {
            Gson gson = new Gson();
            JoinGameRequest request = gson.fromJson(req.body(), JoinGameRequest.class);

            String authToken = req.headers("Authorization");

            JoinGamesService service = new JoinGamesService(request);
            JoinGameResult result = service.joinGame(authToken,request);

            res.body(gson.toJson(result));
            res.status(200);
            return gson.toJson(result);
        }
        catch (DataAccessException e){
            if (e.getMessage().equals("Unauthorized")){
                res.status(401);
                return "{ \"message\": \"Error: unauthorized\" }";
            }
            else if (e.getMessage().equals("already taken")){
                res.status(403);
                return "{ \"message\": \"Error: already taken\" }";
            }
            else if (e.getMessage().equals("no gameID")){
                res.status(400);
                return "{ \"message\": \"Error: bad request\" }";
            }
            else{
                res.status(501);
                return "{ \"message\": \"Error: description\" }";
            }
        }

        //String authTOken = req.headers("Authorization");

    }
}
