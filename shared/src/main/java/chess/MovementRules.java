package chess;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class MovementRules {

    public MovementRules() {

    }

    private static boolean move(ChessBoard board, ChessPosition ogPosition, ArrayList<ChessMove> newPossiblePositions, ChessPosition newPosition) {
        if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
            ;
        }
        else if(board.getPiece(newPosition) != null){
            if(board.getPiece(ogPosition).getTeamColor() == board.getPiece(newPosition).getTeamColor()){
                return true;
            }
            else{
                newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
                return true;
            }
        }
        else{
            newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
        }
        return false;
    }


    public void rightMove(int range,ChessBoard board,ChessPosition ogPosition, ArrayList<ChessMove> newPossiblePositions){
        for (int i = 1; i < range; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow(), ogPosition.getColumn() + i);
            if (move(board, ogPosition, newPossiblePositions, newPosition)) break;
        }
    }
    public void leftMove(int range,ChessBoard board,ChessPosition ogPosition, ArrayList<ChessMove> newPossiblePositions){
        for (int i = 1; i < range; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow(), ogPosition.getColumn() - i);
            if (move(board, ogPosition, newPossiblePositions, newPosition)) break;
        }
    }
    public void upMove(int range,ChessBoard board,ChessPosition ogPosition, ArrayList<ChessMove> newPossiblePositions){
        for (int i = 1; i < range; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow()+i, ogPosition.getColumn());
            if (move(board, ogPosition, newPossiblePositions, newPosition)) break;
        }
    }
    public void downMove(int range,ChessBoard board,ChessPosition ogPosition, ArrayList<ChessMove> newPossiblePositions){
        for (int i = 1; i < range; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow()-i, ogPosition.getColumn());
            if (move(board, ogPosition, newPossiblePositions, newPosition)) break;
        }
    }
    public void upRightMove(int range,ChessBoard board,ChessPosition ogPosition, ArrayList<ChessMove> newPossiblePositions){
        for (int i = 1; i < range; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow()+i, ogPosition.getColumn() + i);
            if (move(board, ogPosition, newPossiblePositions, newPosition)) break;
        }
    }
    public void upLeftMove(int range,ChessBoard board,ChessPosition ogPosition, ArrayList<ChessMove> newPossiblePositions){
        for (int i = 1; i < range; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow()+i, ogPosition.getColumn() - i);
            if (move(board, ogPosition, newPossiblePositions, newPosition)) break;
        }
    }
    public void downRightMove(int range,ChessBoard board,ChessPosition ogPosition, ArrayList<ChessMove> newPossiblePositions){
        for (int i = 1; i < range; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow()-i, ogPosition.getColumn() + i);
            if (move(board, ogPosition, newPossiblePositions, newPosition)) break;
        }
    }
    public void downLeftMove(int range,ChessBoard board,ChessPosition ogPosition, ArrayList<ChessMove> newPossiblePositions){
        for (int i = 1; i < range; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow()-i, ogPosition.getColumn() - i);
            if (move(board, ogPosition, newPossiblePositions, newPosition)) break;
        }
    }
    public void knightMove(int i,int j, ChessBoard board,ChessPosition ogPosition,ArrayList<ChessMove> newPossiblePositions){
        ChessPosition newPosition = new ChessPosition(ogPosition.getRow() +i , ogPosition.getColumn() + j);
        if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
            ;
        }
        else if(board.getPiece(newPosition) != null){
            if(board.getPiece(ogPosition).getTeamColor() == board.getPiece(newPosition).getTeamColor()){

            }
            else{
                newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
            }
        }
        else{
            newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
        }
    }
    public ArrayList<ChessMove> kingMoves(ChessBoard board, ChessPosition ogPosition) {
        ArrayList<ChessMove> newPossiblePositions = new ArrayList<>();

        rightMove(2,board,ogPosition,newPossiblePositions);
        leftMove(2,board,ogPosition,newPossiblePositions);
        upMove(2,board,ogPosition,newPossiblePositions);
        downMove(2,board,ogPosition,newPossiblePositions);
        upRightMove(2,board,ogPosition,newPossiblePositions);
        upLeftMove(2,board,ogPosition,newPossiblePositions);
        downRightMove(2,board,ogPosition,newPossiblePositions);
        downLeftMove(2,board,ogPosition,newPossiblePositions);


        return newPossiblePositions;
    }
    public ArrayList<ChessMove> queenMoves(ChessBoard board, ChessPosition ogPosition) {
        ArrayList<ChessMove> newPossiblePositions = new ArrayList<>();

        rightMove(9,board,ogPosition,newPossiblePositions);
        leftMove(9,board,ogPosition,newPossiblePositions);
        upMove(9,board,ogPosition,newPossiblePositions);
        downMove(9,board,ogPosition,newPossiblePositions);
        upRightMove(9,board,ogPosition,newPossiblePositions);
        upLeftMove(9,board,ogPosition,newPossiblePositions);
        downRightMove(9,board,ogPosition,newPossiblePositions);
        downLeftMove(9,board,ogPosition,newPossiblePositions);

        return newPossiblePositions;

    }
    public ArrayList<ChessMove> bishopMoves(ChessBoard board, ChessPosition ogPosition){
        ArrayList<ChessMove> newPossiblePositions = new ArrayList<>();

        ArrayList<ChessPosition> newBishopPositions = new ArrayList<>();

        upRightMove(9,board,ogPosition,newPossiblePositions);
        upLeftMove(9,board,ogPosition,newPossiblePositions);
        downRightMove(9,board,ogPosition,newPossiblePositions);
        downLeftMove(9,board,ogPosition,newPossiblePositions);

        return newPossiblePositions;}
    public ArrayList<ChessMove> knightMoves(ChessBoard board, ChessPosition ogPosition){
        ArrayList<ChessMove> newPossiblePositions = new ArrayList<>();

        knightMove(2,1,board,ogPosition,newPossiblePositions);// up 2 right 1
        knightMove(2,-1,board,ogPosition,newPossiblePositions);// up 2 left 1
        knightMove(-2,1,board,ogPosition,newPossiblePositions);// down 2 right 1
        knightMove(-2,-1,board,ogPosition,newPossiblePositions);// down 2 left 1
        knightMove(1,2,board,ogPosition,newPossiblePositions);// up 1 right 2
        knightMove(1,-2,board,ogPosition,newPossiblePositions);// up 1 left 2
        knightMove(-1,2,board,ogPosition,newPossiblePositions);// down 1 right 2
        knightMove(-1,-2,board,ogPosition,newPossiblePositions);// down 1 left 2



        return newPossiblePositions;}
    public ArrayList<ChessMove> rookMoves(ChessBoard board, ChessPosition ogPosition) {

        ArrayList<ChessMove> newPossiblePositions = new ArrayList<>();

        rightMove(9,board,ogPosition,newPossiblePositions);
        leftMove(9,board,ogPosition,newPossiblePositions);
        upMove(9,board,ogPosition,newPossiblePositions);
        downMove(9,board,ogPosition,newPossiblePositions);



        return newPossiblePositions;}
    public ArrayList<ChessMove> pawnMoves(ChessBoard board, ChessPosition ogPosition){
        ArrayList<ChessMove> newPossiblePositions = new ArrayList<>();ChessPosition newPosition;ChessPosition newPositionAtStart;ChessPosition enemeyToTheRight;ChessPosition enemeyToTheLeft;int startingRow;
        if (board.getPiece(ogPosition).getTeamColor() == ChessGame.TeamColor.WHITE){
            newPosition = new ChessPosition(ogPosition.getRow() + 1 , ogPosition.getColumn());
            newPositionAtStart = new ChessPosition(ogPosition.getRow() + 2,ogPosition.getColumn());
            enemeyToTheRight = new ChessPosition(ogPosition.getRow() + 1 , ogPosition.getColumn()+1);enemeyToTheLeft = new ChessPosition(ogPosition.getRow() + 1 , ogPosition.getColumn() - 1);startingRow = 2;
        }
        else {
            newPosition = new ChessPosition(ogPosition.getRow() - 1, ogPosition.getColumn());
            newPositionAtStart = new ChessPosition(ogPosition.getRow() - 2, ogPosition.getColumn());
            enemeyToTheRight = new ChessPosition(ogPosition.getRow() - 1 , ogPosition.getColumn()+1);enemeyToTheLeft = new ChessPosition(ogPosition.getRow() - 1 , ogPosition.getColumn() - 1);startingRow = 7;
        }
        if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
        }
        else if (ogPosition.getRow() == startingRow){
            if (board.getPiece(newPosition) == null) {
                newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
                if (board.getPiece(newPositionAtStart) == null){
                    newPossiblePositions.add(new ChessMove(ogPosition, newPositionAtStart, null));
                }
                if (enemeyToTheLeft.getRow() < 1 || enemeyToTheLeft.getRow() > 8 || enemeyToTheLeft.getColumn() < 1 || enemeyToTheLeft.getColumn() > 8) {
                }
                else {
                    if (board.getPiece(enemeyToTheLeft) != null) {
                        handleEnemies(board.getPiece(enemeyToTheLeft).getTeamColor() != board.getPiece(ogPosition).getTeamColor(), enemeyToTheLeft, newPossiblePositions, ogPosition);
                    }
                }
                if (enemeyToTheRight.getRow() < 1 || enemeyToTheRight.getRow() > 8 || enemeyToTheRight.getColumn() < 1 || enemeyToTheRight.getColumn() > 8) {
                }
                else {
                    if (board.getPiece(enemeyToTheRight) != null) {
                        handleEnemies(board.getPiece(enemeyToTheRight).getTeamColor() != board.getPiece(ogPosition).getTeamColor(), enemeyToTheRight, newPossiblePositions, ogPosition);
                    }
                }
            }
            }
        else {
            handleEnemies(board.getPiece(newPosition) == null, newPosition, newPossiblePositions, ogPosition);
            if (board.getPiece(enemeyToTheLeft) != null){
                handleEnemies(board.getPiece(enemeyToTheLeft).getTeamColor() != board.getPiece(ogPosition).getTeamColor(), enemeyToTheLeft, newPossiblePositions, ogPosition);
            }
            if (board.getPiece(enemeyToTheRight) != null){
                handleEnemies(board.getPiece(enemeyToTheRight).getTeamColor() != board.getPiece(ogPosition).getTeamColor(), enemeyToTheRight, newPossiblePositions, ogPosition);
            }
        }
        return newPossiblePositions;
    }

    private static void handleEnemies(boolean board, ChessPosition enemeyToTheRight, ArrayList<ChessMove> newPossiblePositions, ChessPosition ogPosition) {
        if (board) {

            if (enemeyToTheRight.getRow() == 8 || enemeyToTheRight.getRow() == 1) {
                newPossiblePositions.add((new ChessMove(ogPosition, enemeyToTheRight, ChessPiece.PieceType.QUEEN)));
                newPossiblePositions.add(new ChessMove(ogPosition, enemeyToTheRight, ChessPiece.PieceType.KNIGHT));
                newPossiblePositions.add(new ChessMove(ogPosition, enemeyToTheRight, ChessPiece.PieceType.BISHOP));
                newPossiblePositions.add(new ChessMove(ogPosition, enemeyToTheRight, ChessPiece.PieceType.ROOK));
            } else {
                newPossiblePositions.add(new ChessMove(ogPosition, enemeyToTheRight, null));
            }
        }
    }
}
