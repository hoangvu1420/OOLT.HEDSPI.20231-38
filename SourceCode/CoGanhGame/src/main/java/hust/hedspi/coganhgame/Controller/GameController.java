package hust.hedspi.coganhgame.Controller;

import hust.hedspi.coganhgame.ComponentView.PieceComp;
import hust.hedspi.coganhgame.ComponentView.TileComp;
import hust.hedspi.coganhgame.Const;
import hust.hedspi.coganhgame.Model.Game;
import hust.hedspi.coganhgame.Model.MoveResult;
import hust.hedspi.coganhgame.Model.Piece;
import hust.hedspi.coganhgame.Model.Tile;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.layout.Region;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class GameController {
    private final Game game;
    @FXML
    public Pane boardPane;
    @FXML
    public Button btnExit;

    private final Group tileCompGroup = new Group();
    private final Group pieceCompGroup = new Group();
    private final TileComp[][] viewBoard = new TileComp[Const.WIDTH][Const.HEIGHT];
    private final Map<Piece, PieceComp> pieceMap = new HashMap<>();
    private final ChangeListener<Number> timeLeftListener = (observable, oldValue, newValue) -> {
        if (newValue.intValue() <= 0) {
            System.out.println("Time out");
            // TODO: Delete this line after finished the UI
            switchPlayer();
        }
    };

    public GameController(String player1Name, String player2Name, int timeLimit) {
        this.game = new Game(player1Name, player2Name, timeLimit);
    }

    public GameController(Game game) {
        this.game = game;
    }

    @FXML
    public void initialize() {
        boardPane.setPrefSize(Const.BOARD_WIDTH, Const.BOARD_HEIGHT);
        initViewBoard();
        boardPane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE); // this line is used to make the boardPane fit the size of the board
        boardPane.getChildren().addAll(tileCompGroup, pieceCompGroup);
        // set the outline of the board
        boardPane.setStyle("-fx-border-color: #0F044C; -fx-border-width: 5px; -fx-border-radius: 15px; -fx-background-color: #EFEFEF;");
        game.getCurrentPlayer().getTimeLeft().addListener(timeLeftListener);
        game.getCurrentPlayer().setTurn();
    }

    public void initViewBoard() {
        Tile[][] modelBoard = game.getBoard();

        for (int row = 0; row < Const.HEIGHT; row++) {
            for (int col = 0; col < Const.WIDTH; col++) {
                TileComp tileComp = new TileComp(row, col);
                viewBoard[row][col] = tileComp;

                // we use numbers from 1-5 to represent the rows of the board
                // and letters from A-E to represent the columns of the board
                if (col == 0 || col == Const.WIDTH - 1) {
                    Label label = new Label(String.valueOf(Const.HEIGHT - row));
                    label.setLayoutX((double) Const.TILE_SIZE / 2 + (col == 0 ? -65 : +50));
                    label.setLayoutY((double) Const.TILE_SIZE / 2 - 10);
                    label.setFont(Const.COOR_FONT);
                    tileComp.getChildren().add(label);
                }
                if (row == 0 || row == Const.HEIGHT - 1) {
                    Label label = new Label(String.valueOf((char) (col + 65)));
                    label.setLayoutX((double) Const.TILE_SIZE / 2 - 5);
                    label.setLayoutY((double) Const.TILE_SIZE / 2 + (row == 0 ? -65 : +45));
                    label.setFont(Const.COOR_FONT);
                    tileComp.getChildren().add(label);
                }

                Tile modelTile = modelBoard[row][col];
                if (modelTile.hasPiece()) {
                    PieceComp pieceComp = makePieceComp(modelTile.getPiece().getSide(), row, col);
                    pieceCompGroup.getChildren().add(pieceComp);
                    pieceMap.put(modelTile.getPiece(), pieceComp);
                    if (modelTile.getPiece().getSide() != game.getCurrentPlayer().getSide()) {
                        pieceComp.setDisablePiece();
                    }
                }

                tileCompGroup.getChildren().add(tileComp);
            }
        }
        // draw a line from the top left tileComp to the bottom right tileComp
        drawLine(viewBoard[0][0], viewBoard[Const.HEIGHT - 1][Const.WIDTH - 1]);
        // draw a line from the top right tileComp to the bottom left tileComp
        drawLine(viewBoard[0][Const.WIDTH - 1], viewBoard[Const.HEIGHT - 1][0]);
        // for each tileComp in the first row, draw a line from that tileComp to the tileComp in the last row
        for (int i = 0; i < Const.WIDTH; i++) {
            drawLine(viewBoard[0][i], viewBoard[Const.HEIGHT - 1][i]);
        }
        // for each tileComp in the first column, draw a line from that tileComp to the tileComp in the last column
        for (int i = 0; i < Const.HEIGHT; i++) {
            drawLine(viewBoard[i][0], viewBoard[i][Const.WIDTH - 1]);
        }
        // draw a line from the center tileComp of the first row to the center tileComp of the last column
        drawLine(viewBoard[0][Const.WIDTH / 2], viewBoard[Const.HEIGHT / 2][Const.WIDTH - 1]);
        // draw a line from the center tileComp of the last column to the center tileComp of the last row
        drawLine(viewBoard[Const.HEIGHT / 2][Const.WIDTH - 1], viewBoard[Const.HEIGHT - 1][Const.WIDTH / 2]);
        // draw a line from the center tileComp of the last row to the center tileComp of the first column
        drawLine(viewBoard[Const.HEIGHT - 1][Const.WIDTH / 2], viewBoard[Const.HEIGHT / 2][0]);
        // draw a line from the center tileComp of the first column to the center tileComp of the first row
        drawLine(viewBoard[Const.HEIGHT / 2][0], viewBoard[0][Const.WIDTH / 2]);
    }

    private void drawLine(TileComp firstTile, TileComp lastTile) {
        double startX = firstTile.getCenterX();
        double startY = firstTile.getCenterY();
        double endX = lastTile.getCenterX();
        double endY = lastTile.getCenterY();
        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(Const.BOARD_STROKE_COLOR);
        line.setStrokeWidth(Const.BOARD_STROKE_WIDTH);
        // make the tip of the line rounded
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        tileCompGroup.getChildren().add(line);
    }

    private PieceComp makePieceComp(boolean side, int row, int col) {
        PieceComp pieceComp = new PieceComp(side, row, col);

        pieceComp.setOnMouseReleased(e -> {
            // when the piece is released, that means the player has finished moving the piece
            // then we process the move

            int newRow = toBoardPos(pieceComp.getLayoutY());
            int newCol = toBoardPos(pieceComp.getLayoutX());

            int oldRow = toBoardPos(pieceComp.getOldY());
            int oldCol = toBoardPos(pieceComp.getOldX());

            Tile[][] modelBoard = game.getBoard();
            MoveResult moveResult = game.processMove(modelBoard[oldRow][oldCol].getPiece(), newRow, newCol);

            if (moveResult.isValidMove()) {
                pieceComp.move(newRow, newCol);
                if (moveResult.isCaptureMove()) {
                    // if the move is a capture move, we remove the pieces that are captured
                    for (Piece capturedModelPiece : moveResult.getCapturedPieces()) {
                        PieceComp capturedPieceComp = pieceMap.get(capturedModelPiece);
                        capturedPieceComp.flipSide();
                    }
                }
                if (game.isGameOver()) {
                    endGame();
                    return;
                }
                switchPlayer();
            } else {
                pieceComp.abortMove();
            }

        });

        return pieceComp;
    }

    private int toBoardPos(double pixel) {
        // this method is used to convert the pixel position on the screen to the position on the board
        return (int) (pixel + Const.TILE_SIZE / 2) / Const.TILE_SIZE;
    }

    private void switchPlayer() {
        game.getCurrentPlayer().makeMove();
        game.getCurrentPlayer().getTimeLeft().removeListener(timeLeftListener);
        game.getCurrentPlayer().setTimeLeft(game.getTimeLimit());
        game.switchPlayer();
        game.getCurrentPlayer().getTimeLeft().addListener(timeLeftListener);
        game.getCurrentPlayer().setTurn();

        for (PieceComp piece : pieceMap.values()) {
            if (piece.getSide() == game.getCurrentPlayer().getSide()) {
                piece.setEnablePiece();
            } else {
                piece.setDisablePiece();
            }
        }
    }

    private void endGame() {
        game.getCurrentPlayer().getTimeLeft().removeListener(timeLeftListener);
        game.getCurrentPlayer().pauseTimer();
        for (PieceComp piece : pieceMap.values()) {
            piece.setDisablePiece();
        }

        System.out.println("Game over");
        System.out.println("Winner: " + game.getCurrentPlayer().getName());
        // TODO: Delete these lines after finished the UI
    }

    @FXML
    public void onBtnExitClick(ActionEvent actionEvent) {
        game.getCurrentPlayer().pauseTimer();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Confirmation");
        alert.setHeaderText(null);

        ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        if (!game.isGameOver()) {
            alert.getButtonTypes().setAll(yesButton, noButton, cancelButton);
            alert.setContentText("The game is not over yet. Do you want to save the game before exit?");
            ButtonType result = alert.showAndWait().orElse(cancelButton);
            if (result == yesButton) {
                game.saveGame();
                // User chose Yes -> hide the current stage
                Node source = (Node) actionEvent.getSource();
                Stage currentStage = (Stage) source.getScene().getWindow();
                currentStage.hide();
            } else if (result == noButton) {
                // User chose No -> hide the current stage
                Node source = (Node) actionEvent.getSource();
                Stage currentStage = (Stage) source.getScene().getWindow();
                currentStage.hide();
            } else {
                // User chose Cancel or closed the dialog -> play the timer again
                game.getCurrentPlayer().playTimer();
            }
        } else {
            alert.getButtonTypes().setAll(yesButton, noButton);
            alert.setContentText("Are you sure you want to exit?");
            ButtonType result = alert.showAndWait().orElse(noButton);
            if (result == yesButton) {
                Node source = (Node) actionEvent.getSource();
                Stage currentStage = (Stage) source.getScene().getWindow();
                currentStage.hide();
            }
        }


    }
}
