package Handlers;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import service.LoginService;
import Request.LoginRequest;
import Result.LoginResult;
import spark.Request;
import spark.Response;

public class LoginHandler {
    public String handleLoginRequest(Request req, Response res) throws DataAccessException {
        try {
            Gson gson = new Gson();
            LoginRequest request = gson.fromJson(req.body(), LoginRequest.class);

            LoginService service = new LoginService(request);
            LoginResult result = service.login(request);

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
