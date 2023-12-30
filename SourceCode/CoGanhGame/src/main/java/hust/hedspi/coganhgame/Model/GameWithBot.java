package hust.hedspi.coganhgame.Model;

import hust.hedspi.coganhgame.Const;

import java.util.ArrayList;

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
                    moves.add(new Move(piece, board[row][col], tile));
                }
            }
        }
        return moves;
    }

    public void makeMove(Move move) {
        move.getPiece().move(move.getToTile());
        move.getToTile().setPiece(move.getPiece());
        move.getFromTile().setPiece(null);
        flipCurrentSide();
    }
}
