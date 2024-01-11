package hust.hedspi.coganhgame.Utilities;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.List;
import java.util.Optional;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import hust.hedspi.coganhgame.Model.Settings.GameSettings;

import javafx.geometry.Insets;
import javafx.util.Pair;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar;

public final class AdaptiveUtilities {
    public static double TILE_SIZE;
    public static double BOARD_WIDTH;
    public static double BOARD_HEIGHT;
    public static double PIECE_SIZE;

    public static void setProperties(double screenHeight) {
        TILE_SIZE = screenHeight / 7.5;
        BOARD_WIDTH = TILE_SIZE * Constants.WIDTH;
        BOARD_HEIGHT = TILE_SIZE * Constants.HEIGHT;
        PIECE_SIZE = TILE_SIZE * 0.22;
    }

}

