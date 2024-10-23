import java.util.ArrayList;
import java.util.Scanner;

public class BaseBallGame {
    Scanner sc = new Scanner(System.in);

    public void play() {
        boolean isRunning = true;
        int difficulty = 3;
        ArrayList<Integer> tryCount = new ArrayList<>();

        while (isRunning) {
            System.out.println("0. 게임 난이도 설정 1. 게임 시작하기 2. 게임 기록보기 3. 종료하기");
            String option = sc.nextLine();
            switch (option) {
                case "0":
                    difficulty = controlDifficulty(difficulty);
                    break;
                case "1":
                    int result = gameStart(difficulty);
                    tryCount.add(result);
                    break;
                case "2":
                    showGameRecord(tryCount);
                    break;
                case "3":
                    isRunning = false;
                default:
                    System.out.println("0, 1, 2, 3 중에 입력해주세요");
            }
        }
    }

    private int controlDifficulty(int currentDifficulty) {
        System.out.println("난이도를 설정해주세요 3,4,5");
        String difficulty = sc.nextLine();
        return switch (difficulty) {
            case "3" -> 3;
            case "4" -> 4;
            case "5" -> 5;
            default -> {
                System.out.printf("잘못 입력하셨습니다. 기존 난이도 %d으로 설정하겠습니다.\n", currentDifficulty);
                yield currentDifficulty;
            }
        };
    }

    private int gameStart(int difficulty) {
        ArrayList<Integer> answerList = generateNumber(difficulty);
        int tryCount = 0;
        int maxTry = 30;

        while (true) {
            int userInputNumber = inputAnswer(difficulty);

            if (userInputNumber == 0) {
                System.out.println("다시 입력해주세요!");
                continue;
            }

            tryCount++;
            if (isAnswer(userInputNumber, answerList)) {
                System.out.println("정답");
                return tryCount;
            } else if (tryCount == maxTry) {
                System.out.println("야구게임 실패하셨습니다.");
                return -1;
            } else {
                int[] strikeAndBallArr = countStrikeAndBall(userInputNumber, answerList);
                printStrikeAndBall(strikeAndBallArr);

                if (tryCount % (maxTry / difficulty) == 0) {
                    showHint(answerList, tryCount / (maxTry / difficulty));
                }
            }
        }
    }

    private ArrayList<Integer> generateNumber(int difficulty) {
        ArrayList<Integer> randomNumberList = new ArrayList<>();

        while (randomNumberList.size() < difficulty) {
            int randomNumber = (int) (Math.random() * 9 + 1);
            if (!randomNumberList.contains(randomNumber)) {
                randomNumberList.add(randomNumber);
            }
        }
        System.out.println(randomNumberList);
        return randomNumberList;
    }

    private int inputAnswer(int difficulty) {
        System.out.println("숫자를 입력해주세요");
        String number = sc.nextLine();

        if (number.length() > difficulty) {
            return 0;
        } else {
            try {
                return Integer.parseInt(number);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
    }

    private boolean isAnswer(int userInputNumber, ArrayList<Integer> answerList) {
        int answerNumber = listNumberToIntNumber(answerList);
        return answerNumber == userInputNumber;
    }

    private int listNumberToIntNumber(ArrayList<Integer> randomNumberList) {
        int powNum = randomNumberList.size() - 1;
        int intNumber = 0;

        for (Integer integer : randomNumberList) {
            intNumber += integer * (int) Math.pow(10, (powNum--));
        }
        return intNumber;
    }

    private int[] countStrikeAndBall(int userInputNumber, ArrayList<Integer> answerList) {
        int[] userInputNumberArr = new int[answerList.size()];
        int index = userInputNumberArr.length - 1;
        int[] strikeAndBallArr = new int[2];

        for (int i = index; i >= 0; i--) {
            int num = userInputNumber % 10;
            userInputNumberArr[i] = num;
            userInputNumber /= 10;
        }

        for (int i = 0; i < answerList.size(); i++) {
            if (answerList.contains(userInputNumberArr[i])) {
                if (userInputNumberArr[i] == answerList.get(i)) {
                    strikeAndBallArr[0] += 1;
                } else {
                    strikeAndBallArr[1] += 1;
                }
            }
        }
        return strikeAndBallArr;
    }

    private void printStrikeAndBall(int[] strikeAndBallArr) {
        StringBuilder tempStr = new StringBuilder();
        int strike = strikeAndBallArr[0];
        int ball = strikeAndBallArr[1];

        if (strike > 0) {
            tempStr.append(strike).append(" 스트라이크");
        }
        if (ball > 0) {
            tempStr.append(ball).append(" 볼");
        }
        if (strike == 0 && ball == 0) {
            tempStr.append("아웃");
        }
        System.out.println(tempStr);
    }

    private void showHint(ArrayList<Integer> answerList, int digit) {
        for (int i = 0; i < digit; i++) {
            System.out.printf("%d 번째 자리는 %d 입니다.\n", i + 1, answerList.get(i));
        }
    }

    private void showGameRecord(ArrayList<Integer> tryCount) {
        if (!tryCount.isEmpty()) {
            int index = 1;
            for (Integer i : tryCount) {
                if (i == -1) {
                    System.out.printf("%d 번째 게임은 실패\n", index);
                } else {
                    System.out.printf("%d 번째 게임 시도 횟수 : %d\n", index, i);
                }
                index++;
            }
        } else {
            System.out.println("아직 게임을 플레이 한 기록이 없습니다.");
        }
    }
}
