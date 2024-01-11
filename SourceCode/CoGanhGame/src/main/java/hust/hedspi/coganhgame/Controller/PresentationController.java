package hust.hedspi.coganhgame.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

public class PresentationController {

    @FXML
    private TextArea txtBoard;
    @FXML
    private Label boardTitle;
    @FXML
    private ImageView boardImageId;
    @FXML
    private Label subSection;
    
    private int currentSlide = 0;

    @FXML
    private void initialize() {
        loadSlide(currentSlide);
    }

    @FXML
    private void onBackClick() {
        if (currentSlide > 0) {
            currentSlide--;
            loadSlide(currentSlide);
        }
    }

    @FXML
    private void onNextClick() {
        if (currentSlide < 10) {
            currentSlide++;
            loadSlide(currentSlide);
        }
    }

    private void loadSlide(int slideIndex) {
        switch (slideIndex) {
        	case 0:
        		boardTitle.setText("Introduction to the game of Cờ gánh");
        		txtBoard.setText("""
                        Cờ gánh, also known as Cờ chém, is a strategic game for two players, originating from Quảng Nam, Vietnam. There are various theories about the formation of this simple and unique game, but in essence, it is a distinctive cultural feature creatively developed by the local people.\r
                        Initially, the locals used simple items to make the chessboard and pieces, such as drawing the board on a tiled floor and using clamshells, seashells, pebbles…\r
                        """);
        	
        		break;
            case 1:
            	boardTitle.setText("Board and pieces of Cờ gánh");
                txtBoard.setText("""
                        The Cờ Gánh board is square, divided into 16 squares, and marked with horizontal, vertical, and diagonal lines to indicate the allowed movement paths of the pieces. There are 25 intersections on the board to place the pieces.\r
                        \r
                        """);

                break;
            case 2:
            	boardTitle.setText("Board and pieces of Cờ gánh");
                txtBoard.setText("""
                        Cờ gánh pieces include 16 pieces, divided into 2 colors (or 2 types). At the beginning of the game, the pieces are placed as follow:\r
                        5 pieces of one player are placed at the 5 intersections of the last row on their side of the board.\r
                        2 pieces are placed at the outer edges of the second row.\r
                        1 piece is placed at the far left of the third row.\r
                        """);
                break;
            case 3:
            	boardTitle.setText("How to play Cờ gánh");
                txtBoard.setText("""
                        1.Starting the game: each player is given 8 pieces, colored (or identified) differently from the opponent's pieces. Players arrange their pieces as set up above.\r

                        2. While playing: each player alternately moves any of their pieces to an adjacent empty intersection on the square grid, horizontally, vertically, or diagonally at will, as long as there is no piece already there and following the grid lines.\r

                        3. The goal of the game is to change all the colors (or identities) of the opponent's pieces to match their own, leaving one player with no pieces to move.\r
                        """);
                break;
                
            case 4:
            	boardTitle.setText("Differents way to change the color of your opponent’s pieces");
            	subSection.setText("Gánh\n");
                txtBoard.setText("""
                        When a piece of one side moves between two pieces of the other ,then the two adjacent opponent's pieces are considered "Gánh" and their color is changed to become the color of the middle piece.\r
                        A player can only "Gánh" when actively moving their piece between two opponent's pieces, not when the opponent moves their piece.\r
                        In the move to "Gánh" the opponent's pieces, it is possible to "Gánh" 4 or 6 opponent's pieces at the same time. This move is also called "chầu" 4 or "chầu" 6.\r
                        \r
                        """);
                break;
            case 5:
            	boardTitle.setText("Differents way to change the color of your opponent’s pieces");
            	subSection.setText("Vây / Chẹt");
                txtBoard.setText("""
                        When a player's piece is surrounded by the opponent's pieces, making it unable to move, it is considered to be "Vây" or "Chẹt" (similar to playing Go). Then the surrounded piece will change color. \r
                        \r
                        """);
                break;
            case 6:
            	boardTitle.setText("Differents way to change the color of your opponent’s pieces");
            	subSection.setText("Open position trap");
                txtBoard.setText("""
                        In some cases, a player can proactively create a position for the opponent's piece to move in between to "Gánh" their piece. The goal may be to then "Gánh" the opponent's piece chầu 4 or chầu 6, or to create a pathway for further moves. Such a move is called an "Open" move.\r
                        When a player proactively creates an "Open" position for the opponent to "Gánh," then it's the opponent's turn, and they are compelled to "Gánh." This is a strategy of the player. This position is not only used to change the color of many of the opponent's pieces but also to escape from a difficult position.\r
                        \r
                        \r
                        """);
                break;
            case 7:
            	boardTitle.setText("End of the game");
            	subSection.setText("");
                txtBoard.setText("The game ends when there is only one type of piece left on the board, and the player with pieces remaining is the winner.\r\n");
                break;
            
        }
    }
}
