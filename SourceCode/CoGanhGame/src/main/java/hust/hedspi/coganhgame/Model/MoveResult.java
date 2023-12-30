package hust.hedspi.coganhgame.Model;

import java.util.ArrayList;

public record MoveResult(boolean isValidMove, ArrayList<Piece> capturedPieces) {
}
