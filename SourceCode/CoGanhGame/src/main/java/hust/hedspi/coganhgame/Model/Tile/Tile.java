package hust.hedspi.coganhgame.Model.Tile;

import hust.hedspi.coganhgame.Model.Piece;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Tile implements Serializable {
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

    public abstract ArrayList<Tile> getConnectedTiles(Tile[][] board);

    public ArrayList<Tile> getAvailableMoves(Tile[][] board) {
        ArrayList<Tile> availableMoves = new ArrayList<>();
        ArrayList<Tile> connectedTiles = getConnectedTiles(board);
        for (Tile tile : connectedTiles) {
            if (!tile.hasPiece()) {
                availableMoves.add(tile);
            }
        }
        return availableMoves;
    }

    public static Tile getTileType(Piece piece, int row, int col) {
        // if row + col is even or 0, return a Tile8Direct otherwise return a Tile4Direct
        if ((row + col) % 2 == 0) {
            return new Tile8Direct(piece, row, col);
        } else {
            return new Tile4Direct(piece, row, col);
        }
    }
}
