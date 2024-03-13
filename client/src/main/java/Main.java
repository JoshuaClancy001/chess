import chess.*;
import ui.ClientChessBoard;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        ClientChessBoard chessBoard = new ClientChessBoard();
        chessBoard.printChessBoard();
    }
}