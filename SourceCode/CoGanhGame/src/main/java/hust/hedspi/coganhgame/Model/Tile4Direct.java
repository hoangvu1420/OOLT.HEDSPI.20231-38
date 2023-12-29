package hust.hedspi.coganhgame.Model;

import hust.hedspi.coganhgame.Const;

import java.util.ArrayList;

public class Tile4Direct extends Tile {
    public Tile4Direct(Piece piece, int row, int col) {
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
        if (row < Const.HEIGHT - 1) {
            connectedTiles.add(board[row + 1][col]);
        }
        if (col > 0) {
            connectedTiles.add(board[row][col - 1]);
        }
        if (col < Const.WIDTH - 1) {
            connectedTiles.add(board[row][col + 1]);
        }
        return connectedTiles;
    }
}
