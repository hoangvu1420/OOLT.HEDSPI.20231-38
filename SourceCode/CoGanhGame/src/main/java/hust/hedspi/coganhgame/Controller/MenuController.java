package hust.hedspi.coganhgame.Controller;

import hust.hedspi.coganhgame.Exception.GameNotFoundException;
import hust.hedspi.coganhgame.GameApplication;
import hust.hedspi.coganhgame.Model.Game.Game;
import hust.hedspi.coganhgame.Model.Settings.GameSettings;
import hust.hedspi.coganhgame.Utilities.AdaptiveUtilities;
import hust.hedspi.coganhgame.Utilities.ViewUtilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


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
        Node source = (Node) actionEvent.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        String gameMode = AdaptiveUtilities.showGameOptions("Choose Game Mode", "Choose Game Mode", "2 Players", "Play With Bot");
        if (gameMode == null || gameMode.isEmpty()) {
            // User closed the game options box, return to the menu
            return;
        }

        final GameController[] controller = {null};
        if (gameMode.equals("2 Players")) {
            GameSettings gameSettings = AdaptiveUtilities.get2PlayersSettings();
            if (gameSettings == null) {
                // User canceled the input
                return;
            }
            controller[0] = new GameController(gameSettings.getPlayer1Name(), gameSettings.getPlayer2Name(), gameSettings.getGameTime());
        } else if (gameMode.equals("Play With Bot")) {
            GameSettings gameSettings = AdaptiveUtilities.getPlayWithBotSettings();
            if (gameSettings == null) {
                return;
            }
            controller[0] = new GameController(gameSettings.getPlayer1Name(), gameSettings.getGameTime(), gameSettings.getBotLevel());
        }

        showGameView(currentStage, controller[0]);
    }

    @FXML
    public void onContinueClick(ActionEvent actionEvent) {
        Game game;
        try {
            game = Game.loadGame();
        } catch (GameNotFoundException e) {
            ViewUtilities.showAlert("Error", "No saved game found!", AlertType.ERROR);
            return;
        }

        Node source = (Node) actionEvent.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();
        GameController controller = new GameController(game);
        showGameView(currentStage, controller);
    }

    private void showGameView(Stage currentStage, GameController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(GameApplication.class.getResource("View/game-view.fxml"));

        Stage newStage = null;
        try {
            fxmlLoader.setControllerFactory(c -> controller);
            newStage = new Stage();
            newStage.setTitle("Co Ganh Game");
            newStage.setScene(new Scene(fxmlLoader.load()));
            newStage.setResizable(false);
        } catch (IOException e) {
            ViewUtilities.showAlert("Error", "Error loading game view", e.getMessage(), AlertType.ERROR);
        }

        newStage.setOnShown(event -> currentStage.hide());
        newStage.setOnHidden(event -> currentStage.show());
        // when the new stage is shown, the current stage is hidden and vice versa

        newStage.show();
    }

    @FXML
    public void onHowClick(ActionEvent actionEvent) {
    }

    @FXML
    public void onExitClick() {
        if (ViewUtilities.showConfirm("Exit", "Are you sure you want to exit?")) {
            System.exit(0);
        }
    }
}
