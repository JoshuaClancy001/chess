import server.Server;
import chess.*;
import dataAccess.DataAccessException;

public class Main {
    public static void main(String[] args) throws DataAccessException {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Server: " + piece);

        new Server().run(8080);
/*
        RegisterRequest registerRequest = new RegisterRequest("username","password","email");
        RegisterRequest registerRequest2 = new RegisterRequest("username2","password2","email2");
        RegistrationService registrationService = new RegistrationService(registerRequest);
        //ClearApplicationService clearApplicationService = new ClearApplicationService();
        RegisterResult registerResult = registrationService.register(registerRequest);
        registrationService.register(registerRequest2);
        //clearApplicationService.clearApplication();
        */

    }
}