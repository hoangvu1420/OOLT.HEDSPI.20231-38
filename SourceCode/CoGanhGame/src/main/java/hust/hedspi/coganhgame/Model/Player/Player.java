package hust.hedspi.coganhgame.Model.Player;

import hust.hedspi.coganhgame.Utilities;

import java.io.Serializable;

public abstract class Player implements Serializable{
    private final String name;
    private final boolean side; // true: red, false: black
    private int totalPiece;
    protected int totalTime = 0; // in milliseconds

    public Player(String name, boolean side) {
        this.name = name;
        this.side = side;
        this.totalPiece = Utilities.TOTAL_PIECE / 2;
    }

    public String getName() {
        return name;
    }

    public boolean getSide() {
        return side;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public int getTotalPiece() {
        return totalPiece;
    }

    public void increaseTotalPiece(int qty) {
        totalPiece += qty;
    }

    public void decreaseTotalPiece(int qty) {
        totalPiece -= qty;
    }

    public abstract void playTimer();

    public abstract void pauseTimer();
}
