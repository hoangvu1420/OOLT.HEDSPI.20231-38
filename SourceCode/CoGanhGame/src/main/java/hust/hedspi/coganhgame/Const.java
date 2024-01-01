package hust.hedspi.coganhgame;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public final class Const {
    public static final int TILE_SIZE = 160;
    public static final int WIDTH = 5;
    public static final int HEIGHT = 5;
    public static final int BOARD_WIDTH = WIDTH * TILE_SIZE;
    public static final int BOARD_HEIGHT = HEIGHT * TILE_SIZE;
    public static final int BOARD_STROKE_WIDTH = 3;
    public static final double PIECE_SIZE = TILE_SIZE * 0.20;
    public static final int TOTAL_PIECE = 16;
    public static final boolean RED_SIDE = true;
    public static final boolean BLUE_SIDE = false;
    public static final int BOT_MOVE_DELAY = 1;
    public static final int BOT_LEVEL_EASY = 3;
    public static final int BOT_LEVEL_MEDIUM = 5;
    public static final int BOT_LEVEL_HARD = 7;

    public static final Color BOARD_STROKE_COLOR = Color.valueOf("#222831");
    public static final Color RED_PIECE_COLOR = Color.valueOf("#E21818");
    public static final Color BUE_PIECE_COLOR = Color.valueOf("#2666CF");
    public static final Font COOR_FONT = new Font("Arial", 20);
}
