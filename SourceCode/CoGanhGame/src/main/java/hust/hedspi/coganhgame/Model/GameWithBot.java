package hust.hedspi.coganhgame.Model;

import hust.hedspi.coganhgame.Const;

import java.util.ArrayList;
import java.util.Stack;

public class GameWithBot extends Game{
    public GameWithBot(String playerName, int timeLimit) {
        super(playerName, "Bot", timeLimit);
    }

    public ArrayList<Move> generateMoves() {
        ArrayList<Move> moves = new ArrayList<>();
        for (int row = 0; row < Const.HEIGHT; row++) {
            for (int col = 0; col < Const.WIDTH; col++) {
                Piece piece = board[row][col].getPiece();
                if (piece == null || piece.getSide() != getCurrentPlayer().getSide()) {
                    continue;
                }
                ArrayList<Tile> availableTiles = board[row][col].getAvailableMoves(board);
                for (Tile tile : availableTiles) {
                    moves.add(new Move(board[row][col], tile));
                }
            }
        }
        return moves;
    }

    public MoveResult makeMove(Move move) {
        int fromRow = move.fromTile().getRow();
        int fromCol = move.fromTile().getCol();
        int toRow = move.toTile().getRow();
        int toCol = move.toTile().getCol();
        MoveResult moveResult = processMove(fromRow, fromCol, toRow, toCol);
        switchPlayer();
        return moveResult;
    }

    public void undoMove(Move move, MoveResult moveResult) {
        Piece piece = move.toTile().getPiece();
        move.toTile().removePiece();
        move.fromTile().setPiece(piece);
        if (moveResult.capturedPieces() != null) {
            getCurrentPlayer().increaseTotalPiece(moveResult.capturedPieces().size());
            for (Piece capturedPiece : moveResult.capturedPieces()) {
                capturedPiece.flipSide();
            }
        }
    }

    public int evaluateBoard() {
        int totalValue = 0;
        for (int row = 0; row < Const.HEIGHT; row++) {
            for (int col = 0; col < Const.WIDTH; col++) {
                Piece piece = board[row][col].getPiece();
                if (piece == null) {
                    continue;
                }
                if (piece.getSide() == Const.BLUE_SIDE) {
                    totalValue += 10;
                } else {
                    totalValue -= 10;
                }
            }
        }
        return totalValue;
    }
}
