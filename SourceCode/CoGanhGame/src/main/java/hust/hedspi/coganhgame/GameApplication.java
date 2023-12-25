package hust.hedspi.coganhgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameApplication extends Application {
    @Override
    public void start(Stage stage) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("View/menu-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            // set the scene size to match the size of the root layout.
            stage.setResizable(false);
            stage.setTitle("Co Ganh Game");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}