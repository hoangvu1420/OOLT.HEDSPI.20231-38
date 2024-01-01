package hust.hedspi.coganhgame.Model.Game;

import hust.hedspi.coganhgame.Const;
import hust.hedspi.coganhgame.Exception.GameNotFoundException;
import hust.hedspi.coganhgame.Model.*;
import hust.hedspi.coganhgame.Model.Move.Move;
import hust.hedspi.coganhgame.Model.Move.MoveResult;
import hust.hedspi.coganhgame.Model.Player.BotPlayer;
import hust.hedspi.coganhgame.Model.Player.Player;
import hust.hedspi.coganhgame.Model.Tile.Tile;

import java.io.*;
import java.util.ArrayList;

public class Game implements Serializable {
    protected Tile[][] board;
    private final Player player1;
    private final Player player2;
    private Player currentPlayer;
    private final int timeLimit;

    public Game(String name1, String name2, int timeLimit) {
        this.player1 = new Player(name1, true, timeLimit); // player1 is red and turn first
        this.player2 = new Player(name2, false, timeLimit); // player2 is blue and turn second
        this.timeLimit = timeLimit;
        this.currentPlayer = this.player1;
        initBoard();
    }

    public Game(String playerName, int timeLimit, int botLevel) {
        this.player1 = new Player(playerName, true, timeLimit); // player1 is red and turn first
        this.player2 = new BotPlayer(timeLimit, botLevel); // player2 is blue and turn second
        this.timeLimit = timeLimit;
        this.currentPlayer = this.player1;
        initBoard();
    }

    private void initBoard() {
        // init board
        this.board = new Tile[Const.WIDTH][Const.HEIGHT];

        // init pieces
        for (int row = 0; row < Const.HEIGHT; row++) {
            for (int col = 0; col < Const.WIDTH; col++) {
                Piece piece = null;
                // if the side is true, the piece is red, otherwise it is blue
                // make the first row of the board red
                if (row == 0) {
                    piece = new Piece(true);
                }
                // make the last row of the board blue
                if (row == Const.HEIGHT - 1) {
                    piece = new Piece(false);
                }
                // make the first column of the board red
                if (col == 0) {
                    if (row == 1) {
                        piece = new Piece(true);
                    } else if (row == 2 || row == 3) {
                        piece = new Piece(false);
                    }
                }
                // make the last column of the board blue
                if (col == Const.WIDTH - 1) {
                    if (row == Const.HEIGHT - 2) {
                        piece = new Piece(false);
                    } else if (row == Const.HEIGHT - 3 || row == Const.HEIGHT - 4) {
                        piece = new Piece(true);
                    }
                }

                this.board[row][col] = Tile.getTileType(piece, row, col);
            }
        }
    }

    public Tile[][] getBoard() {
        return this.board;
    }

    public Player getPlayer1() {
        return this.player1;
    }

