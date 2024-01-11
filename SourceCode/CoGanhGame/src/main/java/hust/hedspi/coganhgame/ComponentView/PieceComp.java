package hust.hedspi.coganhgame.ComponentView;

import hust.hedspi.coganhgame.Utilities.Constants;
import hust.hedspi.coganhgame.Utilities.ViewUtilities;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.util.Duration;

import static hust.hedspi.coganhgame.Utilities.AdaptiveUtilities.PIECE_SIZE;
import static hust.hedspi.coganhgame.Utilities.AdaptiveUtilities.TILE_SIZE;

public class PieceComp extends StackPane {
    private boolean side; // true: red, false: blue
    private double oldX, oldY;
    private final Ellipse ellipse;

    private static final double PIECE_STROKE_WIDTH = PIECE_SIZE * 0.08;

    public PieceComp(boolean side, int row, int col) {
        this.side = side;
        move(row, col);

        // make a black background
        Ellipse background = new Ellipse(PIECE_SIZE, PIECE_SIZE * 0.832);
        background.setFill(Color.BLACK);
        background.setStroke(Color.BLACK);
        background.setStrokeWidth(PIECE_STROKE_WIDTH);
        background.setTranslateX((TILE_SIZE - PIECE_SIZE * 2) / 2);
        background.setTranslateY((TILE_SIZE - PIECE_SIZE * 0.832 * 2) / 2 + PIECE_SIZE * 0.18);

        // make a red or blue piece
        ellipse = new Ellipse(PIECE_SIZE, PIECE_SIZE * 0.832);
        ellipse.setFill(side ? ViewUtilities.RED_PIECE_COLOR : ViewUtilities.BUE_PIECE_COLOR); // if the side is true, the piece is red, otherwise it is blue
        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(PIECE_STROKE_WIDTH);
        ellipse.setTranslateX((TILE_SIZE - PIECE_SIZE * 2) / 2);
        ellipse.setTranslateY((TILE_SIZE - PIECE_SIZE * 0.832 * 2) / 2);
        ellipse.setCursor(Cursor.HAND);

        getChildren().addAll(background, ellipse);
    }

    public double getOldY() {
        return oldY;
    }

    public double getOldX() {
        return oldX;
    }

    public boolean getSide() {
        return side;
    }

    public Ellipse getEllipse() {
        return ellipse;
    }

    public void move(int row, int col) {
        oldY = row * TILE_SIZE;
        oldX = col * TILE_SIZE;
        relocate(oldX, oldY);
    }

    public void slowMove(int row, int col) {
        double newY = row * TILE_SIZE;
        double newX = col * TILE_SIZE;

        Timeline timeline = new Timeline();
        KeyValue kvX = new KeyValue(this.translateXProperty(), newX - oldX);
        KeyValue kvY = new KeyValue(this.translateYProperty(), newY - oldY);
        KeyFrame kf = new KeyFrame(Duration.seconds(Constants.BOT_MOVE_DELAY), kvX, kvY);
        timeline.getKeyFrames().add(kf);

        timeline.setOnFinished(event -> {
            this.relocate(newX, newY);
            this.setTranslateX(0);
            this.setTranslateY(0);
        });

        this.toFront();
        timeline.play();

        oldX = newX;
        oldY = newY;
    }

    public void flipSide() {
        ellipse.setFill(ellipse.getFill() == ViewUtilities.RED_PIECE_COLOR ? ViewUtilities.BUE_PIECE_COLOR : ViewUtilities.RED_PIECE_COLOR);
        side = !side;
    }

    public void abortMove() {
        relocate(oldX, oldY);
    }

    public void setDisablePiece() {
        ellipse.setDisable(true);
    }

    public void setEnablePiece() {
        ellipse.setDisable(false);
    }
}