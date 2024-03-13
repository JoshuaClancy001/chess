package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;

public class ClientChessBoard {

    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_CHARS = 3;
    private static final int LINE_WIDTH_IN_CHARS = 1;
    private static final String EMPTY = "   ";

    public ClientChessBoard() {}

    public void printChessBoard(){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        String[][] board = new String[8][8];
        fillBoard(board);
        String[] whiteHeader = { "a", "b", "c", "d", "e", "f", "g", "h" };
        String[] blackHeader = { "h", "g", "f", "e", "d", "c", "b", "a" };

        out.print(ERASE_SCREEN);
        drawHeader(out,whiteHeader);
        drawRowsWhite(out, board, "White");
        drawHeader(out,whiteHeader);
        out.println();
        drawHeader(out,blackHeader);
        drawRowsBlack(out,board,"Black");
        drawHeader(out,blackHeader);
    }

    public void drawRowsWhite(PrintStream out, String[][] board, String whiteOrBlack){
        for (int rowNum = 8; rowNum > 0; rowNum--){
            String[] row = board[rowNum - 1];
            if (rowNum % 2 == 0){
                drawEvenRow(out, rowNum, row,whiteOrBlack );
            }
            else{
                drawOddRow(out, rowNum, row,whiteOrBlack);
            }
            out.print(SET_BG_COLOR_LIGHT_GREY);
            out.print(SET_TEXT_COLOR_BLACK);
            out.print(" ");
            out.print(rowNum);
            out.print(" ");
            out.print(RESET_BG_COLOR);
            out.println();
        }
    }
    public void drawRowsBlack(PrintStream out, String[][] board, String whiteOrBlack){
        for (int rowNum = 1; rowNum < 9; rowNum++){
            String[] row = board[rowNum-1];
            if (rowNum % 2 == 0){
                drawEvenRow(out, rowNum, row,whiteOrBlack );
            }
            else{
                drawOddRow(out, rowNum, row,whiteOrBlack);
            }
            out.print(SET_BG_COLOR_LIGHT_GREY);
            out.print(SET_TEXT_COLOR_BLACK);
            out.print(" ");
            out.print(rowNum);
            out.print(" ");
            out.print(RESET_BG_COLOR);
            out.println();
        }
    }

    public void drawOddRow(PrintStream out, int rowNum, String[] row, String whiteOrBlack){
        int prefixLength = 1;
        int suffixLength = 1;

        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(SET_TEXT_BOLD);
        out.print(" " + rowNum + " ");
        if (whiteOrBlack.equals("White")){
            printPiecesWhite(out,row, rowNum,whiteOrBlack);
        }
        else{
            printPiecesBlack(out,row,rowNum,whiteOrBlack);
        }

    }

    public void drawEvenRow(PrintStream out, int rowNum, String[] row, String whiteOrBlack){
        int prefixLength = 1;
        int suffixLength = 1;

        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(SET_TEXT_BOLD);
        out.print(" " + rowNum + " ");
        if (whiteOrBlack.equals("White")){
            printPiecesWhite(out,row, rowNum,whiteOrBlack);
        }
        else{
            printPiecesBlack(out,row,rowNum,whiteOrBlack);
        }





    }

    void printPiecesWhite(PrintStream out, String[] row, int rowNum,String whiteOrBlack){
        for (int colNum = 0; colNum < row.length; colNum++){
            printPiece(out,colNum,row,rowNum,whiteOrBlack);
        }
    }

    void printPiecesBlack(PrintStream out, String[] row, int rowNum,String whiteOrBlack){
        for (int colNum = row.length - 1; colNum >= 0; colNum--){
            printPiece(out,colNum,row,rowNum,whiteOrBlack);
        }
    }


    private static void setBackAndText(PrintStream out, int rowNum, int colNum,String whiteOrBlack) {
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
        if (whiteOrBlack.equals("White")) {
            if (rowNum == 8 || rowNum == 7) {
                out.print(SET_TEXT_COLOR_BLUE);
            } else if (rowNum == 1 || rowNum == 2) {
                out.print(SET_TEXT_COLOR_RED);
            }
        }
        else{
            if (rowNum == 8 || rowNum == 7) {
                out.print(SET_TEXT_COLOR_RED);
            } else if (rowNum == 1 || rowNum == 2) {
                out.print(SET_TEXT_COLOR_BLUE);
            }
        }
    }

    void printPiece(PrintStream out, int col, String[] row, int rowNum,String whiteOrBlack){
        setBackAndText(out, rowNum, col,whiteOrBlack);
        out.print(" ");
        out.print(row[col]);
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
        out.print(RESET_BG_COLOR);
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

    public void fillBoard(String[][] board){

        for(int row = 0; row < 8; row++){
            for (int col = 0; col < 8; col ++){
                addPiece(board," ",row+1,col+1);
            }
        }


        addPiece(board, "R", 1,1);
        addPiece(board, "N", 1,2);
        addPiece(board, "B", 1,3);
        addPiece(board, "Q", 1,4);
        addPiece(board, "K", 1,5);
        addPiece(board, "B", 1,6);
        addPiece(board, "N", 1,7);
        addPiece(board, "R", 1,8);


        //adding the White Pawns

        addPiece(board, "P", 2, 1);
        addPiece(board, "P", 2, 2);
        addPiece(board, "P", 2, 3);
        addPiece(board, "P", 2, 4);
        addPiece(board, "P", 2, 5);
        addPiece(board, "P", 2, 6);
        addPiece(board, "P", 2, 7);
        addPiece(board, "P", 2, 8);

        addPiece(board, "P", 7, 1);
        addPiece(board, "P", 7, 2);
        addPiece(board, "P", 7, 3);
        addPiece(board, "P", 7, 4);
        addPiece(board, "P", 7, 5);
        addPiece(board, "P", 7, 6);
        addPiece(board, "P", 7, 7);
        addPiece(board, "P", 7, 8);

        //adding Black Pawns

        addPiece(board, "R", 8,1);
        addPiece(board, "N", 8,2);
        addPiece(board, "B", 8,3);
        addPiece(board, "Q", 8,4);
        addPiece(board, "K", 8,5);
        addPiece(board, "B", 8,6);
        addPiece(board, "N", 8,7);
        addPiece(board, "R", 8,8);
    }
}
