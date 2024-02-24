package server.Handlers;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import service.RegistrationService;
import server.Request.RegisterRequest;
import server.Result.RegisterResult;
import spark.Request;
import spark.Response;

public class RegisterHandler {

    private static RegisterHandler instance;
    public String handleRegisterRequest(Request req, Response res) throws DataAccessException {

        try {
            Gson gson = new Gson();
            RegisterRequest request = gson.fromJson(req.body(), RegisterRequest.class);

            RegistrationService service = new RegistrationService(request);
            RegisterResult result = service.register(request);


            res.body(gson.toJson(result));
            res.status(200);

            return gson.toJson(result);
        }
        catch(DataAccessException e){
            if (e.getMessage().equals("User already exists")) {
                res.status(403);
                return "{ \"message\": \"Error: already taken\" }";
            }
            else if (e.getMessage().equals("bad request")){
                res.status(400);
                return "{ \"message\": \"Error: bad request\" }";
            }
            else {
                res.status(500);
                return "{ \"message\": \"Error: description\" }";
            }
        }

        //String authTOken = req.headers("Authorization");
    }

    public static RegisterHandler getInstance(){
        if (instance == null){
            instance = new RegisterHandler();
        }
        return instance;
    }

}
