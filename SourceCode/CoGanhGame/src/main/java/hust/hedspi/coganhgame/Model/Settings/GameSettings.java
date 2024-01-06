package hust.hedspi.coganhgame.Model.Settings;

public class GameSettings {
    private String player1Name;
    private String player2Name;
    private int gameTime;
    private int botLevel;

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }
    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }
    public void setGameTime(int gameTime){
        this.gameTime = gameTime;
    }
    public void setBotLevel(int botLevel){
        this.botLevel = botLevel;
    }
    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public int getGameTime() {
        return gameTime;
    }
    public int getBotLevel(){
        return botLevel;
    }
}
