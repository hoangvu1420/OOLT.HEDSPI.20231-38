package hust.hedspi.coganhgame.Model;

import java.io.Serializable;

public class Tile implements Serializable{
    private Piece piece; // each tile may have a piece or not
    private final int row;
    private final int col;

    public Tile(Piece piece, int row, int col) {
        this.piece = piece;
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean hasPiece() {
        return piece != null;
    }

    public void removePiece() {
        this.piece = null;
    }
}
