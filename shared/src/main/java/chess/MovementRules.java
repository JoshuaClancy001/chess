package chess;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class MovementRules {

    public MovementRules() {

    }

    public ArrayList<ChessMove> KingMoves(ChessBoard board, ChessPosition ogPosition) {
        ArrayList<ChessMove> newPossiblePositions = new ArrayList<>();

        ArrayList<ChessPosition> newkingPositions = new ArrayList<>();

        newkingPositions.add(new ChessPosition(ogPosition.row, ogPosition.col + 1));
        newkingPositions.add(new ChessPosition(ogPosition.row, ogPosition.col - 1));
        newkingPositions.add(new ChessPosition(ogPosition.row + 1, ogPosition.col));
        newkingPositions.add(new ChessPosition(ogPosition.row + 1, ogPosition.col + 1));
        newkingPositions.add(new ChessPosition(ogPosition.row + 1, ogPosition.col - 1));
        newkingPositions.add(new ChessPosition(ogPosition.row - 1, ogPosition.col));
        newkingPositions.add(new ChessPosition(ogPosition.row - 1, ogPosition.col + 1));
        newkingPositions.add(new ChessPosition(ogPosition.row - 1, ogPosition.col + -1));

        for (ChessPosition i : newkingPositions) {

            if (i.row < 1 || i.row > 8 || i.col < 1 || i.col > 8) {
                System.out.println("Off the board");
            } else if (board.getPiece(i) != null) {
                if (board.getPiece(ogPosition).getTeamColor().equals(board.getPiece(new ChessPosition(i.row, i.col)).getTeamColor())) {
                    System.out.println("That is your piece");
                } else if (board.getPiece(ogPosition).getTeamColor() != board.getPiece(i).getTeamColor()) {
                    System.out.println("That is an enemy piece");
                    newPossiblePositions.add(new ChessMove(ogPosition, i, null));//right one
                }

            } else {
                newPossiblePositions.add(new ChessMove(ogPosition, i, null));//right one
            }
        }


        return newPossiblePositions;
    }

    public ArrayList<ChessMove> QueenMoves(ChessBoard board, ChessPosition ogPosition) {
        ArrayList<ChessMove> newPossiblePositions = new ArrayList<>();

        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.row + i, ogPosition.col + i);
            if (newPosition.row < 1 || newPosition.row > 8 || newPosition.col < 1 || newPosition.col > 8) {
                System.out.println("Off the board");
            }
            else if(board.getPiece(newPosition) != null){
                if(board.getPiece(ogPosition).getTeamColor() == board.getPiece(newPosition).getTeamColor()){
                    break;
                }
                else{
                    newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
                    break;
                }
            }
            else{
                newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
            }
        }// up right
        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.row - i, ogPosition.col + i);
            if (newPosition.row < 1 || newPosition.row > 8 || newPosition.col < 1 || newPosition.col > 8) {
                System.out.println("Off the board");
            }
            else if(board.getPiece(newPosition) != null){
                if(board.getPiece(ogPosition).getTeamColor() == board.getPiece(newPosition).getTeamColor()){
                    break;
                }
                else{
                    newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
                    break;
                }
            }
            else{
                newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
            }
        }// down right
        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.row + i, ogPosition.col - i);
            if (newPosition.row < 1 || newPosition.row > 8 || newPosition.col < 1 || newPosition.col > 8) {
                System.out.println("Off the board");
            }
            else if(board.getPiece(newPosition) != null){
                if(board.getPiece(ogPosition).getTeamColor() == board.getPiece(newPosition).getTeamColor()){
                    break;
                }
                else{
                    newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
                    break;
                }
            }
            else{
                newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
            }
        }// up left
        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.row - i, ogPosition.col - i);
            if (newPosition.row < 1 || newPosition.row > 8 || newPosition.col < 1 || newPosition.col > 8) {
                System.out.println("Off the board");
            }
            else if(board.getPiece(newPosition) != null){
                if(board.getPiece(ogPosition).getTeamColor() == board.getPiece(newPosition).getTeamColor()){
                    break;
                }
                else{
                    newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
                    break;
                }
            }
            else{
                newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
            }
        }// down left

        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.row , ogPosition.col + i);
            if (newPosition.row < 1 || newPosition.row > 8 || newPosition.col < 1 || newPosition.col > 8) {
                System.out.println("Off the board");
            }
            else if(board.getPiece(newPosition) != null){
                if(board.getPiece(ogPosition).getTeamColor() == board.getPiece(newPosition).getTeamColor()){
                    break;
                }
                else{
                    newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
                    break;
                }
            }
            else{
                newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
            }
        }// right
        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.row, ogPosition.col - i);
            if (newPosition.row < 1 || newPosition.row > 8 || newPosition.col < 1 || newPosition.col > 8) {
                System.out.println("Off the board");
            }
            else if(board.getPiece(newPosition) != null){
                if(board.getPiece(ogPosition).getTeamColor() == board.getPiece(newPosition).getTeamColor()){
                    break;
                }
                else{
                    newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
                    break;
                }
            }
            else{
                newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
            }
        }// left
        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.row + i, ogPosition.col);
            if (newPosition.row < 1 || newPosition.row > 8 || newPosition.col < 1 || newPosition.col > 8) {
                System.out.println("Off the board");
            }
            else if(board.getPiece(newPosition) != null){
                if(board.getPiece(ogPosition).getTeamColor() == board.getPiece(newPosition).getTeamColor()){
                    break;
                }
                else{
                    newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
                    break;
                }
            }
            else{
                newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
            }
        }// up
        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.row - i, ogPosition.col);
            if (newPosition.row < 1 || newPosition.row > 8 || newPosition.col < 1 || newPosition.col > 8) {
                System.out.println("Off the board");
            }
            else if(board.getPiece(newPosition) != null){
                if(board.getPiece(ogPosition).getTeamColor() == board.getPiece(newPosition).getTeamColor()){
                    break;
                }
                else{
                    newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
                    break;
                }
            }
            else{
                newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
            }
        }// down

        return newPossiblePositions;

    }
    public ArrayList<ChessMove> BishopMoves(ChessBoard board, ChessPosition ogPosition){
        ArrayList<ChessMove> newPossiblePositions = new ArrayList<>();

        ArrayList<ChessPosition> newBishopPositions = new ArrayList<>();

        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.row + i, ogPosition.col + i);
            if (newPosition.row < 1 || newPosition.row > 8 || newPosition.col < 1 || newPosition.col > 8) {
                System.out.println("Off the board");
            }
            else if(board.getPiece(newPosition) != null){
                if(board.getPiece(ogPosition).getTeamColor() == board.getPiece(newPosition).getTeamColor()){
                    break;
                }
                else{
                    newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
                    break;
                }
            }
            else{
                newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
            }
        }// up right
        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.row - i, ogPosition.col + i);
            if (newPosition.row < 1 || newPosition.row > 8 || newPosition.col < 1 || newPosition.col > 8) {
                System.out.println("Off the board");
            }
            else if(board.getPiece(newPosition) != null){
                if(board.getPiece(ogPosition).getTeamColor() == board.getPiece(newPosition).getTeamColor()){
                    break;
                }
                else{
                    newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
                    break;
                }
            }
            else{
                newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
            }
        }// down right
        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.row + i, ogPosition.col - i);
            if (newPosition.row < 1 || newPosition.row > 8 || newPosition.col < 1 || newPosition.col > 8) {
                System.out.println("Off the board");
            }
            else if(board.getPiece(newPosition) != null){
                if(board.getPiece(ogPosition).getTeamColor() == board.getPiece(newPosition).getTeamColor()){
                    break;
                }
                else{
                    newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
                    break;
                }
            }
            else{
                newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
            }
        }// up left
        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.row - i, ogPosition.col - i);
            if (newPosition.row < 1 || newPosition.row > 8 || newPosition.col < 1 || newPosition.col > 8) {
                System.out.println("Off the board");
            }
            else if(board.getPiece(newPosition) != null){
                if(board.getPiece(ogPosition).getTeamColor() == board.getPiece(newPosition).getTeamColor()){
                    break;
                }
                else{
                    newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
                    break;
                }
            }
            else{
                newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
            }
        }// down left

        return newPossiblePositions;}
    public ArrayList<ChessMove> KnightMoves(ChessBoard board, ChessPosition ogPosition){
        ArrayList<ChessMove> newPossiblePositions = new ArrayList<>();

        ArrayList<ChessPosition> newKnightPositions = new ArrayList<>();


        newKnightPositions.add(new ChessPosition(ogPosition.row+2,ogPosition.col+1));//u2r1
        newKnightPositions.add(new ChessPosition(ogPosition.row+2,ogPosition.col-1));//u2l1
        newKnightPositions.add(new ChessPosition(ogPosition.row-2,ogPosition.col+1));//d2r1
        newKnightPositions.add(new ChessPosition(ogPosition.row-2,ogPosition.col-1));//d2l1
        newKnightPositions.add(new ChessPosition(ogPosition.row+1,ogPosition.col+2));//r2u1
        newKnightPositions.add(new ChessPosition(ogPosition.row+1,ogPosition.col-2));//l2u1
        newKnightPositions.add(new ChessPosition(ogPosition.row-1,ogPosition.col+2));//r2d1
        newKnightPositions.add(new ChessPosition(ogPosition.row-1,ogPosition.col-2));//l2d1

        for (ChessPosition i : newKnightPositions) {

            if (i.row < 1 || i.row > 8 || i.col < 1 || i.col > 8) {
                System.out.println("Off the board");
            } else if (board.getPiece(i) != null) {
                if (board.getPiece(ogPosition).getTeamColor().equals(board.getPiece(new ChessPosition(i.row, i.col)).getTeamColor())) {
                    System.out.println("That is your piece");
                } else if (board.getPiece(ogPosition).getTeamColor() != board.getPiece(i).getTeamColor()) {
                    System.out.println("That is an enemy piece");
                    newPossiblePositions.add(new ChessMove(ogPosition, i, null));//right one
                }

            } else {
                newPossiblePositions.add(new ChessMove(ogPosition, i, null));//right one
            }
        }


        return newPossiblePositions;}
    public ArrayList<ChessMove> RookMoves(ChessBoard board, ChessPosition ogPosition) {

        ArrayList<ChessMove> newPossiblePositions = new ArrayList<>();

        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.row , ogPosition.col + i);
            if (newPosition.row < 1 || newPosition.row > 8 || newPosition.col < 1 || newPosition.col > 8) {
                System.out.println("Off the board");
            }
            else if(board.getPiece(newPosition) != null){
                if(board.getPiece(ogPosition).getTeamColor() == board.getPiece(newPosition).getTeamColor()){
                    break;
                }
                else{
                    newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
                    break;
                }
            }
            else{
                newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
            }
        }// right
        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.row, ogPosition.col - i);
            if (newPosition.row < 1 || newPosition.row > 8 || newPosition.col < 1 || newPosition.col > 8) {
                System.out.println("Off the board");
            }
            else if(board.getPiece(newPosition) != null){
                if(board.getPiece(ogPosition).getTeamColor() == board.getPiece(newPosition).getTeamColor()){
                    break;
                }
                else{
                    newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
                    break;
                }
            }
            else{
                newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
            }
        }// left
        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.row + i, ogPosition.col);
            if (newPosition.row < 1 || newPosition.row > 8 || newPosition.col < 1 || newPosition.col > 8) {
                System.out.println("Off the board");
            }
            else if(board.getPiece(newPosition) != null){
                if(board.getPiece(ogPosition).getTeamColor() == board.getPiece(newPosition).getTeamColor()){
                    break;
                }
                else{
                    newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
                    break;
                }
            }
            else{
                newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
            }
        }// up
        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.row - i, ogPosition.col);
            if (newPosition.row < 1 || newPosition.row > 8 || newPosition.col < 1 || newPosition.col > 8) {
                System.out.println("Off the board");
            }
            else if(board.getPiece(newPosition) != null){
                if(board.getPiece(ogPosition).getTeamColor() == board.getPiece(newPosition).getTeamColor()){
                    break;
                }
                else{
                    newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
                    break;
                }
            }
            else{
                newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
            }
        }// down



        return newPossiblePositions;}
    public ArrayList<ChessMove> PawnMoves(ChessBoard board, ChessPosition ogPosition){

        ArrayList<ChessMove> newPossiblePositions = new ArrayList<>();

        ArrayList<ChessPosition> newPawnPositions = new ArrayList<>();
        if (board.getPiece(ogPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {

            if (ogPosition.getRow() == 2) {
                newPawnPositions.add(new ChessPosition(ogPosition.row + 2, ogPosition.col));
            }

                newPawnPositions.add(new ChessPosition(ogPosition.row + 1, ogPosition.col));//u2r1}

                if (board.getPiece(new ChessPosition(ogPosition.row + 1, ogPosition.col + 1)) != null)
                    if (board.getPiece(ogPosition).getTeamColor() != board.getPiece(new ChessPosition(ogPosition.row + 1, ogPosition.col + 1)).getTeamColor()) {
                        newPawnPositions.add(new ChessPosition(ogPosition.row + 1, ogPosition.col + 1));
                    }
                if (board.getPiece(new ChessPosition(ogPosition.row + 1, ogPosition.col - 1)) != null) {
                    if (board.getPiece(ogPosition).getTeamColor() != board.getPiece(new ChessPosition(ogPosition.row + 1, ogPosition.col - 1)).getTeamColor()) {
                        newPawnPositions.add(new ChessPosition(ogPosition.row + 1, ogPosition.col - 1));
                    }
                }
            }

        if (board.getPiece(ogPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {

            if (ogPosition.getRow() == 7) {
                newPawnPositions.add(new ChessPosition(ogPosition.row - 2, ogPosition.col));
            }

            newPawnPositions.add(new ChessPosition(ogPosition.row - 1, ogPosition.col));//u2r1
            }
            if (board.getPiece(new ChessPosition(ogPosition.row - 1, ogPosition.col - 1)) != null)
                if (board.getPiece(ogPosition).getTeamColor() != board.getPiece(new ChessPosition(ogPosition.row - 1, ogPosition.col - 1)).getTeamColor()) {
                    newPawnPositions.add(new ChessPosition(ogPosition.row - 1, ogPosition.col - 1));
                }
            if (board.getPiece(new ChessPosition(ogPosition.row - 1, ogPosition.col + 1)) != null) {
                if (board.getPiece(ogPosition).getTeamColor() != board.getPiece(new ChessPosition(ogPosition.row - 1, ogPosition.col + 1)).getTeamColor()) {
                    newPawnPositions.add(new ChessPosition(ogPosition.row - 1, ogPosition.col + 1));
                }
            }

        for (ChessPosition i : newPawnPositions) {

            if (i.row < 1 || i.row > 8 || i.col < 1 || i.col > 8) {
                System.out.println("Off the board");
            } else if (board.getPiece(i) != null) {
                if (board.getPiece(ogPosition).getTeamColor().equals(board.getPiece(new ChessPosition(i.row, i.col)).getTeamColor())) {
                    System.out.println("That is your piece");
                } else if (board.getPiece(ogPosition).getTeamColor() != board.getPiece(i).getTeamColor()) {
                    System.out.println("That is an enemy piece");
                    newPossiblePositions.add(new ChessMove(ogPosition, i, null));//right one
                }

            } else {
                newPossiblePositions.add(new ChessMove(ogPosition, i, null));//right one
            }
        }


        return newPossiblePositions;}
}
