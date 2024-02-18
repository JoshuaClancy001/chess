import service.ClearApplicationService;
import service.Request.RegisterRequest;
import chess.*;
import dataAccess.DataAccessException;
import service.RegistrationService;

public class Main {
    public static void main(String[] args) throws DataAccessException {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Server: " + piece);
        RegisterRequest registerRequest = new RegisterRequest("username","password","email");
        RegisterRequest registerRequest2 = new RegisterRequest("username2","password2","email2");


        RegistrationService registrationService = new RegistrationService(registerRequest);
        ClearApplicationService clearApplicationService = new ClearApplicationService();
        registrationService.register(registerRequest);
        registrationService.register(registerRequest2);
        clearApplicationService.clearApplication();


    }
}