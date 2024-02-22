package Handlers;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import service.LoginService;
import service.RegistrationService;
import service.Request.LoginRequest;
import service.Request.RegisterRequest;
import service.Result.LoginResult;
import service.Result.RegisterResult;
import spark.Request;
import spark.Response;

public class LoginHandler {
    public String handleLoginRequest(Request req, Response res) throws DataAccessException {
        Gson gson = new Gson();
        LoginRequest request = gson.fromJson(req.body(), LoginRequest.class);

        LoginService service = new LoginService(request);
        LoginResult result = service.login(request);

        res.body(gson.toJson(result));

        return gson.toJson(result);

        //String authTOken = req.headers("Authorization");

    }
}
