package hust.hedspi.coganhgame.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Node;

import java.io.IOException;

public class PlayController {
    private Stage stage;

    public void Switch(ActionEvent event) {
        try {
            // Load the new scene
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hust/hedspi/coganhgame/View/intro-view.fxml"));
            Parent root = fxmlLoader.load();
            Scene newScene = new Scene(root);

            // Create a new stage and set the new scene
            Stage newStage = new Stage(StageStyle.DECORATED); // Use a decorated stage
            newStage.setScene(newScene);

            // Close the current stage if it exists
            if (stage != null) {
                stage.close();
            }

            // Set the new stage as the current stage
            stage = newStage;

            // Show the new stage
            stage.show();
        } catch (IOException e) {
            // Handle exceptions
            e.printStackTrace(); // Handle exception in a more appropriate way (e.g., logging)
        }
    }

    public void goBackToPlay(ActionEvent event) throws Exception  {
        Parent root = FXMLLoader.load(getClass().getResource("/hust/hedspi/coganhgame/View/how-view.fxml"));
        Scene newScene = new Scene(root);

        if (stage != null) {
            stage.close();
        }

        stage = new Stage();
        stage.setScene(newScene);
        stage.show();
    }

    public void goBackToFig2(ActionEvent event) throws Exception  {
        Parent root = FXMLLoader.load(getClass().getResource("/hust/hedspi/coganhgame/View/Figure2b.fxml"));
        Scene newScene = new Scene(root);


        if (stage != null) {
            stage.close();
        }

        stage = new Stage();
        stage.setScene(newScene);
        stage.show();
    }


    public void goBackToFig1(ActionEvent event) throws Exception  {
        Parent root = FXMLLoader.load(getClass().getResource("/hust/hedspi/coganhgame/View/Figure1a.fxml"));
        Scene newScene = new Scene(root);

        // Close the current stage (previous screen)
        if (stage != null) {
            stage.close();
        }

        // Switch to the new scene
        stage = new Stage();
        stage.setScene(newScene);
        stage.show();
    }


    public void nextBoard(ActionEvent event) throws Exception  {
        Parent root = FXMLLoader.load(getClass().getResource("/hust/hedspi/coganhgame/View/board2.fxml"));
        Scene newScene = new Scene(root);

        // Close the current stage (previous screen)
        if (stage != null) {
            stage.close();
        }

        // Switch to the new scene
        stage = new Stage();
        stage.setScene(newScene);
        stage.show();
    }

    public void goBackToMenu(ActionEvent event) throws Exception  {
        Parent root = FXMLLoader.load(getClass().getResource("/hust/hedspi/coganhgame/View/menu-view.fxml"));
        Scene newScene = new Scene(root);

        if (stage != null) {
            stage.close();
        }

        stage = new Stage();
        stage.setScene(newScene);
        stage.show();
    }
    public void SwitchBoard(ActionEvent event) throws Exception {
        // Load the new scene
        Parent root = FXMLLoader.load(getClass().getResource("/hust/hedspi/coganhgame/View/board-view.fxml"));
        Scene newScene = new Scene(root);

        if (stage != null) {
            stage.close();
        }

        // Switch to the new scene
        stage = new Stage();
        stage.setScene(newScene);
        stage.show();
    }


    public void SwitchFigure1(ActionEvent event) throws Exception {
        // Load the new scene
        Parent root = FXMLLoader.load(getClass().getResource("/hust/hedspi/coganhgame/View/play.fxml"));
        Scene newScene = new Scene(root);

        if (stage != null) {
            stage.close();
        }

        stage = new Stage();
        stage.setScene(newScene);
        stage.show();
    }

    public void SwitchFigure(ActionEvent event) throws Exception {
        // Load the new scene
        Parent root = FXMLLoader.load(getClass().getResource("/hust/hedspi/coganhgame/View/Figure1a.fxml"));
        Scene newScene = new Scene(root);

        if (stage != null) {
            stage.close();
        }

        stage = new Stage();
        stage.setScene(newScene);
        stage.show();
    }


    public void End(ActionEvent event) throws Exception {
        // Load the new scene
        Parent root = FXMLLoader.load(getClass().getResource("/hust/hedspi/coganhgame/View/end.fxml"));
        Scene newScene = new Scene(root);

        if (stage != null) {
            stage.close();
        }

        stage = new Stage();
        stage.setScene(newScene);
        stage.show();
    }

    public void SwitchFigure2(ActionEvent event) throws Exception {
        // Load the new scene
        Parent root = FXMLLoader.load(getClass().getResource("/hust/hedspi/coganhgame/View/Figure2b.fxml"));
        Scene newScene = new Scene(root);

        if (stage != null) {
            stage.close();
        }

        stage = new Stage();
        stage.setScene(newScene);
        stage.show();
    }

    public void SwitchFigure3(ActionEvent event) throws Exception {
        // Load the new scene
        Parent root = FXMLLoader.load(getClass().getResource("/hust/hedspi/coganhgame/View/Figure3a.fxml"));
        Scene newScene = new Scene(root);

        if (stage != null) {
            stage.close();
        }

        // Switch to the new scene
        stage = new Stage();
        stage.setScene(newScene);
        stage.show();
    }
}

