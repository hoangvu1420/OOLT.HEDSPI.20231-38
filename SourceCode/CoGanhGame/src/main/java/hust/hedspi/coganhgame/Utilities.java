package hust.hedspi.coganhgame;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.List;
import java.util.Optional;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import hust.hedspi.coganhgame.Model.Settings.GameSettings;

public final class Utilities {
    public static double TILE_SIZE;
    public static final int WIDTH = 5;
    public static final int HEIGHT = 5;
    public static double BOARD_WIDTH;
    public static double BOARD_HEIGHT;
    public static double PIECE_SIZE;
    public static final int BOARD_STROKE_WIDTH = 3;
    public static final int TOTAL_PIECE = 16;
    public static final boolean RED_SIDE = true;
    public static final boolean BLUE_SIDE = false;
    public static final double BOT_MOVE_DELAY = 0.5;
    public static final int BOT_LEVEL_EASY = 3;
    public static final int BOT_LEVEL_MEDIUM = 5;
    public static final int BOT_LEVEL_HARD = 7;

    public static final Color BOARD_STROKE_COLOR = Color.valueOf("#222831");
    public static final Color RED_PIECE_COLOR = Color.valueOf("#E21818");
    public static final Color BUE_PIECE_COLOR = Color.valueOf("#2666CF");
    public static final Font COOR_FONT = new Font("Arial", 18);

    public static void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void showAlert(String title, String header, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static boolean showConfirm(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }
    public static String showGameOptions(String title, String prompt, String buttonText1, String buttonText2) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle(title);
        String[] result = {null};
        // Create buttons
        Button button1 = new Button(buttonText1);
        Button button2 = new Button(buttonText2);
        double buttonWidth = 150.0; // You can adjust the width as needed
        button1.setMinWidth(buttonWidth);
        button2.setMinWidth(buttonWidth);
        // Set actions for the buttons
        button1.setOnAction(e -> {
            stage.close();
            result[0] = buttonText1;
        });
        button2.setOnAction(e -> {
            stage.close();
            result[0] = buttonText2;
        });
        Label label = new Label(prompt);
        label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;"); // Increase label font size

        // Increase button font size
        button1.setStyle("-fx-font-size: 16px;");
        button2.setStyle("-fx-font-size: 16px;");

        VBox layout = new VBox(20); // Increased spacing between label and buttons
        layout.setAlignment(Pos.CENTER); // Center the children vertically
        layout.getChildren().addAll(label, button1, button2); // Add label on top

        // Scene
        Scene scene = new Scene(layout, 450, 250); // Adjusted width and height


        // Stage setup
        stage.setScene(scene);

        // Show the stage and wait for the user's choice
        stage.showAndWait();

        // Return the text of the selected button
        return result[0];
    }
    public static GameSettings get2PlayersSettings() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Game's settings");
        dialog.setHeaderText(null);

        GameSettings gameSettings = new GameSettings();
        Optional<String> player1Name;
        do {
            dialog.getEditor().clear();
            dialog.setContentText("Enter Player 1 Name:");
            player1Name = dialog.showAndWait();
            if (!player1Name.isPresent()) {
                return null; // User canceled the input for player 1 name
            }
        } while (player1Name.get().isEmpty());
        gameSettings.setPlayer1Name(player1Name.orElse(""));

        Optional<String> player2Name;
        do {
            dialog.getEditor().clear();
            dialog.setContentText("Enter Player 2 Name:");
            player2Name = dialog.showAndWait();
            if (!player2Name.isPresent()) {
                return null; // User canceled the input for player 2 name
            }
        } while (player2Name.get().isEmpty());
        dialog.getEditor().clear();
        gameSettings.setPlayer2Name(player2Name.orElse(""));

        Optional<String> gameTime;
        do {
            dialog.getEditor().clear();
            dialog.setContentText("Enter Time Limit (in seconds):");
            gameTime = dialog.showAndWait();
            if (!gameTime.isPresent()) {
                return null; // User canceled the input
            }
        } while (gameTime.get().isEmpty()|| !gameTime.get().matches("\\d+"));
        int gameTimeValue = Integer.parseInt(gameTime.get());
        gameSettings.setGameTime(gameTimeValue);

        return gameSettings;
    }

    public static GameSettings getPlayWithBotSettings() {
        // Player 1 Name Input
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Game's settings");
        dialog.setHeaderText(null);
        GameSettings gameSettings = new GameSettings();
        Optional<String> player1Name;

        // Check if the user canceled the input
        do {
            dialog.getEditor().clear();
            dialog.setContentText("Enter Player Name:");
            player1Name = dialog.showAndWait();
            if (!player1Name.isPresent()) {
                return null; // User canceled the input for player 1 name
            }
        } while (player1Name.get().isEmpty());
        gameSettings.setPlayer1Name(player1Name.orElse(""));

        // Game Time Input
        Optional<String> gameTime;
        do {
            dialog.getEditor().clear();
            dialog.setContentText("Enter Time Limit (in seconds):");
            gameTime = dialog.showAndWait();
            if (!gameTime.isPresent()) {
                return null; // User canceled the input for player 1 name
            }
        } while (gameTime.get().isEmpty()|| !gameTime.get().matches("\\d+"));
        int gameTimeValue = Integer.parseInt(gameTime.get());
        gameSettings.setGameTime(gameTimeValue);

        // Bot Difficulty Input
        List<String> botDifficultyOptions = List.of("Easy", "Medium", "Hard");
        ChoiceDialog<String> botDifficultyDialog = new ChoiceDialog<>(botDifficultyOptions.get(0), botDifficultyOptions);
        botDifficultyDialog.setTitle("Game Settings");
        botDifficultyDialog.setHeaderText(null);
        botDifficultyDialog.setContentText("Choose Bot Difficulty:");
        Optional<String> botDifficulty= botDifficultyDialog.showAndWait();
        int botDifficultyValue = Utilities.BOT_LEVEL_EASY;
        if (!botDifficulty.isPresent()) {
            return null;
        }
        if(botDifficulty.get().equals("Easy")){
            botDifficultyValue = Utilities.BOT_LEVEL_EASY;
        }
        if(botDifficulty.get().equals("Medium")){
            botDifficultyValue = Utilities.BOT_LEVEL_MEDIUM;
        }
        if(botDifficulty.get().equals("Hard")){
            botDifficultyValue = Utilities.BOT_LEVEL_HARD;
        }

        gameSettings.setBotLevel(botDifficultyValue);

        return gameSettings;
    }

}

