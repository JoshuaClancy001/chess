package server.Handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import service.LogoutService;
import server.Request.LogoutRequest;
import server.Result.LogoutResult;
import spark.Request;
import spark.Response;

public class LogoutHandler {

    private static LogoutHandler instance;

    public String handleLogoutRequest(Request req, Response res)throws DataAccessException {
        try {
            Gson gson = new Gson();
            LogoutRequest request = gson.fromJson(req.body(), LogoutRequest.class);

            String authToken = req.headers("Authorization");

            LogoutService service = new LogoutService(request);
            LogoutResult result = service.logout(request, authToken);

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
                res.status(500);
                return "{ \"message\": \"Error: description\" }";
            }
        }

    }
}
