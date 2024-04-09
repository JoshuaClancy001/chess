package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;

public class ClientChessBoard {

    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final String EMPTY = "   ";

    public ClientChessBoard() {
    }

    public void printChessBoard(ChessBoard chessBoard, boolean[][] validMoves) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        String[] whiteHeader = {"a", "b", "c", "d", "e", "f", "g", "h"};
        String[] blackHeader = {"h", "g", "f", "e", "d", "c", "b", "a"};

        out.print(ERASE_SCREEN);
        drawHeader(out, whiteHeader);
        drawRowsWhite(out, chessBoard, "White", validMoves);
        drawHeader(out, whiteHeader);
        out.println();
        drawHeader(out, blackHeader);
        drawRowsBlack(out, chessBoard, "Black", validMoves);
        drawHeader(out, blackHeader);
        out.print("\u001B[0m");
    }

    public void drawRowsWhite(PrintStream out, ChessBoard board, String whiteOrBlack, boolean[][] validMoves) {
        for (int rowNum = 8; rowNum > 0; rowNum--) {
            ChessPiece[] row = board.getRow(rowNum);
            boolean[] validMoveRow = validMoves[rowNum];
            if (rowNum % 2 == 0) {
                drawEvenRow(out, rowNum, row, whiteOrBlack, validMoveRow);
            } else {
                drawOddRow(out, rowNum, row, whiteOrBlack, validMoveRow);
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

    public void drawRowsBlack(PrintStream out, ChessBoard board, String whiteOrBlack, boolean[][] validMoves) {
        for (int rowNum = 1; rowNum < 9; rowNum++) {
            ChessPiece[] row = board.getRow(rowNum);
            boolean[] validMovesRow = validMoves[rowNum];
            if (rowNum % 2 == 0) {
                drawEvenRow(out, rowNum, row, whiteOrBlack, validMovesRow);
            } else {
                drawOddRow(out, rowNum, row, whiteOrBlack, validMovesRow);
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

    public void drawOddRow(PrintStream out, int rowNum, ChessPiece[] row, String whiteOrBlack, boolean[] validMovesRow) {

        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(SET_TEXT_BOLD);
        out.print(" " + (rowNum) + " ");
        if (whiteOrBlack.equals("White")) {
            printPiecesWhite(out, row, rowNum, validMovesRow);
        } else {
            printPiecesBlack(out, row, rowNum, validMovesRow);
        }

    }

    public void drawEvenRow(PrintStream out, int rowNum, ChessPiece[] row, String whiteOrBlack, boolean[] validMovesRow) {

        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_BLACK);
        out.print(SET_TEXT_BOLD);
        out.print(" " + (rowNum) + " ");
        if (whiteOrBlack.equals("White")) {
            printPiecesWhite(out, row, rowNum, validMovesRow);
        } else {
            printPiecesBlack(out, row, rowNum, validMovesRow);
        }


    }

    void printPiecesWhite(PrintStream out, ChessPiece[] row, int rowNum, boolean[] validMovesRow) {
        for (int colNum = 1; colNum < row.length; colNum++) {
            printPiece(out, colNum, row, rowNum, validMovesRow);
        }
    }

    void printPiecesBlack(PrintStream out, ChessPiece[] row, int rowNum, boolean[] validMovesRow) {
        for (int colNum = row.length - 1; colNum > 0; colNum--) {
            printPiece(out, colNum, row, rowNum, validMovesRow);
        }
    }


    private static void setBackAndText(PrintStream out, int rowNum, ChessPiece[] row, int colNum, boolean[] validMoves) {


        if (rowNum % 2 == 0) {
            if (colNum % 2 == 0) {
                out.print(SET_BG_COLOR_WHITE);
            } else {
                out.print(SET_BG_COLOR_BLACK);
            }
        } else {
            if (colNum % 2 == 0) {
                out.print(SET_BG_COLOR_BLACK);
            } else {
                out.print(SET_BG_COLOR_WHITE);
            }
        }

        if (validMoves[colNum]) {
            out.print(SET_BG_COLOR_YELLOW);
        }

        if (row[colNum] != null) {
            if (row[colNum].getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
                out.print(SET_TEXT_COLOR_RED);
            } else if (row[colNum].getTeamColor().equals(ChessGame.TeamColor.BLACK)) {
                out.print(SET_TEXT_COLOR_BLUE);
            }
        }
    }

    void printPiece(PrintStream out, int col, ChessPiece[] row, int rowNum, boolean[] validMoves) {
        setBackAndText(out, rowNum, row, col, validMoves);
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

    public void drawHeader(PrintStream out, String[] header) {
        setBlack(out);
        int prefixLength = 1;
        int suffixLength = 1;
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(EMPTY.repeat(prefixLength));
        out.print(" ");
        drawHeaders(out, header);
        out.print(EMPTY.repeat(suffixLength));
        out.print("\u001B[0m");
        out.println();
    }

    public void drawHeaders(PrintStream out, String[] header) {
        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; boardCol++) {
            out.print(SET_TEXT_COLOR_BLACK);
            out.print(SET_TEXT_BOLD);
            out.print(header[boardCol]);
            if (boardCol == BOARD_SIZE_IN_SQUARES - 1) {
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
}
