package hust.hedspi.coganhgame.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class RulesController {
    @FXML
    private TextArea txtRules;
    
    @FXML
    private TextArea txtIntroduction;
    @FXML
    private TextArea txtPresentation;
    @FXML
    private TextArea txtStartingGame;
    @FXML
    private TextArea txtGameplay;
    @FXML
    private TextArea txtGanh;
    @FXML
    private TextArea txtVayChet;
    @FXML
    private TextArea txtOpenPositionTrap;
    @FXML
    private TextArea  txtWinningGame;
    @FXML
    private Button btnClose;
    

    @FXML
    public void initialize() {
        // Initialize the text area with the game rules
       
    	 String introductionText = "•	Cờ Gánh, also known as Cờ Chém, is a strategic game for two players originating from Quảng Nam, Vietnam.\r\n"
         		+ "•	Rooted in local culture, the game evolved from simple items like clamshells and pebbles used as pieces and improvised chessboards drawn on tiled floors.\r\n"
         		+ "•	The game has gained popularity with specially designed game boards available in bookstores and stationery stores.\r\n"
         		+ "";
         String presentationText ="•	The Cờ Gánh board is a square divided into 16 squares with horizontal, vertical, and diagonal lines indicating piece movement paths.\r\n"
         		+ "•	The board has 25 intersections for piece placement.\r\n"
         		+ "•	Pieces: 16 in total, divided into 2 colors/types.\r\n"
         		+ "•	Initial piece placement:\r\n"
         		+ "•	5 pieces in the last row on one player's side.\r\n"
         		+ "•	2 pieces on the outer edges of the second row.\r\n"
         		+ "•	1 piece at the far left of the third row.\r\n"
         		+ "•	Pieces often have two sides with different colors, changed by flipping.\r\n"
         		+ "";
         String startingGameText = "•	Each player receives 8 pieces, colored differently from the opponent's pieces.\r\n"
         		+ "•	Players set up their pieces according to the specified arrangement.\r\n"
         		+ "";
         String gameplayText = "•	Players take turns moving pieces to an adjacent empty intersection.\r\n"
         		+ "•	Movements can be horizontal, vertical, or diagonal, following the grid lines.\r\n"
         		+ "•	The goal is to change all opponent's pieces' colors to match the player's own, leaving the opponent with no movable pieces.\r\n"
         		+ "";
         String ganhText = "•	Occurs when a player's piece moves between two opponent's pieces, changing the color of the adjacent opponent pieces.\r\n"
         		+ "•	Can \"Gánh\" 4 or 6 opponent's pieces simultaneously.\r\n"
         		+ "•	Opponent cannot \"Gánh\" when moving their piece.\r\n"
         		+ "";
         String vayChetText = "•	Happens when a player's piece is surrounded by opponent's pieces, making it unable to move.\r\n"
         		+ "•	The surrounded piece changes color.\r\n"
         		+ "";
         String openPositionTrapText = "•	Players can proactively create positions for the opponent's piece to \"Gánh.\"\r\n"
         		+ "•	Opponent is compelled to \"Gánh\" in such positions, providing strategic advantages.\r\n"
         		+ "";
         String winningGameText = "•	The game ends when there is only one type of piece left on the board.\r\n"
         		+ "•	The player with remaining pieces is declared the winner.\r\n"
         		+ "";
         
         
         txtIntroduction.setText(introductionText);
         txtPresentation.setText(presentationText);
         txtStartingGame.setText(startingGameText);
         txtGameplay.setText(gameplayText);
         txtGanh.setText(ganhText);
         txtVayChet.setText(vayChetText);
         txtOpenPositionTrap.setText(openPositionTrapText);
         txtWinningGame.setText(winningGameText);
  
    }

    @FXML
    public void onCloseClick(ActionEvent actionEvent) {
        // Close the rules screen when the "Close" button is clicked
        btnClose.getScene().getWindow().hide();
    }
}
