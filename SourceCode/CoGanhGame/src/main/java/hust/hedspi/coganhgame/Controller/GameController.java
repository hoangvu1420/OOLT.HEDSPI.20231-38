package hust.hedspi.coganhgame.Controller;

import hust.hedspi.coganhgame.ComponentView.PieceComp;
import hust.hedspi.coganhgame.ComponentView.TileComp;
import hust.hedspi.coganhgame.Model.Player.HumanPlayer;
import hust.hedspi.coganhgame.Utilities;
import hust.hedspi.coganhgame.Model.*;
import hust.hedspi.coganhgame.Model.Game.Game;
import hust.hedspi.coganhgame.Model.Game.GameWithBot;
import hust.hedspi.coganhgame.Model.Move.Move;
import hust.hedspi.coganhgame.Model.Move.MoveResult;
import hust.hedspi.coganhgame.Model.Player.BotPlayer;
import hust.hedspi.coganhgame.Model.Tile.Tile;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Task;
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
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import static hust.hedspi.coganhgame.Utilities.TILE_SIZE;
import static hust.hedspi.coganhgame.Utilities.WIDTH;
import static hust.hedspi.coganhgame.Utilities.HEIGHT;

public class GameController {
    private final Game game;
    @FXML
    public Pane boardPane;
    @FXML
    public Button btnExit;

    private Tile currentTile;
    private final Group tileCompGroup = new Group();
    private final Group pieceCompGroup = new Group();
    private final TileComp[][] viewBoard = new TileComp[WIDTH][HEIGHT];
    private final Map<Piece, PieceComp> pieceMap = new HashMap<>();
    private final ChangeListener<Number> timeLeftListener = (observable, oldValue, newValue) -> {
        if (newValue.intValue() <= 0) {
            System.out.println("Time out");
            // TODO: Delete this line after finished the UI, display the time out message on the UI
            switchPlayer();
        }
    };
    private final ExecutorService executor = Executors.newSingleThreadExecutor(); // this executor is used to run the botMoveTask

    public GameController(String player1Name, String player2Name, int timeLimit) {
        this.game = new Game(player1Name, player2Name, timeLimit);
    }

    public GameController(String player1Name, int timeLimit, int botLevel) {
        this.game = new GameWithBot(player1Name, timeLimit, botLevel);
    }

    public GameController(Game game) {
        this.game = game;
    }

