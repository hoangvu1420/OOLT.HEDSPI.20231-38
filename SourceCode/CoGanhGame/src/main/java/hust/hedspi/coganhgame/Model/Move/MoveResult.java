package hust.hedspi.coganhgame.Model.Move;

import hust.hedspi.coganhgame.Model.Piece;

import java.util.ArrayList;

public record MoveResult(boolean isValidMove, ArrayList<Piece> capturedPieces) {
}
