package chess;

import java.util.*;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessGame.TeamColor TeamColor;
    private ChessBoard board;

    public ChessGame() {
        this.board = new ChessBoard();
        this.board.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return this.TeamColor;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.TeamColor = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition){
        Collection<ChessMove> moves;
        Collection<ChessMove> validMoves =  new ArrayList<>();
        if (this.board.getPiece(startPosition) == null){
            return null;
        }
        else {
            moves = this.board.getPiece(startPosition).pieceMoves(this.board,startPosition);

            for (ChessMove move : moves) {
                ChessPiece piece = this.board.getPiece(move.getStartPosition());
                ChessPiece takenPiece = this.board.getPiece((move.getEndPosition()));
                this.board.addPiece(move.getEndPosition(), piece);
                this.board.removePiece(move.getStartPosition());

                if (!isInCheck(this.board.getPiece(move.getEndPosition()).getTeamColor())){
                    validMoves.add(move);
                }
                undoMove(move,takenPiece);
            }
        }

        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void undoMove(ChessMove move, ChessPiece takenPiece)  {
            ChessPiece piece = this.board.getPiece(move.getEndPosition());
            this.board.addPiece(move.getStartPosition(), piece);
            this.board.removePiece(move.getEndPosition());
            if (takenPiece != null) {
                this.board.addPiece(move.getEndPosition(), takenPiece);
            }

    }
    public void makeMove(ChessMove move) throws InvalidMoveException {
        Collection<ChessMove> valid = new ArrayList<>();
        valid = validMoves(move.getStartPosition());

        if (this.board.getPiece(move.getStartPosition()).getTeamColor() != this.TeamColor){
            throw new InvalidMoveException("Invalid Move");
        }
            if (valid.contains(move)) {
                ChessPiece piece = this.board.getPiece(move.getStartPosition());
                ChessPiece takenPiece = this.board.getPiece(move.getEndPosition());
                if (move.getPromotionPiece() != null){
                    this.board.addPiece(move.getEndPosition(),new ChessPiece(this.TeamColor,move.getPromotionPiece()));
                }
                else {
                    this.board.addPiece(move.getEndPosition(), piece);
                }
                this.board.removePiece(move.getStartPosition());

                if (this.TeamColor == TeamColor.WHITE){
                    setTeamTurn(TeamColor.BLACK);
                }
                else{
                    setTeamTurn(TeamColor.WHITE);
                }
                if (isInCheck(piece.getTeamColor())){
                    undoMove(move,takenPiece);
                    throw new InvalidMoveException();
                }
            }
            else {
                throw new InvalidMoveException("Invalid Move");
            }

    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        boolean isInCheck = false;
        ChessPosition kingPosition = null;
        Collection<ChessMove> moves = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(board.getPiece(new ChessPosition(i,j)) != null){
                if (board.getPiece(new ChessPosition(i, j)).getTeamColor() == teamColor && board.getPiece(new ChessPosition(i, j)).getPieceType() == ChessPiece.PieceType.KING) {
                    kingPosition = new ChessPosition(i, j);
                }
                }
            }
        }

        for (int i = 1; i < 9; i++){
            for (int j = 1; j < 9; j++){
                if (board.getPiece(new ChessPosition(i,j)) != null && board.getPiece(new ChessPosition(i,j)).getTeamColor() != teamColor){
                    moves = board.getPiece(new ChessPosition(i,j)).pieceMoves(board,new ChessPosition(i,j));
                    for (ChessMove move: moves){
                        if (move.getEndPosition().equals(kingPosition)){
                            isInCheck = true;
                        }
                    }
                }
            }
        }
        return isInCheck;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        Collection<ChessMove> moves = new ArrayList<>();

        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                if (board.getPiece(new ChessPosition(i,j)) != null && board.getPiece(new ChessPosition(i,j)).getTeamColor() == teamColor){
                    moves = board.getPiece(new ChessPosition(i,j)).pieceMoves(board,new ChessPosition(i,j));
                    for (ChessMove move: moves){
                        ChessPiece piece = this.board.getPiece(move.getStartPosition());
                        ChessPiece takenPiece = this.board.getPiece(move.getEndPosition());
                        this.board.addPiece(move.getEndPosition(), piece);
                        this.board.removePiece(move.getStartPosition());
                        if (!isInCheck(teamColor)) {
                            undoMove(move,takenPiece);
                            return false;
                            }
                        else{
                            undoMove(move,takenPiece);
                        }
                    }
                }
            }
        }

        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        boolean isInStalemate = false;
        Collection<ChessMove> validMoves = new ArrayList<>();
        Collection<ChessMove> Moves = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.getPiece(new ChessPosition(i, j)) != null && board.getPiece(new ChessPosition(i, j)).getTeamColor() == teamColor) {
                    Moves = validMoves(new ChessPosition(i, j));
                    validMoves.addAll(Moves);
                }
            }
        }
        if (validMoves.isEmpty()){
            isInStalemate = true;
        }
        return isInStalemate;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return this.board;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return TeamColor == chessGame.TeamColor && Objects.equals(board, chessGame.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(TeamColor, board);
    }
}
