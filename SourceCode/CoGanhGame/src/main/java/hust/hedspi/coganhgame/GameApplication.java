package hust.hedspi.coganhgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class GameApplication extends Application {
    @Override
    public void start(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenHeight = screenBounds.getHeight();
        Utilities.TILE_SIZE = (int) (screenHeight / 6.5);
        Utilities.BOARD_WIDTH = Utilities.TILE_SIZE * Utilities.WIDTH;
        Utilities.BOARD_HEIGHT = Utilities.TILE_SIZE * Utilities.HEIGHT;
        Utilities.PIECE_SIZE = Utilities.TILE_SIZE * 0.22;
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("View/menu-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            // set the scene size to match the size of the root layout.
            stage.setResizable(false);
            stage.setTitle("Co Ganh Game");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e){
            Utilities.showAlert("Error", "Error loading menu view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}