    public Player getPlayer2() {
        return this.player2;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public Player getOpponent() {
        if (this.currentPlayer == this.player1) {
            return this.player2;
        } else {
            return this.player1;
        }
    }

    public int getTimeLimit() {
        return this.timeLimit;
    }

    public void switchPlayer() {
        if (this.currentPlayer == this.player1) {
            this.currentPlayer = this.player2;
        } else {
            this.currentPlayer = this.player1;
        }
    }

    public MoveResult processMove(Move move) {
        int fromRow = move.fromTile().getRow();
        int fromCol = move.fromTile().getCol();
        int toRow = move.toTile().getRow();
        int toCol = move.toTile().getCol();
        if (toRow < 0 || toRow >= Const.HEIGHT || toCol < 0 || toCol >= Const.WIDTH) {
            // if the position (row, col) is out of the board, return invalid move
            return new MoveResult(false, null);
        }

        Piece piece = this.board[fromRow][fromCol].getPiece();
        if (this.board[toRow][toCol].hasPiece() || piece.getSide() != this.currentPlayer.getSide()) {
            // if the tile at (row, col) already has a piece, return invalid move
            return new MoveResult(false, null);
        }

        // get the connected tiles of the tile at (oldRow, oldCol)
        ArrayList<Tile> connectedTiles = board[fromRow][fromCol].getConnectedTiles(this.board);
        if (connectedTiles.contains(this.board[toRow][toCol])) {
            // if the tile at (row, col) is in the connected tiles, move the piece to the new position
            this.board[fromRow][fromCol].removePiece();
            this.board[toRow][toCol].setPiece(piece);
            ArrayList<Piece> capturedPieces = new ArrayList<>();
            capturedPieces.addAll(getCarriedPieces(toRow, toCol, board[toRow][toCol].getConnectedTiles(this.board)));
            capturedPieces.addAll(getSurroundedPieces());
            if (!capturedPieces.isEmpty()) {
                // if the captured pieces are not empty, return a capture move
                this.currentPlayer.increaseTotalPiece(capturedPieces.size());
                // decrease the number of pieces of the opponent
                getOpponent().decreaseTotalPiece(capturedPieces.size());
                return new MoveResult(true, capturedPieces);
            }
            return new MoveResult(true, null);
        }

        return new MoveResult(false, null);
    }

    private ArrayList<Piece> getCarriedPieces(int row, int col, ArrayList<Tile> connectedTiles) {
        ArrayList<Piece> carriedPieces = new ArrayList<>();

        Piece piece = this.board[row][col].getPiece();
        // loop through all the connected tiles, check each pair of connected tiles
        for (int i = 0; i < connectedTiles.size(); i++) {
            Tile tile1 = connectedTiles.get(i);
            for (int j = i + 1; j < connectedTiles.size(); j++) {
                Tile tile2 = connectedTiles.get(j);

                if (tile1.getRow() + tile2.getRow() != 2 * row || tile1.getCol() + tile2.getCol() != 2 * col) {
                    continue;
                }

                if (tile1.hasPiece() && tile2.hasPiece()) {
                    // if both tiles have pieces, check if the pieces have the same side
                    Piece piece1 = tile1.getPiece();
                    Piece piece2 = tile2.getPiece();
                    if (piece1.getSide() == piece2.getSide() && piece1.getSide() != piece.getSide()) {
                        // if the pieces have the same side and the same side is different from the side of the piece
                        carriedPieces.add(piece1);
                        carriedPieces.add(piece2);
                        piece1.flipSide();
                        piece2.flipSide();
                    }
                }
            }
        }

        return carriedPieces;
    }

    public ArrayList<Piece> getSurroundedPieces() {
        ArrayList<Piece> surroundedPieces = new ArrayList<>();
        boolean[][] visited = new boolean[Const.HEIGHT][Const.WIDTH];
        for (int row = 0; row < Const.HEIGHT; row++) {
            for (int col = 0; col < Const.WIDTH; col++) {
                Tile tile = this.board[row][col];
                if (tile.hasPiece() && !visited[row][col] && tile.getPiece().getSide() != this.currentPlayer.getSide()) {
                    // for each piece that has not been visited, we use flood fill algorithm to find the group of pieces
                    // that form a group, if the group is surrounded, we flip the side of the pieces in the group
                    // and add them to the surrounded pieces
                    ArrayList<Piece> group = new ArrayList<>();
                    if (floodFill(row, col, group, visited)) {
                        flipGroup(group);
                        surroundedPieces.addAll(group);
                        break;
                        // theoretically, for each move, there is only one group of pieces that is surrounded,
                        // so we can break the loop after we find the first group of pieces that is surrounded
                    }
                }
            }
        }
        return surroundedPieces;
    }

    private boolean floodFill(int row, int col, ArrayList<Piece> group, boolean[][] visited) {
        // this algorithm is used to find the pieces that form a group then check if the group is surrounded
        Piece piece = this.board[row][col].getPiece();
        visited[row][col] = true;
        group.add(piece);

        boolean isSurrounded = true;
        ArrayList<Tile> connectedTiles = board[row][col].getConnectedTiles(this.board);
        for (Tile tile : connectedTiles) {
            if (!tile.hasPiece()) {
                isSurrounded = false;
            } else if (tile.getPiece().getSide() == piece.getSide() && !visited[tile.getRow()][tile.getCol()]) {
                isSurrounded &= floodFill(tile.getRow(), tile.getCol(), group, visited);
            }
        }
        return isSurrounded;
    }

    private void flipGroup(ArrayList<Piece> group) {
        for (Piece piece : group) {
            piece.flipSide();
        }
    }

    public boolean isGameOver() {
        return getCurrentPlayer().getTotalPiece() == Const.TOTAL_PIECE || getOpponent().getTotalPiece() == Const.TOTAL_PIECE;
    }

    public void saveGame() {
        try {
            FileOutputStream fos = new FileOutputStream("game_state.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();
            fos.close();
        } catch (IOException ex) {
            System.out.println("Error saving game: " + ex.getMessage());
        }
    }

    public static Game loadGame() throws GameNotFoundException {
        Game game = null;
        try {
            File file = new File("game_state.txt");
            if (!file.exists() || file.length() == 0) {
                throw new GameNotFoundException();
            }
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            game = (Game) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error loading game: " + ex.getMessage());
        }
        return game;
    }

}
