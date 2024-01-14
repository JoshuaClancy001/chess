package chess;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class MovementRules {

    public MovementRules(){

    }

    public ArrayList<ChessMove> KingMoves(ChessBoard board, ChessPosition ogPosition){
        ArrayList<ChessMove> newPossiblePositions = new ArrayList<>();

        ArrayList<ChessPosition> newkingPositions = new ArrayList<>();

        newkingPositions.add(new ChessPosition(ogPosition.row,ogPosition.col+1));
        newkingPositions.add(new ChessPosition(ogPosition.row, ogPosition.col - 1));
        newkingPositions.add(new ChessPosition(ogPosition.row + 1, ogPosition.col));
        newkingPositions.add(new ChessPosition(ogPosition.row + 1, ogPosition.col + 1));
        newkingPositions.add(new ChessPosition(ogPosition.row + 1, ogPosition.col - 1));
        newkingPositions.add(new ChessPosition(ogPosition.row - 1, ogPosition.col));
        newkingPositions.add(new ChessPosition(ogPosition.row - 1, ogPosition.col + 1));
        newkingPositions.add(new ChessPosition(ogPosition.row - 1, ogPosition.col + -1));

    for (ChessPosition i : newkingPositions) {

        if (i.row < 1 || i.row > 8 || i.col < 1 || i.col > 8){
            System.out.println("Off the board");
        }
        else if (board.getPiece(i) != null) {
            if (board.getPiece(ogPosition).getTeamColor().equals(board.getPiece(new ChessPosition(i.row, i.col)).getTeamColor())) {
                System.out.println("That is your piece");
            }
            else if (board.getPiece(ogPosition).getTeamColor() != board.getPiece(i).getTeamColor()){
                System.out.println("That is an enemy piece");
                newPossiblePositions.add(new ChessMove(ogPosition, i, null));//right one
            }

        }
        else {
            newPossiblePositions.add(new ChessMove(ogPosition, i, null));//right one
        }
    }


        return newPossiblePositions;}
    public ArrayList<ChessMove> QueenMoves(ChessBoard board, ChessPosition ogPosition){return null;}
    public ArrayList<ChessMove> BishopMoves(ChessBoard board, ChessPosition ogPosition){return null;}
    public ArrayList<ChessMove> KnightMoves(ChessBoard board, ChessPosition ogPosition){return null;}
    public ArrayList<ChessMove> RookMoves(ChessBoard board, ChessPosition ogPosition){return null;}
    public ArrayList<ChessMove> PawnMoves(ChessBoard board, ChessPosition ogPosition){return null;}
}
