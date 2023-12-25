package hust.hedspi.coganhgame.Model;

import hust.hedspi.coganhgame.Const;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;

public class Player implements Serializable{
    @Serial
    private static final long serialVersionUID = 6171647155107564395L;
    private final String name;
    private final boolean side; // true: red, false: black
    private int totalPiece;
    private int totalTime = 0;
    private final int timeLimit;
    private transient IntegerProperty timeLeft;
    private transient Timeline timeline;

    // TODO: finish save/load game

    public Player(String name, boolean side, int timeLimit) {
        this.name = name;
        this.side = side;
        this.timeLimit = timeLimit;
        this.totalPiece = Const.TOTAL_PIECE / 2;

        initTimeline();
    }

    public void initTimeline() {
        this.timeLeft = new SimpleIntegerProperty(timeLimit);
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            totalTime++;
            timeLeft.set(timeLeft.get() - 1);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public IntegerProperty getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int time) {
        timeLeft.set(time);
    }

    public void playTimer() {
        timeline.play();
    }

    public void pauseTimer() {
        timeline.pause();
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

    public void setTurn() {
        System.out.println("It's " + name + "'s turn");
        playTimer();
    }

    public void makeMove() {
        pauseTimer();
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        initTimeline();
    }

}
