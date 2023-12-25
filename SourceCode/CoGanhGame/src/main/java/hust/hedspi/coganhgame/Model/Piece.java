package hust.hedspi.coganhgame.Model;

import java.io.Serializable;

public class Piece implements Serializable{
    private boolean side; // true: red, false: blue
    private int row;
    private int col;

    public Piece(boolean side, int row, int col) {
        this.side = side;
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void move(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean getSide() {
        return side;
    }

    public void flipSide() {
        side = !side;
    }
}
