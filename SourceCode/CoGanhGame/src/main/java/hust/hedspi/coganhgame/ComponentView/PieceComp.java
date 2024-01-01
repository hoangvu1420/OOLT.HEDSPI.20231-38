package hust.hedspi.coganhgame.ComponentView;

import hust.hedspi.coganhgame.Const;
import javafx.animation.TranslateTransition;
import javafx.scene.Cursor;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.util.Duration;

import static hust.hedspi.coganhgame.Const.TILE_SIZE;
import static hust.hedspi.coganhgame.Const.PIECE_SIZE;

public class PieceComp extends StackPane {
    private boolean side; // true: red, false: blue
    private double oldX, oldY;
    private final Ellipse ellipse;

    private static final double PIECE_STROKE_WIDTH = PIECE_SIZE * 0.05;

    public PieceComp(boolean side, int row, int col) {
        this.side = side;
        move(row, col);

        // make a black background
        Ellipse bg = new Ellipse(PIECE_SIZE, PIECE_SIZE * 0.832);
        bg.setFill(Color.BLACK);
        bg.setStroke(Color.BLACK);
        bg.setStrokeWidth(PIECE_STROKE_WIDTH);
        bg.setTranslateX((TILE_SIZE - PIECE_SIZE * 2) / 2);
        bg.setTranslateY((TILE_SIZE - PIECE_SIZE * 0.832 * 2) / 2 + PIECE_SIZE * 0.18);

        // make a red or blue piece
        ellipse = new Ellipse(PIECE_SIZE, PIECE_SIZE * 0.832);
        ellipse.setFill(side ? Const.RED_PIECE_COLOR : Const.BUE_PIECE_COLOR); // if the side is true, the piece is red, otherwise it is blue
        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(PIECE_STROKE_WIDTH);
        ellipse.setTranslateX((TILE_SIZE - PIECE_SIZE * 2) / 2);
        ellipse.setTranslateY((TILE_SIZE - PIECE_SIZE * 0.832 * 2) / 2);
        ellipse.setCursor(Cursor.HAND);

        getChildren().addAll(bg, ellipse);
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

        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(1));
        transition.setNode(this);

        transition.setFromX(oldX);
        transition.setFromY(oldY);
        transition.setToX(newX);
        transition.setToY(newY);

        transition.setOnFinished(event -> {
            this.relocate(newX, newY);
            this.setTranslateX(0);
            this.setTranslateY(0);
        });

        transition.play();

        oldX = newX;
        oldY = newY;
    }


    public void flipSide() {
        ellipse.setFill(ellipse.getFill() == Const.RED_PIECE_COLOR ? Const.BUE_PIECE_COLOR : Const.RED_PIECE_COLOR);
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