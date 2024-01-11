package hust.hedspi.coganhgame.Utilities;

import hust.hedspi.coganhgame.Model.Settings.GameSettings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

import java.util.List;
import java.util.Optional;

public final class ViewUtilities {
    public static final int BOARD_STROKE_WIDTH = 3;
    public static final Color BOARD_STROKE_COLOR = Color.valueOf("#222831");
    public static final Color RED_PIECE_COLOR = Color.valueOf("#E21818");
    public static final Color BUE_PIECE_COLOR = Color.valueOf("#2666CF");
    public static final Font COOR_FONT = new Font("Arial", 20);

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

    public static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
        label.setStyle("-fx-font-size: 25px; -fx-font-weight: bold;"); // Increase label font size

        // Increase button font size
        button1.setStyle("-fx-font-size: 16px;");
        button2.setStyle("-fx-font-size: 16px;");

        VBox layout = new VBox(10); // Increased spacing between label and buttons
        layout.setAlignment(Pos.CENTER); // Center the children vertically
        layout.getChildren().addAll(label, button1, button2); // Add label on top
        VBox.setMargin(label, new Insets(0, 0, 10, 0)); // Adjust top margin of label

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
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Game's settings");
        dialog.setHeaderText(null);

        // Set the button types
        ButtonType saveButtonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create the grid pane
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField player1NameField = new TextField();
        TextField player2NameField = new TextField();
        TextField gameTimeField = new TextField();

        grid.add(new Label("Enter Player 1 Name:"), 0, 0);
        grid.add(player1NameField, 1, 0);
        grid.add(new Label("Enter Player 2 Name:"), 0, 1);
        grid.add(player2NameField, 1, 1);
        grid.add(new Label("Enter Time Limit (in seconds):"), 0, 2);
        grid.add(gameTimeField, 1, 2);

        dialog.getDialogPane().setContent(grid);

        // Enable/Disable save button depending on whether a name was entered
        Button saveButton = (Button) dialog.getDialogPane().lookupButton(saveButtonType);
        saveButton.setDisable(true);


        // Do some validation (disable the save button if any field is empty)
        player1NameField.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(newValue.trim().isEmpty() || player2NameField.getText().trim().isEmpty() || gameTimeField.getText().trim().isEmpty() || !gameTimeField.getText().matches("\\d+"));
        });

        player2NameField.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(newValue.trim().isEmpty() || player1NameField.getText().trim().isEmpty() || gameTimeField.getText().trim().isEmpty() || !gameTimeField.getText().matches("\\d+"));
        });

        gameTimeField.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(newValue.trim().isEmpty() || player1NameField.getText().trim().isEmpty() || player2NameField.getText().trim().isEmpty() || !gameTimeField.getText().matches("\\d+"));
        });

        // Set the result converter
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return new Pair<>(player1NameField.getText(), player2NameField.getText() + " " + gameTimeField.getText());
            }
            return null;
        });

        // Show the dialog and wait for user input
        Optional<Pair<String, String>> result = dialog.showAndWait();

        // Process the result
        if (result.isPresent()) {
            if(result.get().getKey().isEmpty()){
                ViewUtilities.showAlert("Player's name can not be empty");
            }
            GameSettings gameSettings = new GameSettings();
            gameSettings.setPlayer1Name(result.get().getKey());
            gameSettings.setPlayer2Name(result.get().getValue().split(" ")[0]);
            gameSettings.setGameTime(Integer.parseInt(result.get().getValue().split(" ")[1]));
            return gameSettings;
        } else {
            return null; // User canceled the input
        }
    }

    public static GameSettings getPlayWithBotSettings() {
        Dialog<GameSettings> dialog = new Dialog<>();
        dialog.setTitle("Game's settings");
        dialog.setHeaderText(null);

        // Set the button types
        ButtonType okButtonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, cancelButtonType);

        // Create the grid pane
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField player1NameField = new TextField();
        TextField gameTimeField = new TextField();

        grid.add(new Label("Enter Player Name:"), 0, 0);
        grid.add(player1NameField, 1, 0);
        grid.add(new Label("Enter Time Limit (in seconds):"), 0, 1);
        grid.add(gameTimeField, 1, 1);

        // Bot Difficulty Input
        List<String> botDifficultyOptions = List.of("Easy", "Medium", "Hard");
        ChoiceBox<String> botDifficultyChoiceBox = new ChoiceBox<>();
        botDifficultyChoiceBox.getItems().addAll(botDifficultyOptions);
        botDifficultyChoiceBox.setValue(botDifficultyOptions.get(0));

        grid.add(new Label("Choose Bot Difficulty:"), 0, 2);
        grid.add(botDifficultyChoiceBox, 1, 2);

        dialog.getDialogPane().setContent(grid);

        // Enable/Disable ok button depending on whether all fields are filled and time is a valid integer
        Button okButton = (Button) dialog.getDialogPane().lookupButton(okButtonType);
        okButton.setDisable(true);

        // Do some validation (disable the ok button if any field is empty or time is not a valid integer)
        player1NameField.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.trim().isEmpty() || gameTimeField.getText().trim().isEmpty() || !gameTimeField.getText().matches("\\d+"));
        });

        gameTimeField.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.trim().isEmpty() || player1NameField.getText().trim().isEmpty() || !gameTimeField.getText().matches("\\d+"));
        });

        // Set the result converter
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                GameSettings gameSettings = new GameSettings();
                gameSettings.setPlayer1Name(player1NameField.getText());
                gameSettings.setGameTime(Integer.parseInt(gameTimeField.getText()));

                String selectedBotDifficulty = botDifficultyChoiceBox.getValue();
                int botDifficultyValue = getBotDifficultyValue(selectedBotDifficulty);
                gameSettings.setBotLevel(botDifficultyValue);

                return gameSettings;
            }
            return null;
        });

        // Show the dialog and wait for user input
        Optional<GameSettings> result = dialog.showAndWait();

        return result.orElse(null);
    }

    private static int getBotDifficultyValue(String selectedBotDifficulty) {
        return switch (selectedBotDifficulty) {
            case "Easy" -> Constants.BOT_LEVEL_EASY;
            case "Medium" -> Constants.BOT_LEVEL_MEDIUM;
            case "Hard" -> Constants.BOT_LEVEL_HARD;
            default -> Constants.BOT_LEVEL_EASY; // Default to Easy if unknown difficulty
        };
    }
}
