package hust.hedspi.coganhgame.Model;

import java.util.ArrayList;

public class MoveResult {

    private final boolean isValidMove;
    private final ArrayList<Piece> capturedPieces;

    public MoveResult(boolean isValidMove, ArrayList<Piece> capturedPieces) {
        // this constructor is used when the move is a capture move
        this.isValidMove = isValidMove;
        this.capturedPieces = capturedPieces;
    }

    public MoveResult(boolean isValidMove) {
        // this constructor is used when the move is a normal move
        this.isValidMove = isValidMove;
        this.capturedPieces = null;
    }

    public boolean isValidMove() {
        return isValidMove;
    }

    public boolean isCarryMove() {
        return capturedPieces != null;
    }

    public ArrayList<Piece> getCapturedPieces() {
        return capturedPieces;
    }

}
