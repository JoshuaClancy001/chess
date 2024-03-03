package server.Handlers;
import dataAccess.DataAccessException;
import service.ClearApplicationService;
import spark.Request;
import spark.Response;


public class ClearApplicationHandler {
    public Object handleClearApplicationRequest(Request req, Response res) throws DataAccessException {
        ClearApplicationService service = new ClearApplicationService();
        service.clearApplication();
        res.status(200);
        return "{}";

    }
}
