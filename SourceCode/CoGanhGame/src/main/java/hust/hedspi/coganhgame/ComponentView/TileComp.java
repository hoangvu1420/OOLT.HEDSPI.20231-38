package hust.hedspi.coganhgame.ComponentView;

import hust.hedspi.coganhgame.Utilities.Constants;
import hust.hedspi.coganhgame.Utilities.ViewUtilities;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;

import static hust.hedspi.coganhgame.Utilities.AdaptiveUtilities.TILE_SIZE;
import static hust.hedspi.coganhgame.Utilities.AdaptiveUtilities.PIECE_SIZE;

public class TileComp extends StackPane {
    private final Ellipse highlighter = new Ellipse(PIECE_SIZE * 0.5, PIECE_SIZE * 0.5);

    public TileComp(int row, int col) {
        // Position the tile
        relocate(col * TILE_SIZE, row * TILE_SIZE);
        setPrefSize(TILE_SIZE, TILE_SIZE);

        Pane tilePane = new Pane();
        tilePane.setPrefSize(TILE_SIZE, TILE_SIZE);

        double x0 = TILE_SIZE / 2;
        double y0 = TILE_SIZE / 2;

        // from the center of the tile, draw 4 lines to make a plus
        if (row > 0) {
            tilePane.getChildren().add(drawLine(x0, y0, x0, 0));
        }
        if (row < Constants.HEIGHT - 1) {
            tilePane.getChildren().add(drawLine(x0, y0, x0, TILE_SIZE));
        }
        if (col > 0) {
            tilePane.getChildren().add(drawLine(x0, y0, 0, y0));
        }
        if (col < Constants.WIDTH - 1) {
            tilePane.getChildren().add(drawLine(x0, y0, TILE_SIZE, y0));
        }

        if ((row + col) % 2 == 0) {
            // add 4 more lines to make a cross
            if (row > 0 && col > 0) {
                tilePane.getChildren().add(drawLine(x0, y0, x0 - TILE_SIZE * 0.5, y0 - TILE_SIZE * 0.5));
            }
            if (row < Constants.HEIGHT - 1 && col < Constants.WIDTH - 1) {
                tilePane.getChildren().add(drawLine(x0, y0, x0 + TILE_SIZE * 0.5, y0 + TILE_SIZE * 0.5));
            }
            if (row > 0 && col < Constants.WIDTH - 1) {
                tilePane.getChildren().add(drawLine(x0, y0, x0 + TILE_SIZE * 0.5, y0 - TILE_SIZE * 0.5));
            }
            if (row < Constants.HEIGHT - 1 && col > 0) {
                tilePane.getChildren().add(drawLine(x0, y0, x0 - TILE_SIZE * 0.5, y0 + TILE_SIZE * 0.5));
            }
        }
        getChildren().add(tilePane);

        // add highlighter
        highlighter.setFill(null);
        highlighter.setStroke(ViewUtilities.RED_PIECE_COLOR);
        highlighter.setStrokeWidth(PIECE_SIZE * 0.1);
        highlighter.setVisible(false);
        getChildren().add(highlighter);
    }

    private Line drawLine(double x1, double y1, double x2, double y2) {
        Line line = new Line(x1, y1, x2, y2);
        line.setStroke(ViewUtilities.BOARD_STROKE_COLOR);
        line.setStrokeWidth(ViewUtilities.BOARD_STROKE_WIDTH);
        // make the tip of the line rounded
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        return line;
    }

    public void highlight(boolean side) {
        Color color = Color.web(side ? ViewUtilities.RED_PIECE_COLOR.toString() : ViewUtilities.BUE_PIECE_COLOR.toString(), 0.7);
        highlighter.setStroke(color);
        highlighter.setVisible(true);
    }

    public void removeHighlight() {
        highlighter.setVisible(false);
    }

    public void fillHighlighter() {
        highlighter.setFill(highlighter.getStroke());
    }

    public void unfillHighlighter() {
        highlighter.setFill(null);
    }
}
