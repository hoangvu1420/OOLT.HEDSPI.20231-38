package hust.hedspi.coganhgame.Exception;

public class GameNotFoundException extends Exception{
    public GameNotFoundException() {
        super("No saved game found!");
    }
}
