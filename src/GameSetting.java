import java.util.Scanner;

public class GameSetting {
    private int difficulty;
    private final int MAX_TRY;

    public GameSetting () {
        this.difficulty = 3;
        this.MAX_TRY = 60;
    }

    // getter setter 는 좋지 않은 구조 고민해볼것...
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
        return switch (difficulty) {
            case "3" -> 3;
            case "4" -> 4;
            case "5" -> 5;
            default -> {
                System.out.printf("잘못 입력하셨습니다. 기존 난이도 %d으로 설정하겠습니다.\n", this.difficulty);
                yield this.difficulty;
            }
        };
    }
}
