package hust.hedspi.coganhgame.Controller;

import hust.hedspi.coganhgame.Const;
import hust.hedspi.coganhgame.GameApplication;
import hust.hedspi.coganhgame.Model.Game;
import hust.hedspi.coganhgame.Exception.GameNotFoundException;
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
//            GameController controller = new GameController("Player 1", "Player 2", 100);
            GameController controller = new GameController("Player 1", 100, Const.BOT_LEVEL_MEDIUM);
            fxmlLoader.setControllerFactory(c -> controller);
            Stage newStage = new Stage();
            newStage.setTitle("Co Ganh Game");
            newStage.setScene(new Scene(fxmlLoader.load()));

            newStage.setOnShown(event -> currentStage.hide());
            newStage.setOnHidden(event -> currentStage.show());
            // when the new stage is shown, the current stage is hidden and vice versa

            newStage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @FXML
    public void onContinueClick(ActionEvent actionEvent) {
        try {
            Game game;
            try {
                game = Game.loadGame();
            } catch (GameNotFoundException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
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
            e.printStackTrace();
        }
    }

    @FXML
    public void onHowClick(ActionEvent actionEvent) {
    }

    @FXML
    public void onExitClick() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to exit?");

        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
        if (result == ButtonType.OK){
            // User chose OK
            System.exit(0);
        }
    }

}
