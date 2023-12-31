package hust.hedspi.coganhgame.Model;

import hust.hedspi.coganhgame.Const;

import java.util.ArrayList;

public class BotPlayer extends Player{
    // this class is used to simulate a bot player using minimax algorithm and alpha-beta pruning
    private final int depth;
    // the difficulty of the bot player which is the depth of the minimax algorithm, the higher the difficulty is,
    // the smarter the bot player is but also cost more time to calculate the best move.
    public BotPlayer(int timeLimit, int depth) {
        super("Bot", Const.BLUE_SIDE, timeLimit); // the bot player is always on the blue side (false)
        this.depth = depth;
    }

    public Move getBestMove(GameWithBot game) {
        int depth = this.depth;
        Move bestMove = null;
        int bestScore = -9999;
        ArrayList<Move> allMoves = game.generateMoves();
        for (Move move : allMoves) {
            MoveResult moveResult = game.makeMove(move);
            int score = minimax(game, depth, -10000, 10000, false);
            game.undoMove(move, moveResult);
            if (score >= bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }
        return bestMove;
    }

    private int minimax(GameWithBot game, int depth, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0 || game.isGameOver()) {
            return -game.evaluateBoard();
        }
        if (maximizingPlayer) {
            int maxEval = -9999;
            ArrayList<Move> allMoves = game.generateMoves();
            for (Move move : allMoves) {
                MoveResult moveResult = game.makeMove(move);
                int eval = minimax(game, depth - 1, alpha, beta, false);
                game.undoMove(move, moveResult);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxEval;
        } else {
            int minEval = 9999;
            ArrayList<Move> allMoves = game.generateMoves();
            for (Move move : allMoves) {
                MoveResult moveResult = game.makeMove(move);
                int eval = minimax(game, depth - 1, alpha, beta, true);
                game.undoMove(move, moveResult);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return minEval;
        }
    }
}
