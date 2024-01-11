package hust.hedspi.coganhgame.Controller;

import hust.hedspi.coganhgame.Exception.GameNotFoundException;

import java.io.IOException;

import hust.hedspi.coganhgame.GameApplication;
import hust.hedspi.coganhgame.Model.Game.Game;
import hust.hedspi.coganhgame.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;


public class MenuController {
    @FXML
    public Button btnNewGame;
    @FXML
    public Button btnContinue;
    @FXML
    public Button btnHow;
    @FXML
    public Button btnExit;

    @FXML
    protected void onNewGameClick(ActionEvent actionEvent) {
        try {
            Node source = (Node) actionEvent.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();

            FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("View/game-view.fxml"));
            // TODO:
            //  - Add a method to allow user to choose game mode: 1 player or 2 players
            //  - Add a method to allow user to choose time limit and player name
            //  - Add a method to allow user to choose bot level if they choose to play with bot
            //  - Call the constructor of GameController accordingly to the user's choices
            GameController controller = new GameController("Player 1", "Player 2", 10); // constructor for 2 players
//            GameController controller = new GameController("Player 1", 100, Utilities.BOT_LEVEL_HARD); // constructor for 1 player
            fxmlLoader.setControllerFactory(c -> controller);
            Stage newStage = new Stage();
            newStage.setTitle("Co Ganh Game");
            newStage.setScene(new Scene(fxmlLoader.load()));

            newStage.setOnShown(event -> currentStage.hide());
            newStage.setOnHidden(event -> currentStage.show());
            // when the new stage is shown, the current stage is hidden and vice versa

            newStage.show();
        } catch (Exception e) {
            Utilities.showAlert("Error", "Error loading game view", e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    public void onContinueClick(ActionEvent actionEvent) {
        try {
            Game game;
            try {
                game = Game.loadGame();
            } catch (GameNotFoundException e) {
                Utilities.showAlert("Error", "No saved game found!", AlertType.ERROR);
                return;
            }

            Node source = (Node) actionEvent.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("View/game-view.fxml"));
            GameController controller = new GameController(game);
            fxmlLoader.setControllerFactory(c -> controller);
            Stage newStage = new Stage();
            newStage.setTitle("Co Ganh Game");
            newStage.setScene(new Scene(fxmlLoader.load()));

            newStage.setOnShown(event -> currentStage.hide());
            newStage.setOnHidden(event -> currentStage.show());
            // when the new stage is shown, the current stage is hidden and vice versa

            newStage.show();
        } catch (Exception e) {
            Utilities.showAlert("Error", "Error loading game view", e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    public void onHowClick(ActionEvent actionEvent) {
//    	try {
//            Node source = (Node) actionEvent.getSource();
//            Stage currentStage = (Stage) source.getScene().getWindow();
//
//            FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("View/rules-view.fxml"));
//            Stage newStage = new Stage();
//            newStage.setTitle("Cờ Gánh - How to Play");
//            Scene scene = new Scene(fxmlLoader.load());
//            newStage.setScene(scene);
//            newStage.setOnShown(event -> currentStage.hide());
//            newStage.setOnHidden(event -> currentStage.show());
//
//            newStage.show();
//        } catch (IOException e) {
//            e.printStackTrace(); // Handle the exception appropriately
//        }
    	
    	   try {
    		   Node source = (Node) actionEvent.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("View/presentation-view.fxml"));
               Stage presentationStage = new Stage();
               presentationStage.setTitle("How to Play");
               Scene scene = new Scene(fxmlLoader.load());
               presentationStage.setScene(scene);
               presentationStage.show();
           } catch (IOException e) {
               showAlert("Error", "Error loading presentation view", e.getMessage(), Alert.AlertType.ERROR);
           }
    	
    }
    
    private boolean showConfirm(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }

    private void showAlert(String title, String header, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void onExitClick() {
        if (Utilities.showConfirm("Exit", "Are you sure you want to exit?")) {
            System.exit(0);
        }
    }
}
