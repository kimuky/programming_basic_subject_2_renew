package baseballgame;

public class Main {
    public static void main(String[] args) {
        BaseBallGame baseBallGame = new BaseBallGame();
        baseBallGame.play(new GameSetting(), new RandomNumberGenerator());
    }
}
