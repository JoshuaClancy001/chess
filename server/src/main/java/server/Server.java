package server;

import Handlers.ClearApplicationHandler;
import Handlers.LoginHandler;
import Handlers.RegisterHandler;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.

        Spark.post("/user", ((request, response) ->
                (RegisterHandler.getInstance().handleRegisterRequest(request,response))));

        Spark.post("/session", ((request, response) ->
                (new LoginHandler().handleLoginRequest(request,response))));

        Spark.delete("/db", ((request,response) ->
                (new ClearApplicationHandler().handleClearApplicationRequest(request,response))));

        Spark.init();
        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
