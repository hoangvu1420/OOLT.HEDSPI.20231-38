package hust.hedspi.coganhgame.Model;

public class BotPlayer extends Player{
    // this class is used to simulate a bot player using minimax algorithm and alpha-beta pruning
    private final int difficulty;
    // the difficulty of the bot player which is the depth of the minimax algorithm, the higher the difficulty is,
    // the smarter the bot player is but also cost more time to calculate the best move.
    public BotPlayer(int timeLimit, int difficulty) {
        super("Bot", false, timeLimit); // the bot player is always on the blue side (false)
        this.difficulty = difficulty;
    }

//    public Move getBestMove(Tile[][] board) {
//        // this method is used to get the best move for the bot player
//        // it is implemented with minimax algorithm and alpha-beta pruning
//        // the algorithm is described in the report
//        // the heuristic function is described in the report
//        // the algorithm is implemented in the method minimax()
//        // the heuristic function is implemented in the method evaluate()
//        // the alpha-beta pruning is implemented in the method minimax()
//        // the method minimax() is implemented in the class Player
//        // the method evaluate() is implemented in the class Player
//        // the method getBestMove() is implemented in the class Player
//        return super.getBestMove(board);
//        // TODO: get on this tomorrow
//    }
}
