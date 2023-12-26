package hust.hedspi.coganhgame.Model;

import hust.hedspi.coganhgame.Const;
import hust.hedspi.coganhgame.exception.GameNotFoundException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game implements Serializable {
    private Tile[][] board;
    private final Player player1;
    private final Player player2;
    private Player currentPlayer;

    // make a map to store the connected tiles of any tile in the board
    private final Map<Tile, ArrayList<Tile>> connectionMap;
    private final int timeLimit;

    public Game(String name1, String name2, int timeLimit) {
        this.player1 = new Player(name1, true, timeLimit); // player1 is red and turn first
        this.player2 = new Player(name2, false, timeLimit); // player2 is blue and turn second
        this.timeLimit = timeLimit;
        this.currentPlayer = this.player1;
        this.connectionMap = new HashMap<>();
        initBoard();
        initConnectionMap();
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
                    piece = new Piece(true, row, col);
                }
                // make the last row of the board blue
                if (row == Const.HEIGHT - 1) {
                    piece = new Piece(false, row, col);
                }
                // make the first column of the board red
                if (col == 0) {
                    if (row == 1) {
                        piece = new Piece(true, row, col);
                    } else if (row == 2 || row == 3) {
                        piece = new Piece(false, row, col);
                    }
                }
                // make the last column of the board blue
                if (col == Const.WIDTH - 1) {
                    if (row == Const.HEIGHT - 2) {
                        piece = new Piece(false, row, col);
                    } else if (row == Const.HEIGHT - 3 || row == Const.HEIGHT - 4) {
                        piece = new Piece(true, row, col);
                    }
                }

                this.board[row][col] = new Tile(piece, row, col);
            }
        }
    }

    private void initConnectionMap() {
        // there are 2 types of tiles:
        // - the first type is the tile that connected to 8 surrounding tiles in 8 directions.
        // - the second type is the tile that connected to 4 surrounding tiles in 4 directions.
        // the top left tile is the first type tile, then the next tile to its right is the second type tile, and so on.

        boolean flag = true; // true for first type tile, false for second type tile
        for (int row = 0; row < Const.HEIGHT; row++) {
            for (int col = 0; col < Const.WIDTH; col++) {
                Tile tile = this.board[row][col];
                ArrayList<Tile> connectedTiles = new ArrayList<>();
                // first, we add 4 surrounding tiles to the connected tiles
                if (row > 0) {
                    connectedTiles.add(this.board[row - 1][col]);
                }
                if (row < Const.HEIGHT - 1) {
                    connectedTiles.add(this.board[row + 1][col]);
                }
                if (col > 0) {
                    connectedTiles.add(this.board[row][col - 1]);
                }
                if (col < Const.WIDTH - 1) {
                    connectedTiles.add(this.board[row][col + 1]);
                }
                if (flag) {
                    // if the tile is a first type tile, add 4 more surrounding tiles to the connected tiles
                    if (row > 0 && col > 0) {
                        connectedTiles.add(this.board[row - 1][col - 1]);
                    }
                    if (row < Const.HEIGHT - 1 && col < Const.WIDTH - 1) {
                        connectedTiles.add(this.board[row + 1][col + 1]);
                    }
                    if (row > 0 && col < Const.WIDTH - 1) {
                        connectedTiles.add(this.board[row - 1][col + 1]);
                    }
                    if (row < Const.HEIGHT - 1 && col > 0) {
                        connectedTiles.add(this.board[row + 1][col - 1]);
                    }
                }
                this.connectionMap.put(tile, connectedTiles);
                flag = !flag;
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

    public MoveResult processMove(Piece piece, int row, int col) {
        if (this.board[row][col].hasPiece() || piece.getSide() != this.currentPlayer.getSide()) {
            // if the tile at (row, col) already has a piece, return invalid move
            return new MoveResult(false);
        }

        int oldRow = piece.getRow();
        int oldCol = piece.getCol();

        // get the connected tiles of the tile at (oldRow, oldCol)
        ArrayList<Tile> connectedTiles = getConnectedTiles(oldRow, oldCol);
        if (connectedTiles.contains(this.board[row][col])) {
            // if the tile at (row, col) is in the connected tiles, move the piece to the new position
            movePiece(piece, row, col);
            ArrayList<Piece> capturedPieces = new ArrayList<>(getCarriedPieces(piece, getConnectedTiles(row, col)));
            capturedPieces.addAll(getSurroundedPieces());
            if (!capturedPieces.isEmpty()) {
                // if the captured pieces are not empty, return a capture move
                this.currentPlayer.increaseTotalPiece(capturedPieces.size());
                // decrease the number of pieces of the opponent
                if (this.currentPlayer == this.player1) {
                    this.player2.decreaseTotalPiece(capturedPieces.size());
                } else {
                    this.player1.decreaseTotalPiece(capturedPieces.size());
                }
                return new MoveResult(true, capturedPieces);
            }
            return new MoveResult(true);
        }

        return new MoveResult(false);
    }

    private void movePiece(Piece piece, int row, int col) {
        this.board[piece.getRow()][piece.getCol()].removePiece();
        piece.move(row, col);
        this.board[row][col].setPiece(piece);
    }

    private ArrayList<Piece> getCarriedPieces(Piece piece, ArrayList<Tile> connectedTiles) {
        ArrayList<Piece> carriedPieces = new ArrayList<>();
        int tileRow = piece.getRow();
        int tileCol = piece.getCol();

        // loop through all the connected tiles, check each pair of connected tiles
        for (int i = 0; i < connectedTiles.size(); i++) {
            Tile tile1 = connectedTiles.get(i);
            for (int j = i + 1; j < connectedTiles.size(); j++) {
                Tile tile2 = connectedTiles.get(j);

                if (tile1.getRow() + tile2.getRow() != 2 * tileRow || tile1.getCol() + tile2.getCol() != 2 * tileCol) {
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
                if (tile.hasPiece() && !visited[row][col]) {
                    // for each piece that has not been visited, we use flood fill algorithm to find the group of pieces
                    // that form a group, if the group is surrounded, we flip the side of the pieces in the group
                    // and add them to the surrounded pieces
                    ArrayList<Piece> group = new ArrayList<>();
                    if (floodFill(tile.getPiece(), group, visited)) {
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

    private boolean floodFill(Piece piece, ArrayList<Piece> group, boolean[][] visited) {
        // this algorithm is used to find the pieces that form a group then check if the group is surrounded
        int row = piece.getRow();
        int col = piece.getCol();
        visited[row][col] = true;
        group.add(piece);

        boolean isSurrounded = true;
        ArrayList<Tile> connectedTiles = getConnectedTiles(row, col);
        for (Tile tile : connectedTiles) {
            if (!tile.hasPiece()) {
                isSurrounded = false;
            } else if (tile.getPiece().getSide() == piece.getSide() && !visited[tile.getRow()][tile.getCol()]) {
                isSurrounded &= floodFill(tile.getPiece(), group, visited);
            }
        }
        return isSurrounded;
    }

    private void flipGroup(ArrayList<Piece> group) {
        for (Piece piece : group) {
            piece.flipSide();
        }
    }

    private ArrayList<Tile> getConnectedTiles(int row, int col) {
        return this.connectionMap.get(this.board[row][col]);
    }

    public boolean isGameOver() {
        return this.currentPlayer.getTotalPiece() == Const.TOTAL_PIECE;
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
