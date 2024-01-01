package hust.hedspi.coganhgame.Model.Game;

import hust.hedspi.coganhgame.Const;
import hust.hedspi.coganhgame.Model.Move.Move;
import hust.hedspi.coganhgame.Model.Move.MoveResult;
import hust.hedspi.coganhgame.Model.Piece;
import hust.hedspi.coganhgame.Model.Tile.Tile;

import java.util.ArrayList;

public class GameWithBot extends Game {
    public GameWithBot(String playerName, int timeLimit, int botLevel) {
        super(playerName, timeLimit, botLevel);
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
        MoveResult moveResult = processMove(move);
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
}
