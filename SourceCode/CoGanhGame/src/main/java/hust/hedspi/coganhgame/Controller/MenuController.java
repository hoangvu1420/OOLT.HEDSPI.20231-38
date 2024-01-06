package hust.hedspi.coganhgame.Controller;

import hust.hedspi.coganhgame.Exception.GameNotFoundException;
import hust.hedspi.coganhgame.GameApplication;
import hust.hedspi.coganhgame.Model.Game.Game;
import hust.hedspi.coganhgame.Model.Settings.GameSettings;
import hust.hedspi.coganhgame.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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
            String gameMode = Utilities.showGameOptions("Choose Game Mode", "Choose Game Mode", "2 Players", "Play With Bot");
            if (gameMode == null || gameMode.isEmpty()) {
                // User closed the game options box, return to the menu
                return;
            }

            final GameController[] controller = {null};
            if(gameMode.equals("2 Players")){
                GameSettings gameSettings = Utilities.get2PlayersSettings();
                if (gameSettings == null) {
                    // User canceled the input
                    return;
                }
                controller[0] = new GameController(gameSettings.getPlayer1Name(), gameSettings.getPlayer2Name(), gameSettings.getGameTime());
            } else if (gameMode.equals("Play With Bot")) {
                GameSettings  gameSettings = Utilities.getPlayWithBotSettings();
                if(gameSettings == null){
                    return;
                }
                controller[0] = new GameController(gameSettings.getPlayer1Name(), gameSettings.getGameTime(), gameSettings.getBotLevel());
            }
            FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("View/game-view.fxml"));

            fxmlLoader.setControllerFactory(c -> controller[0]);
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
    }

    @FXML
    public void onExitClick() {
        if (Utilities.showConfirm("Exit", "Are you sure you want to exit?")) {
            System.exit(0);
        }
    }
}
