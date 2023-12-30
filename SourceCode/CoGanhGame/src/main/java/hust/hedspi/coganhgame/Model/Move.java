package hust.hedspi.coganhgame.Model;

public class Move {
    private final Piece piece;
    private final Tile fromTile;
    private final Tile toTile;

    public Move(Piece piece, Tile fromTile, Tile toTile) {
        this.piece = piece;
        this.fromTile = fromTile;
        this.toTile = toTile;
    }

    public Piece getPiece() {
        return piece;
    }

    public Tile getFromTile() {
        return fromTile;
    }

    public Tile getToTile() {
        return toTile;
    }
}
