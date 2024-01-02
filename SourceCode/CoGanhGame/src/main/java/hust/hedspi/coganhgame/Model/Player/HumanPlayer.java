package hust.hedspi.coganhgame.Model.Player;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;

public class HumanPlayer extends Player{
    @Serial
    private static final long serialVersionUID = 6171647155107564395L;
    private final int timeLimit;
    private transient IntegerProperty timeLeft;
    private transient Timeline timeline; // in seconds

    public HumanPlayer(String name, boolean side, int timeLimit) {
        super(name, side);
        this.timeLimit = timeLimit;
        initTimeline();
    }

    private void initTimeline() {
        this.timeLeft = new SimpleIntegerProperty(timeLimit * 1000);
        timeline = new Timeline(new KeyFrame(Duration.millis(1), event -> {
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

    @Override
    public void playTimer() {
        timeline.play();
    }

    @Override
    public void pauseTimer() {
        timeline.pause();
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        initTimeline();
    }
}
