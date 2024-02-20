package Handlers;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import service.RegistrationService;
import service.Request.RegisterRequest;
import service.Result.RegisterResult;

public class RegisterHandler {
    public String handleRegisterRequest(String reqData) throws DataAccessException {
        Gson gson = new Gson();
        RegisterRequest request = gson.fromJson(reqData, RegisterRequest.class);

        RegistrationService service = new RegistrationService(request);
        RegisterResult result = service.register(request);


        return gson.toJson(result);
    }
}
