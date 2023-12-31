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
        // make the move for the bot player
        int fromRow = move.fromTile().getRow();
        int fromCol = move.fromTile().getCol();
        int toRow = move.toTile().getRow();
        int toCol = move.toTile().getCol();
        MoveResult moveResult = processMove(fromRow, fromCol, toRow, toCol);
        switchPlayer();
        return moveResult;
    }

    public void undoMove(Move move, MoveResult moveResult) {
        // undo the move for the bot player
        Piece piece = move.toTile().getPiece();
        move.toTile().removePiece();
        move.fromTile().setPiece(piece);
        if (moveResult.capturedPieces() != null) {
            getCurrentPlayer().increaseTotalPiece(moveResult.capturedPieces().size());
            getOpponent().decreaseTotalPiece(moveResult.capturedPieces().size());
            for (Piece capturedPiece : moveResult.capturedPieces()) {
                capturedPiece.flipSide();
            }
        }
        switchPlayer();
    }

    public static final int[][] favourablePosition = {
            {-1, 0, 1, 0, -1},
            {0, 3, 2, 3, 0},
            {1, 2, 4, 2, 1},
            {0, 3, 2, 3, 0},
            {-1, 0, 1, 0, -1}
    };

    public int evaluateBoard() {
        int totalValue = 0;
        for (int row = 0; row < Const.HEIGHT; row++) {
            for (int col = 0; col < Const.WIDTH; col++) {
                if (!board[row][col].hasPiece()) {
                    continue;
                }
                Piece piece = board[row][col].getPiece();
                if (piece.getSide() == Const.RED_SIDE) {
                    totalValue += 10;
                    totalValue += favourablePosition[row][col];
                } else {
                    totalValue -= 10;
                    totalValue -= favourablePosition[row][col];
                }
            }
        }
        return totalValue;
    }
}
