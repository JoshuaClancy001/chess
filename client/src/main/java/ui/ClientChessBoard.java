package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;

public class ClientChessBoard {

    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final String EMPTY = "   ";

    public ClientChessBoard() {}

    public void printChessBoard(ChessBoard chessBoard, boolean[][] validmoves){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        String[][] board = new String[8][8];
        //fillBoard(board, chessBoard);
        String[] whiteHeader = { "a", "b", "c", "d", "e", "f", "g", "h" };
        String[] blackHeader = { "h", "g", "f", "e", "d", "c", "b", "a" };

        out.print(ERASE_SCREEN);
        drawHeader(out,whiteHeader);
        drawRowsWhite(out, chessBoard, "White",validmoves);
        drawHeader(out,whiteHeader);
        out.println();
        drawHeader(out,blackHeader);
        drawRowsBlack(out,chessBoard,"Black",validmoves);
        drawHeader(out,blackHeader);
        out.print("\u001B[0m");
    }

    public void validMovesStart(boolean[][] chessBoard){
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                chessBoard[i][j] = false;
            }
        }
    }

    public void drawRowsWhite(PrintStream out, ChessBoard board, String whiteOrBlack, boolean[][] validMoves){
        for (int rowNum = 8; rowNum > 0; rowNum--){
            ChessPiece[] row = board.getRow(rowNum);
            boolean[] validMoveRow = validMoves[rowNum];
            if (rowNum % 2 == 0){
                drawEvenRow(out, rowNum, row,whiteOrBlack,validMoveRow );
            }
            else{
                drawOddRow(out, rowNum, row,whiteOrBlack,validMoveRow);
            }
            out.print(SET_BG_COLOR_LIGHT_GREY);
            out.print(SET_TEXT_COLOR_BLACK);
            out.print(" ");
            out.print(rowNum+1);
            out.print(" ");
            out.print("\u001B[0m");
            out.println();
        }
    }
    public void drawRowsBlack(PrintStream out, ChessBoard board, String whiteOrBlack, boolean[][] validMoves){
        for (int rowNum = 1; rowNum < 9; rowNum++){
            ChessPiece[] row = board.getRow(rowNum);
            boolean[] validMovesRow = validMoves[rowNum];
            if (rowNum % 2 == 0){
                drawEvenRow(out, rowNum, row,whiteOrBlack,validMovesRow);
            }
            else{
                drawOddRow(out, rowNum, row,whiteOrBlack,validMovesRow);
            }
            out.print(SET_BG_COLOR_LIGHT_GREY);
            out.print(SET_TEXT_COLOR_BLACK);
            out.print(" ");
            out.print(rowNum);
            out.print(" ");
            out.print("\u001B[0m");
            out.println();
        }
    }

    public void drawOddRow(PrintStream out, int rowNum, ChessPiece[] row, String whiteOrBlack, boolean[] validMovesRow){
        int prefixLength = 1;
        int suffixLength = 1;

        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(SET_TEXT_BOLD);
        out.print(" " + (rowNum) + " ");
        if (whiteOrBlack.equals("White")){
            printPiecesWhite(out,row, rowNum,whiteOrBlack,validMovesRow);
        }
        else{
            printPiecesBlack(out,row,rowNum,whiteOrBlack,validMovesRow);
        }

    }

    public void drawEvenRow(PrintStream out, int rowNum, ChessPiece[] row, String whiteOrBlack,boolean[] validMovesRow){
        int prefixLength = 1;
        int suffixLength = 1;

        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(SET_TEXT_BOLD);
        out.print(" " + (rowNum) + " ");
        if (whiteOrBlack.equals("White")){
            printPiecesWhite(out,row, rowNum,whiteOrBlack,validMovesRow);
        }
        else{
            printPiecesBlack(out,row,rowNum,whiteOrBlack,validMovesRow);
        }





    }

    void printPiecesWhite(PrintStream out, ChessPiece[] row, int rowNum,String whiteOrBlack,boolean[] validMovesRow){
        for (int colNum = 1; colNum < row.length; colNum++){
            printPiece(out,colNum,row,rowNum,whiteOrBlack,validMovesRow);
        }
    }

    void printPiecesBlack(PrintStream out, ChessPiece[] row, int rowNum,String whiteOrBlack, boolean[] validMovesRow){
        for (int colNum = row.length - 1; colNum > 0; colNum--){
            printPiece(out,colNum,row,rowNum,whiteOrBlack,validMovesRow);
        }
    }


    private static void setBackAndText(PrintStream out, int rowNum,ChessPiece[] row ,int colNum,String whiteOrBlack,boolean[] validMoves) {


        if (rowNum % 2 == 0) {
            if (colNum % 2 == 0) {
                out.print(SET_BG_COLOR_WHITE);
            } else {
                out.print(SET_BG_COLOR_BLACK);
            }
        }
        else{
            if (colNum % 2 == 0) {
                out.print(SET_BG_COLOR_BLACK);
            } else {
                out.print(SET_BG_COLOR_WHITE);
            }
        }

        if (validMoves[colNum] == true){
            out.print(SET_BG_COLOR_MAGENTA);
        }

        if (row[colNum] != null) {
            if (row[colNum].getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
                out.print(SET_TEXT_COLOR_RED);
            } else if (row[colNum].getTeamColor().equals(ChessGame.TeamColor.BLACK)) {
                out.print(SET_TEXT_COLOR_BLUE);
            }
        }

//        if (whiteOrBlack.equals("White")) {
//            if (rowNum == 7 || rowNum == 6) {
//                out.print(SET_TEXT_COLOR_BLUE);
//            } else if (rowNum == 0 || rowNum == 1) {
//                out.print(SET_TEXT_COLOR_RED);
//            }
//        }
//        else{
//            if (rowNum == 7 || rowNum == 6) {
//                out.print(SET_TEXT_COLOR_RED);
//            } else if (rowNum == 0 || rowNum == 1) {
//                out.print(SET_TEXT_COLOR_BLUE);
//            }
//        }
    }

    void printPiece(PrintStream out, int col, ChessPiece[] row, int rowNum,String whiteOrBlack, boolean[] validMoves){
        setBackAndText(out, rowNum, row, col,whiteOrBlack,validMoves);
        String printedPiece = " ";
        if (row[col] != null) {
            switch (row[col].getPieceType()) {
                case BISHOP -> printedPiece = "B";
                case KNIGHT -> printedPiece = "N";
                case ROOK -> printedPiece = "R";
                case QUEEN -> printedPiece = "Q";
                case KING -> printedPiece = "K";
                case PAWN -> printedPiece = "P";
            }
        }
        out.print(" ");
        out.print(printedPiece);
        out.print(" ");
    }

    public void drawHeader(PrintStream out, String[] header){
        setBlack(out);
        int prefixLength = 1;
        int suffixLength = 1;
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(EMPTY.repeat(prefixLength));
        out.print(" ");
        drawHeaders(out,header);
        out.print(EMPTY.repeat(suffixLength));
        out.print("\u001B[0m");
        out.println();
    }

    public void drawHeaders(PrintStream out, String[] header){
        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; boardCol++){
            out.print(SET_TEXT_COLOR_BLACK);
            out.print(SET_TEXT_BOLD);
            out.print(header[boardCol]);
            if (boardCol == BOARD_SIZE_IN_SQUARES - 1){
                out.print(" ");
                return;
                }
            out.print("  ");
        }

    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    public void addPiece(String[][] board, String piece, int row, int col) {
        board[row-1][col-1] = piece;
    }

    public void fillBoard(String[][] printBoard, ChessBoard chessBoard){



        for(int row = 0; row < 8; row++){
            for (int col = 0; col < 8; col ++){
                if (chessBoard.getPiece(new ChessPosition(row+1, col+1)) == null){
                    addPiece(printBoard," ",row+1,col+1);
                }
                else if (chessBoard.getPiece(new ChessPosition(row+1,col+1)).getPieceType().equals(ChessPiece.PieceType.ROOK)){
                    addPiece(printBoard, "R", row+1,col+1);
                }
                else if (chessBoard.getPiece(new ChessPosition(row+1,col+1)).getPieceType().equals(ChessPiece.PieceType.KNIGHT)){
                    addPiece(printBoard, "N", row+1,col+1);
                }
                else if (chessBoard.getPiece(new ChessPosition(row+1,col+1)).getPieceType().equals(ChessPiece.PieceType.BISHOP)){
                    addPiece(printBoard, "B", row+1,col+1);
                }
                else if (chessBoard.getPiece(new ChessPosition(row+1,col+1)).getPieceType().equals(ChessPiece.PieceType.QUEEN)){
                    addPiece(printBoard, "Q", row+1,col+1);
                }
                else if (chessBoard.getPiece(new ChessPosition(row+1,col+1)).getPieceType().equals(ChessPiece.PieceType.KING)){
                    addPiece(printBoard, "K", row+1,col+1);
                }
                else if (chessBoard.getPiece(new ChessPosition(row+1,col+1)).getPieceType().equals(ChessPiece.PieceType.PAWN)){
                    addPiece(printBoard, "P", row+1,col+1);
                }

            }
        }

//        addPiece(printBoard, "R", 1,1);
//        addPiece(printBoard, "N", 1,2);
//        addPiece(printBoard, "B", 1,3);
//        addPiece(printBoard, "Q", 1,4);
//        addPiece(printBoard, "K", 1,5);
//        addPiece(printBoard, "B", 1,6);
//        addPiece(printBoard, "N", 1,7);
//        addPiece(printBoard, "R", 1,8);
//
//        addPiece(printBoard, "P", 2, 1);
//        addPiece(printBoard, "P", 2, 2);
//        addPiece(printBoard, "P", 2, 3);
//        addPiece(printBoard, "P", 2, 4);
//        addPiece(printBoard, "P", 2, 5);
//        addPiece(printBoard, "P", 2, 6);
//        addPiece(printBoard, "P", 2, 7);
//        addPiece(printBoard, "P", 2, 8);
//
//        addPiece(printBoard, "P", 7, 1);
//        addPiece(printBoard, "P", 7, 2);
//        addPiece(printBoard, "P", 7, 3);
//        addPiece(printBoard, "P", 7, 4);
//        addPiece(printBoard, "P", 7, 5);
//        addPiece(printBoard, "P", 7, 6);
//        addPiece(printBoard, "P", 7, 7);
//        addPiece(printBoard, "P", 7, 8);
//
//        addPiece(printBoard, "R", 8,1);
//        addPiece(printBoard, "N", 8,2);
//        addPiece(printBoard, "B", 8,3);
//        addPiece(printBoard, "Q", 8,4);
//        addPiece(printBoard, "K", 8,5);
//        addPiece(printBoard, "B", 8,6);
//        addPiece(printBoard, "N", 8,7);
//        addPiece(printBoard, "R", 8,8);
    }
}
