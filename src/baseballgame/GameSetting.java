package baseballgame;

import java.util.Scanner;

public class GameSetting {
    private int difficulty;
    private final int MAX_TRY;

    public GameSetting () {
        this.difficulty = 3;
        this.MAX_TRY = 60;
    }

    //TODO : getter setter 는 좋지 않은 구조 고민해볼것...
    public void setDifficulty() {
        this.difficulty = controlDifficulty();
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getMAX_TRY() {
        return MAX_TRY;
    }

    public int controlDifficulty() {
        Scanner sc = new Scanner(System.in);

        System.out.println("난이도를 설정해주세요 3,4,5");
        String difficulty = sc.nextLine();

        try {
            return Integer.parseInt(difficulty);
        } catch (NumberFormatException e) {
            return this.difficulty;
        }
    }
}
