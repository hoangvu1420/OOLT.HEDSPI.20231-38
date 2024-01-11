package hust.hedspi.coganhgame.Model.Player;

import hust.hedspi.coganhgame.Utilities.Constants;
import hust.hedspi.coganhgame.Model.Game.GameWithBot;
import hust.hedspi.coganhgame.Model.Move.Move;
import hust.hedspi.coganhgame.Model.Move.MoveResult;
import hust.hedspi.coganhgame.Model.Piece;
import hust.hedspi.coganhgame.Model.Tile.Tile;

import java.util.ArrayList;

public class BotPlayer extends Player {
    // this class is used to simulate a bot player using minimax algorithm and alpha-beta pruning
    private final int botLevel; // the botLevel is the depth of the minimax algorithm
    private long startTime;

    public BotPlayer(int botLevel) {
        super("Bot", Constants.BLUE_SIDE); // the bot player is always on the blue side (false)
        this.botLevel = botLevel;
    }

    public int getBotLevel() {
        return botLevel;
    }

    public static int positionCount = 0;

    public Move getBestMove(GameWithBot game) {
        Move bestMove;
        int bestScore = -9999;
        ArrayList<Move> allMoves = game.generateMoves();
        int[] scores = new int[allMoves.size()];
        ArrayList<Move> bestMoves = new ArrayList<>();
        for (int i = 0; i < allMoves.size(); i++) {
            Move move = allMoves.get(i);
            MoveResult moveResult = game.makeMove(move);
            if (game.isGameOver()) {
                bestMoves.add(move);
            } else {
                int score = minimax(game, this.botLevel - 1, -10000, 10000, false);
                scores[i] = score;
                bestScore = Math.max(bestScore, score);
            }
            game.undoMove(move, moveResult);
        }
        if (!bestMoves.isEmpty()) {
            // when bestMoves is not empty, it means that there is a move that can end the game.
            // if we don't choose the move that can end the game,
            // the bot will try to maximize the board value on its side before ending the game and lead to time-wasting
            bestMove = bestMoves.get((int) (Math.random() * bestMoves.size()));
            return bestMove;
        }
        for (int i = 0; i < allMoves.size(); i++) {
            if (scores[i] == bestScore) {
                bestMoves.add(allMoves.get(i));
            }
        }
        // pick a random move from the best moves
        bestMove = bestMoves.get((int) (Math.random() * bestMoves.size()));
        return bestMove;
    }

    private int minimax(GameWithBot game, int depth, int alpha, int beta, boolean maximizingPlayer) {
        positionCount++;
        if (depth == 0 || game.isGameOver()) {
            return evaluateBoard(game.getBoard());
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

    private final int[][] favourablePosition = {
            {-1, 0, 1, 0, -1},
            {0, 0, 1, 0, 0},
            {1, 1, 2, 1, 1},
            {0, 0, 1, 0, 0},
            {-1, 0, 1, 0, -1}
    };

    private int evaluateBoard(Tile[][] board) {
        int totalValue = 0;
        for (int row = 0; row < Constants.HEIGHT; row++) {
            for (int col = 0; col < Constants.WIDTH; col++) {
                if (!board[row][col].hasPiece()) {
                    continue;
                }
                Piece piece = board[row][col].getPiece();
                if (piece.getSide() == Constants.RED_SIDE) {
                    totalValue -= 10;
                    totalValue -= favourablePosition[row][col];
                } else {
                    totalValue += 10;
                    totalValue += favourablePosition[row][col];
                }
            }
        }
        return totalValue;
    }

    @Override
    public void playTimer() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void pauseTimer() {
        long endTime = System.currentTimeMillis();
        int time = (int) (endTime - startTime);
        totalTime += time;
    }
}
