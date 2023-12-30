package hust.hedspi.coganhgame.Model;

import hust.hedspi.coganhgame.Const;

import java.util.ArrayList;
import java.util.Stack;

public class GameWithBot extends Game{
    // this GameWithBot class is a subclass of Game class; it provides some methods to serve the bot which
    // is implemented with Minimax algorithm
    private static final Stack<Tile[][]> boardHistory = new Stack<>();
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

    public void makeMove(Move move) {
        int fromRow = move.fromTile().getRow();
        int fromCol = move.fromTile().getCol();
        int toRow = move.toTile().getRow();
        int toCol = move.toTile().getCol();
        boardHistory.push(board);
        processMove(fromRow, fromCol, toRow, toCol);
        switchPlayer();
    }

    public void undoMove() {
        if (boardHistory.isEmpty()) {
            return;
        }
        board = boardHistory.pop();
        switchPlayer();
        // the method of using stack to store board history is called Memento
    }
}
