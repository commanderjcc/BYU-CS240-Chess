package chessImpl;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.Objects;

public class CBoard implements chess.ChessBoard {
    private ArrayList<ArrayList<ChessPiece>> board = new ArrayList<>();

    public CBoard() {
        for (int i = 0; i < 8; i++) {
            var blankrow = new ArrayList<ChessPiece>(8);
            for (int j = 0; j < 8; j++) {
                blankrow.add(null);
            }
            board.add(blankrow);
        }
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        var row = board.get(position.getRow()-1);
        row.remove(position.getColumn()-1);

        switch (piece.getPieceType()) {
            case PAWN -> piece = new Pawn(piece.getTeamColor());
            case ROOK -> piece = new Rook(piece.getTeamColor());
            case KNIGHT -> piece = new Knight(piece.getTeamColor());
            case BISHOP -> piece = new Bishop(piece.getTeamColor());
            case QUEEN -> piece = new Queen(piece.getTeamColor());
            case KING -> piece = new King(piece.getTeamColor());
        }

        row.add(position.getColumn()-1, piece);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CBoard that = (CBoard) o;
        return Objects.equals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board);
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    @Override
    public ChessPiece getPiece(ChessPosition position) {
        return board.get(position.getRow()-1).get(position.getColumn()-1);
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    @Override
    public void resetBoard() {
        board = new ArrayList<>();

        //White Row 1
        var row = new ArrayList<ChessPiece>(8);
        row.add(new Rook(ChessGame.TeamColor.WHITE));
        row.add(new Knight(ChessGame.TeamColor.WHITE));
        row.add(new Bishop(ChessGame.TeamColor.WHITE));
        row.add(new Queen(ChessGame.TeamColor.WHITE));
        row.add(new King(ChessGame.TeamColor.WHITE));
        row.add(new Bishop(ChessGame.TeamColor.WHITE));
        row.add(new Knight(ChessGame.TeamColor.WHITE));
        row.add(new Rook(ChessGame.TeamColor.WHITE));
        board.add(row);

        //White Row 2
        row = new ArrayList<>(8);
        for (int i = 0; i < 8; i++) {
            row.add(new Pawn(ChessGame.TeamColor.WHITE));
        }
        board.add(row);

        //Midfield
        for (int i = 2; i < 6; i++) {
            row = new ArrayList<>(8);
            for (int j = 0; j < 8; j++) {
                row.add(null);
            }
            board.add(row);
        }

        //Black Row 7
        row = new ArrayList<>(8);
        for (int i = 0; i < 8; i++) {
            row.add(new Pawn(ChessGame.TeamColor.BLACK));
        }
        board.add(row);

        //Black Row 8
        row = new ArrayList<ChessPiece>(8);
        row.add(new Rook(ChessGame.TeamColor.BLACK));
        row.add(new Knight(ChessGame.TeamColor.BLACK));
        row.add(new Bishop(ChessGame.TeamColor.BLACK));
        row.add(new Queen(ChessGame.TeamColor.BLACK));
        row.add(new King(ChessGame.TeamColor.BLACK));
        row.add(new Bishop(ChessGame.TeamColor.BLACK));
        row.add(new Knight(ChessGame.TeamColor.BLACK));
        row.add(new Rook(ChessGame.TeamColor.BLACK));
        board.add(row);
    }

    /**
     * @param startPosition
     * @param endPosition
     */
    @Override
    public void replacePiece(ChessPosition startPosition, ChessPosition endPosition, ChessPiece.PieceType pieceType) {
        ChessPiece piece;
        if (pieceType == null) {
            piece = getPiece(startPosition);
        } else {
            switch (pieceType) {
                case PAWN -> piece = new Pawn(getPiece(startPosition).getTeamColor());
                case ROOK -> piece = new Rook(getPiece(startPosition).getTeamColor());
                case KNIGHT -> piece = new Knight(getPiece(startPosition).getTeamColor());
                case BISHOP -> piece = new Bishop(getPiece(startPosition).getTeamColor());
                case QUEEN -> piece = new Queen(getPiece(startPosition).getTeamColor());
                case KING -> piece = new King(getPiece(startPosition).getTeamColor());
                default -> throw new IllegalStateException("Unexpected value: " + pieceType);
            }
        }

        board.get(startPosition.getRow()-1).remove(startPosition.getColumn()-1);
        board.get(startPosition.getRow()-1).add(startPosition.getColumn()-1, null);
        board.get(endPosition.getRow()-1).remove(endPosition.getColumn()-1);
        board.get(endPosition.getRow()-1).add(endPosition.getColumn()-1, piece);
    }


    @Override
    public CBoard copy() {
        CBoard copiedBoard = new CBoard();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if (board.get(i).get(j) == null) {
                    continue;
                }
                ChessPiece piece = board.get(i).get(j).copy();

                copiedBoard.addPiece(new CPosition(i+1, j+1), piece);
            }
        }

        return copiedBoard;
    }
}
