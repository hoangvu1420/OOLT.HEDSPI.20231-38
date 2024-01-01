package hust.hedspi.coganhgame.Model;

import hust.hedspi.coganhgame.Const;

import java.util.ArrayList;

public class BotPlayer extends Player{
    // this class is used to simulate a bot player using minimax algorithm and alpha-beta pruning
    private final int botLevel;
    // the difficulty of the bot player which is the depth of the minimax algorithm, the higher the difficulty is,
    // the smarter the bot player is but also cost more time to calculate the best move.
    public BotPlayer(int timeLimit, int botLevel) {
        super("Bot", Const.BLUE_SIDE, timeLimit); // the bot player is always on the blue side (false)
        this.botLevel = botLevel;
    }
    public static int positionCount = 0;

    public Move getBestMove(GameWithBot game) {
        Move bestMove = null;
        int bestScore = -9999;
        ArrayList<Move> allMoves = game.generateMoves();
        int[] scores = new int[allMoves.size()];
        for (int i = 0; i < allMoves.size(); i++) {
            Move move = allMoves.get(i);
            MoveResult moveResult = game.makeMove(move);
            int score = minimax(game, this.botLevel - 1, -10000, 10000, false);
            scores[i] = score;
            game.undoMove(move, moveResult);
            if (score >= bestScore) {
                bestScore = score;
            }
        }
        ArrayList<Move> bestMoves = new ArrayList<>();
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
            return -evaluateBoard(game.getBoard());
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

    public final int[][] favourablePosition = {
            {-1, 0, 1, 0, -1},
            {0, 3, 2, 3, 0},
            {1, 2, 4, 2, 1},
            {0, 3, 2, 3, 0},
            {-1, 0, 1, 0, -1}
    };

    public int evaluateBoard(Tile[][] board) {
        int totalValue = 0;
        for (int row = 0; row < Const.HEIGHT; row++) {
            for (int col = 0; col < Const.WIDTH; col++) {
                if (!board[row][col].hasPiece()) {
                    continue;
                }
                Piece piece = board[row][col].getPiece();
                if (piece.getSide() == Const.RED_SIDE) {
                    totalValue += 10;
                    totalValue += favourablePosition[row][col];
                } else {
                    totalValue -= 10;
                    totalValue -= favourablePosition[row][col];
                }
            }
        }
        return totalValue;
    }
}
