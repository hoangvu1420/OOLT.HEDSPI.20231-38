package hust.hedspi.coganhgame.Model;

import java.io.Serializable;

public class Piece implements Serializable{
    private boolean side; // true: red, false: blue

    public Piece(boolean side) {
        this.side = side;
    }

    public boolean getSide() {
        return side;
    }

    public void flipSide() {
        side = !side;
    }
}
