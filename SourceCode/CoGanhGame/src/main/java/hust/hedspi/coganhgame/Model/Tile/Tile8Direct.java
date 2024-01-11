package hust.hedspi.coganhgame.Model.Tile;

import hust.hedspi.coganhgame.Utilities.Constants;
import hust.hedspi.coganhgame.Model.Piece;

import java.util.ArrayList;

public class Tile8Direct extends Tile {
    public Tile8Direct(Piece piece, int row, int col) {
        super(piece, row, col);
    }

    @Override
    public ArrayList<Tile> getConnectedTiles(Tile[][] board) {
        ArrayList<Tile> connectedTiles = new ArrayList<>();
        int row = getRow();
        int col = getCol();
        if (row > 0) {
            connectedTiles.add(board[row - 1][col]);
        }
        if (row < Constants.HEIGHT - 1) {
            connectedTiles.add(board[row + 1][col]);
        }
        if (col > 0) {
            connectedTiles.add(board[row][col - 1]);
        }
        if (col < Constants.WIDTH - 1) {
            connectedTiles.add(board[row][col + 1]);
        }
        if (row > 0 && col > 0) {
            connectedTiles.add(board[row - 1][col - 1]);
        }
        if (row < Constants.HEIGHT - 1 && col < Constants.WIDTH - 1) {
            connectedTiles.add(board[row + 1][col + 1]);
        }
        if (row > 0 && col < Constants.WIDTH - 1) {
            connectedTiles.add(board[row - 1][col + 1]);
        }
        if (row < Constants.HEIGHT - 1 && col > 0) {
            connectedTiles.add(board[row + 1][col - 1]);
        }
        return connectedTiles;
    }
}
