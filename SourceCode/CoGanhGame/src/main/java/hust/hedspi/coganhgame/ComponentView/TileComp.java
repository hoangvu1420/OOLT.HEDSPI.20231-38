package hust.hedspi.coganhgame.ComponentView;

import javafx.scene.layout.Pane;

import static hust.hedspi.coganhgame.Utilities.TILE_SIZE;

public class TileComp extends Pane {
    public TileComp(int row, int col) {
        // Position the tile
        relocate(col * TILE_SIZE, row * TILE_SIZE);
    }

    // Get center point of the tile
    public double getCenterX() {
        return getLayoutX() + (double) TILE_SIZE / 2;
    }

    public double getCenterY() {
        return getLayoutY() + (double) TILE_SIZE / 2;
    }

}
