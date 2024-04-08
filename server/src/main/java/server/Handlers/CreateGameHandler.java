package server.Handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import service.CreateGamesService;
import Request.CreateGameRequest;
import Result.CreateGameResult;
import spark.Request;
import spark.Response;

public class CreateGameHandler {

    public String handleCreateGameRequest(Request req, Response res) throws DataAccessException {
        try {
            Gson gson = new Gson();
            CreateGameRequest request = gson.fromJson(req.body(), CreateGameRequest.class);

            String authToken = req.headers("Authorization");

            CreateGamesService service = new CreateGamesService(request);
            CreateGameResult result = service.createGame(authToken,request);

            res.body(gson.toJson(result));
            res.status(200);
            return gson.toJson(result);
        }
        catch (DataAccessException e){
            if (e.getMessage().equals("Unauthorized")){
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
