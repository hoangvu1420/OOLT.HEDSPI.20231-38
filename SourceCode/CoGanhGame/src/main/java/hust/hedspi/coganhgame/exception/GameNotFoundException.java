package hust.hedspi.coganhgame.exception;

public class GameNotFoundException extends Exception{
    public GameNotFoundException() {
        super("No saved game found!");
    }
}