    @FXML
    public void initialize() {
        boardPane.setPrefSize(Utilities.BOARD_WIDTH, Utilities.BOARD_HEIGHT);
        initViewBoard();
        boardPane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE); // this line is used to make the boardPane fit the size of the board
        boardPane.getChildren().addAll(tileCompGroup, pieceCompGroup);
        // set the outline of the board
        boardPane.setStyle("-fx-border-color: #0F044C; -fx-border-width: 5px; -fx-border-radius: 15px; -fx-background-color: #EFEFEF;");
        ((HumanPlayer) game.getCurrentPlayer()).getTimeLeft().addListener(timeLeftListener);
        game.getCurrentPlayer().playTimer();
    }

    private void initViewBoard() {
        Tile[][] modelBoard = game.getBoard();

        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                TileComp tileComp = new TileComp(row, col);
                viewBoard[row][col] = tileComp;

                // we use numbers from 1-5 to represent the rows of the board
                // and letters from A-E to represent the columns of the board
                if (col == 0 || col == WIDTH - 1) {
                    Label label = new Label(String.valueOf(HEIGHT - row));
                    label.setLayoutX((double) TILE_SIZE / 2 + (col == 0 ? -(TILE_SIZE * 0.35) : (TILE_SIZE * 0.3)));
                    label.setLayoutY((double) TILE_SIZE / 2 - 9);
                    label.setFont(Utilities.COOR_FONT);
                    tileComp.getChildren().add(label);
                }
                if (row == 0 || row == HEIGHT - 1) {
                    Label label = new Label(String.valueOf((char) (col + 65)));
                    label.setLayoutX((double) TILE_SIZE / 2 - 6);
                    label.setLayoutY((double) TILE_SIZE / 2 + (row == 0 ? -(TILE_SIZE * 0.35 + 5) : (TILE_SIZE * 0.3 - 5)));
                    label.setFont(Utilities.COOR_FONT);
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
        drawLine(viewBoard[0][0], viewBoard[HEIGHT - 1][WIDTH - 1]);
        // draw a line from the top right tileComp to the bottom left tileComp
        drawLine(viewBoard[0][WIDTH - 1], viewBoard[HEIGHT - 1][0]);
        // for each tileComp in the first row, draw a line from that tileComp to the tileComp in the last row
        for (int i = 0; i < WIDTH; i++) {
            drawLine(viewBoard[0][i], viewBoard[HEIGHT - 1][i]);
        }
        // for each tileComp in the first column, draw a line from that tileComp to the tileComp in the last column
        for (int i = 0; i < HEIGHT; i++) {
            drawLine(viewBoard[i][0], viewBoard[i][WIDTH - 1]);
        }
        // draw a line from the center tileComp of the first row to the center tileComp of the last column
        drawLine(viewBoard[0][WIDTH / 2], viewBoard[HEIGHT / 2][WIDTH - 1]);
        // draw a line from the center tileComp of the last column to the center tileComp of the last row
        drawLine(viewBoard[HEIGHT / 2][WIDTH - 1], viewBoard[HEIGHT - 1][WIDTH / 2]);
        // draw a line from the center tileComp of the last row to the center tileComp of the first column
        drawLine(viewBoard[HEIGHT - 1][WIDTH / 2], viewBoard[HEIGHT / 2][0]);
        // draw a line from the center tileComp of the first column to the center tileComp of the first row
        drawLine(viewBoard[HEIGHT / 2][0], viewBoard[0][WIDTH / 2]);
    }

    private void drawLine(TileComp firstTile, TileComp lastTile) {
        double startX = firstTile.getCenterX();
        double startY = firstTile.getCenterY();
        double endX = lastTile.getCenterX();
        double endY = lastTile.getCenterY();
        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(Utilities.BOARD_STROKE_COLOR);
        line.setStrokeWidth(Utilities.BOARD_STROKE_WIDTH);
        // make the tip of the line rounded
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        tileCompGroup.getChildren().add(line);
    }

    private PieceComp makePieceComp(boolean side, int row, int col) {
        PieceComp pieceComp = new PieceComp(side, row, col);
        AtomicReference<Double> mouseX = new AtomicReference<>((double) 0);
        AtomicReference<Double> mouseY = new AtomicReference<>((double) 0);

        pieceComp.getEllipse().setOnMousePressed(e -> {
            mouseX.set(e.getSceneX());
            mouseY.set(e.getSceneY());
            int rowPressed = toBoardPos(pieceComp.getLayoutY());
            int colPressed = toBoardPos(pieceComp.getLayoutX());
            if (currentTile == null) {
                currentTile = game.getBoard()[rowPressed][colPressed];
            } else if (rowPressed == currentTile.getRow() && colPressed == currentTile.getCol()) {
                return;
            }
            currentTile = game.getBoard()[rowPressed][colPressed];
            for (Tile move : currentTile.getAvailableMoves(game.getBoard())) {
                System.out.println(move.getRow() + "-" + move.getCol());
                // TODO: Delete this line after finished the UI,
                //  replace it with a method to highlight the valid moves on the UI
                // set the tileComp of the valid moves to be highlighted
            }
            // bring the piece to the front
            pieceComp.toFront();
        });

        pieceComp.getEllipse().setOnMouseDragged(e -> {
            pieceComp.relocate(e.getSceneX() - mouseX.get() + pieceComp.getOldX(), e.getSceneY() - mouseY.get() + pieceComp.getOldY());
            int rowDragged = toBoardPos(pieceComp.getLayoutY());
            int colDragged = toBoardPos(pieceComp.getLayoutX());
            if (rowDragged != currentTile.getRow() || colDragged != currentTile.getCol()) {
                Tile draggedTile = game.getBoard()[rowDragged][colDragged];
                if (currentTile.getAvailableMoves(game.getBoard()).contains(draggedTile)) {
                    System.out.println("To: " + draggedTile.getRow() + "-" + draggedTile.getCol());
                    // TODO: Delete this line after finished the UI,
                    //  replace it with a method to highlight the tileComp that the piece is being dragged to on the UI
                    // set the tileComp that the piece is being dragged to be highlighted
                }
            }
        });

        pieceComp.getEllipse().setOnMouseReleased(e -> {
            // when the piece is released, that means the player has finished moving the piece
            // then we process the move

            int newRow = toBoardPos(pieceComp.getLayoutY());
            int newCol = toBoardPos(pieceComp.getLayoutX());

            int oldRow = toBoardPos(pieceComp.getOldY());
            int oldCol = toBoardPos(pieceComp.getOldX());

            Move move = new Move(game.getBoard()[oldRow][oldCol], game.getBoard()[newRow][newCol]);
            MoveResult moveResult = game.processMove(move);

            if (moveResult.isValidMove()) {
                pieceComp.move(newRow, newCol);
                if (moveResult.capturedPieces() != null) {
                    // if the move is a capture move, we flip the side of the captured pieces
                    for (Piece capturedModelPiece : moveResult.capturedPieces()) {
                        PieceComp capturedPieceComp = pieceMap.get(capturedModelPiece);
                        capturedPieceComp.flipSide();
                    }
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
        return (int) (pixel + TILE_SIZE / 2) / TILE_SIZE;
    }

    private void switchPlayer() {
        if (game.isGameOver()) {
            endGame();
            return;
        }
        if (game.getCurrentPlayer() instanceof HumanPlayer) {
            game.getCurrentPlayer().pauseTimer();
            ((HumanPlayer) game.getCurrentPlayer()).getTimeLeft().removeListener(timeLeftListener);
            ((HumanPlayer) game.getCurrentPlayer()).setTimeLeft(game.getTimeLimit() * 1000);
        }
        game.switchPlayer();

        System.out.println("Current player: " + game.getCurrentPlayer().getName());
        // TODO: Delete this line after finished the UI, display the name of the current player on the UI

        if (game.getCurrentPlayer() instanceof HumanPlayer) {
            ((HumanPlayer) game.getCurrentPlayer()).getTimeLeft().addListener(timeLeftListener);
            game.getCurrentPlayer().playTimer();
            for (PieceComp piece : pieceMap.values()) {
                if (piece.getSide() == game.getCurrentPlayer().getSide()) {
                    piece.setEnablePiece();
                } else {
                    piece.setDisablePiece();
                }
            }
        } else {
            for (PieceComp piece : pieceMap.values()) {
                piece.setDisablePiece();
            }
            botMakeMove();
        }
    }

    private void botMakeMove() {
        // Create a new Task
        Task<Move> botMoveTask = new Task<>() {
            @Override
            protected Move call() {
                // Perform the long-running operation (bot deciding its move)
                BotPlayer botPlayer = (BotPlayer) game.getCurrentPlayer();
                botPlayer.playTimer();
                Move botMove = botPlayer.getBestMove((GameWithBot) game);
                botPlayer.pauseTimer();
                return botMove;
            }
        };

        // Set up what to do when the Task is done
        botMoveTask.setOnSucceeded(event -> {
            Move botMove = botMoveTask.getValue();

            PieceComp botPieceComp = pieceMap.get(botMove.fromTile().getPiece());
            MoveResult botMoveResult = game.processMove(botMove);
            botPieceComp.slowMove(botMove.toTile().getRow(), botMove.toTile().getCol());

            PauseTransition pause = new PauseTransition(Duration.seconds(Utilities.BOT_MOVE_DELAY));
            pause.setOnFinished(e -> {
                if (botMoveResult.capturedPieces() != null) {
                    // if the move is a capture move, we flip the side of the captured pieces
                    for (Piece capturedModelPiece : botMoveResult.capturedPieces()) {
                        PieceComp capturedPieceComp = pieceMap.get(capturedModelPiece);
                        capturedPieceComp.flipSide();
                    }
                }
                System.out.println("Position count: " + BotPlayer.positionCount);
                // TODO: Delete these lines after finished the UI,
                //  display the time and position count of the bot on the UI
                BotPlayer.positionCount = 0;
                switchPlayer();
            });
            pause.play();
        });

        // get the executor to run the task
        executor.execute(botMoveTask);
    }

    private void endGame() {
        executor.shutdown(); // shutdown the executor to avoid memory leak
        if (game.getCurrentPlayer() instanceof HumanPlayer) {
            ((HumanPlayer) game.getCurrentPlayer()).getTimeLeft().removeListener(timeLeftListener);
            game.getCurrentPlayer().pauseTimer();
        }
        for (PieceComp piece : pieceMap.values()) {
            piece.setDisablePiece();
        }

        System.out.println("Game over");
        System.out.println("Winner: " + game.getCurrentPlayer().getName());
        System.out.println(game.getPlayer1().getName() + " total time: " + game.getPlayer1().getTotalTime());
        System.out.println(game.getPlayer2().getName() + " total time: " + game.getPlayer2().getTotalTime());
        // TODO: Delete these lines after finished the UI, display the winner and total time of each player on the UI
    }

    @FXML
    public void onBtnExitClick(ActionEvent actionEvent) {
        if (game.getCurrentPlayer() instanceof HumanPlayer) {
            game.getCurrentPlayer().pauseTimer();
        }

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
                if (game.getCurrentPlayer() instanceof HumanPlayer) {
                    game.getCurrentPlayer().playTimer();
                }
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
