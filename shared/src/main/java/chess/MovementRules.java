package chess;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class MovementRules {

    public MovementRules() {

    }

    public ArrayList<ChessMove> KingMoves(ChessBoard board, ChessPosition ogPosition) {
        ArrayList<ChessMove> newPossiblePositions = new ArrayList<>();

        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow() , ogPosition.getColumn()+1);
            if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
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
        }// right 1
        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow(), ogPosition.getColumn()-1);
            if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
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
        }// left 1
        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow()+1, ogPosition.getColumn());
            if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
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
        }// up 1
        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow()-1,ogPosition.getColumn());
            if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
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
        }// down 1
        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow() +1, ogPosition.getColumn() + 1);
            if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
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
        }// up 1 right 1
        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow() +1, ogPosition.getColumn() -1);
            if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
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
        }// up 1 left 1
        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow()-1 , ogPosition.getColumn() + 1);
            if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
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
        }// down 1 right 1
        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow() -1, ogPosition.getColumn() -1);
            if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
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
        }// down 1 left 1


        return newPossiblePositions;
    }
    public ArrayList<ChessMove> QueenMoves(ChessBoard board, ChessPosition ogPosition) {
        ArrayList<ChessMove> newPossiblePositions = new ArrayList<>();

        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow() + i, ogPosition.getColumn() + i);
            if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
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
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow() - i, ogPosition.getColumn() + i);
            if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
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
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow() + i, ogPosition.getColumn() - i);
            if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
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
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow() - i, ogPosition.getColumn() - i);
            if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
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
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow() , ogPosition.getColumn() + i);
            if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
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
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow(), ogPosition.getColumn() - i);
            if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
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
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow() + i, ogPosition.getColumn());
            if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
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
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow() - i, ogPosition.getColumn());
            if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
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
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow() + i, ogPosition.getColumn() + i);
            if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
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
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow() - i, ogPosition.getColumn() + i);
            if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
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
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow() + i, ogPosition.getColumn() - i);
            if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
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
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow() - i, ogPosition.getColumn() - i);
            if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
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

        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow() +2 , ogPosition.getColumn() + 1);
            if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
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
        }// up 2 right 1
        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow() +2, ogPosition.getColumn() -1);
            if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
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
        }// up 2 left 1
        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow() -2, ogPosition.getColumn() + 1);
            if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
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
        }// down 2 right 1
        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow()-2,ogPosition.getColumn()-1);
            if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
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
        }// down 2 left 1
        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow() +1, ogPosition.getColumn() + 2);
            if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
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
        }// up 1 right 2
        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow() +1, ogPosition.getColumn() -2);
            if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
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
        }// up 1 left 2
        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow()-1 , ogPosition.getColumn() + 2);
            if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
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
        }// down 1 right 2
        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow() -1, ogPosition.getColumn() -2);
            if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
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
        }// down 1 left 2



        return newPossiblePositions;}
    public ArrayList<ChessMove> RookMoves(ChessBoard board, ChessPosition ogPosition) {

        ArrayList<ChessMove> newPossiblePositions = new ArrayList<>();

        for (int i = 1; i < 9; i++) {
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow() , ogPosition.getColumn() + i);
            if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
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
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow(), ogPosition.getColumn() - i);
            if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
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
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow() + i, ogPosition.getColumn());
            if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
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
            ChessPosition newPosition = new ChessPosition(ogPosition.getRow() - i, ogPosition.getColumn());
            if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
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
        ChessPosition newPosition;
        ChessPosition newPositionAtStart;
        ChessPosition enemeyToTheRight;
        ChessPosition enemeyToTheLeft;
        int startingRow;
        if (board.getPiece(ogPosition).getTeamColor() == ChessGame.TeamColor.WHITE){
            newPosition = new ChessPosition(ogPosition.getRow() + 1 , ogPosition.getColumn());
            newPositionAtStart = new ChessPosition(ogPosition.getRow() + 2,ogPosition.getColumn());
            enemeyToTheRight = new ChessPosition(ogPosition.getRow() + 1 , ogPosition.getColumn()+1);
            enemeyToTheLeft = new ChessPosition(ogPosition.getRow() + 1 , ogPosition.getColumn() - 1);
            startingRow = 2;
        }
        else {
            newPosition = new ChessPosition(ogPosition.getRow() - 1, ogPosition.getColumn());
            newPositionAtStart = new ChessPosition(ogPosition.getRow() - 2, ogPosition.getColumn());
            enemeyToTheRight = new ChessPosition(ogPosition.getRow() - 1 , ogPosition.getColumn()+1);
            enemeyToTheLeft = new ChessPosition(ogPosition.getRow() - 1 , ogPosition.getColumn() - 1);
            startingRow = 7;
        }

        if (newPosition.getRow() < 1 || newPosition.getRow() > 8 || newPosition.getColumn() < 1 || newPosition.getColumn() > 8) {
            System.out.println("Off the board");
        }

        else if (ogPosition.getRow() == startingRow){

            if (board.getPiece(newPosition) == null) {
                newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
                if (board.getPiece(newPositionAtStart) == null){
                    newPossiblePositions.add(new ChessMove(ogPosition, newPositionAtStart, null));
                }
            }
        }
        else {
            if (board.getPiece(newPosition) == null) {
                if (newPosition.getRow() == 8 || newPosition.getRow() == 1) {
                    newPossiblePositions.add((new ChessMove(ogPosition, newPosition, ChessPiece.PieceType.QUEEN)));
                    newPossiblePositions.add(new ChessMove(ogPosition, newPosition, ChessPiece.PieceType.KNIGHT));
                    newPossiblePositions.add(new ChessMove(ogPosition, newPosition, ChessPiece.PieceType.BISHOP));
                    newPossiblePositions.add(new ChessMove(ogPosition, newPosition, ChessPiece.PieceType.ROOK));
                }
                else {
                    newPossiblePositions.add(new ChessMove(ogPosition, newPosition, null));
                    }
            }


            if (board.getPiece(enemeyToTheLeft) != null){
                if(board.getPiece(enemeyToTheLeft).getTeamColor() != board.getPiece(ogPosition).getTeamColor()){
                    if (enemeyToTheLeft.getRow() == 8 || enemeyToTheLeft.getRow() == 1) {
                        newPossiblePositions.add((new ChessMove(ogPosition,enemeyToTheLeft, ChessPiece.PieceType.QUEEN)));
                        newPossiblePositions.add(new ChessMove(ogPosition, enemeyToTheLeft, ChessPiece.PieceType.KNIGHT));
                        newPossiblePositions.add(new ChessMove(ogPosition, enemeyToTheLeft, ChessPiece.PieceType.BISHOP));
                        newPossiblePositions.add(new ChessMove(ogPosition, enemeyToTheLeft, ChessPiece.PieceType.ROOK));
                    }
                    else{
                        newPossiblePositions.add(new ChessMove(ogPosition,enemeyToTheLeft,null));
                    }
                }
            }

            if (board.getPiece(enemeyToTheRight) != null){
                if(board.getPiece(enemeyToTheRight).getTeamColor() != board.getPiece(ogPosition).getTeamColor()){

                    if (enemeyToTheRight.getRow() == 8 || enemeyToTheRight.getRow() == 1) {
                        newPossiblePositions.add((new ChessMove(ogPosition, enemeyToTheRight, ChessPiece.PieceType.QUEEN)));
                        newPossiblePositions.add(new ChessMove(ogPosition, enemeyToTheRight, ChessPiece.PieceType.KNIGHT));
                        newPossiblePositions.add(new ChessMove(ogPosition, enemeyToTheRight, ChessPiece.PieceType.BISHOP));
                        newPossiblePositions.add(new ChessMove(ogPosition, enemeyToTheRight, ChessPiece.PieceType.ROOK));
                    }
                    else {
                        newPossiblePositions.add(new ChessMove(ogPosition,enemeyToTheRight,null));
                    }
                }
            }
        }
        return newPossiblePositions;
    }
}
