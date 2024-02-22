package Handlers;
import com.google.gson.Gson;
import service.ClearApplicationService;
import service.RegistrationService;
import service.Request.RegisterRequest;
import service.Result.RegisterResult;
import spark.Request;
import spark.Response;


public class ClearApplicationHandler {
    public Object handleClearApplicationRequest(Request req, Response res){
        ClearApplicationService service = new ClearApplicationService();
        service.clearApplication();
        res.status(200);
        return "{}";

    }
}
