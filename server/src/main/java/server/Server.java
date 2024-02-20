package server;

import Handlers.RegisterHandler;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.

        Spark.post("/user", ((request, response) ->
                (new RegisterHandler().handleRegisterRequest("{ \"username\": \"john_doe\", \"password\": \"password123\", \"email\": \"john@example.com\" }"))));
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
