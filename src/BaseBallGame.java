import java.util.ArrayList;
import java.util.Scanner;

//test
public class BaseBallGame {
    Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        BaseBallGame baseBallGame = new BaseBallGame();
        baseBallGame.play(new GameSetting(), new RandomNumberGenerator());
    }

    public void play(GameSetting gameSetting, RandomNumberGenerator randomNumberGenerator) {
        boolean isRunning = true; // 메인을 돌아가게 하는 상태
        ArrayList<Integer> tryCount = new ArrayList<>(); //시도횟수를 저장하는 콜렉션

        while (isRunning) {
            System.out.println("0. 게임 난이도 설정 1. 게임 시작하기 2. 게임 기록보기 3. 종료하기");
            String option = sc.nextLine();

            // option 을 enum 을 통해 관리
            GameOption gameOption = GameOption.option(option);

            switch (gameOption) {
                case SET_DIFFICULTY: //난이도 조절
                    gameSetting.setDifficulty();
                    break;
                case START_GAME: // 게임시작
                    int result = gameStart(gameSetting, randomNumberGenerator); // 게임결과를 result 에 저장
                    tryCount.add(result); // 해당 결과를 콜렉션에 저장
                    break;
                case SHOW_RECORD: // 게임기록 보기
                    showGameRecord(tryCount, gameSetting.getMAX_TRY());
                    break;
                case EXIT: // 무한루프 종료
                    isRunning = false;
                case ERROR: // 0, 1 ,2 , 3 중 입력하지않으면 error
                    System.out.println("0, 1, 2, 3 중에 입력해주세요");
            }
        }
    }

    private int gameStart(GameSetting gameSetting, RandomNumberGenerator randomNumberGenerator) {
        // 난이도와 종료 횟수 지정
        int difficulty = gameSetting.getDifficulty();
        int MAX_TRY = gameSetting.getMAX_TRY();
        int tryCount = 0;

        // 난수를 answerList 에 저장
        ArrayList<Integer> answerList = randomNumberGenerator.generateNumber(difficulty);

        // 맞출때까지 무한루프
        while (true) {
            // 유저 입력
            int userInputNumber = inputAnswer(difficulty);

            // 시도횟수 증가
            tryCount++;

            // 스트라이크 볼 카운트
            int[] strikeAndBallArr = countStrikeAndBall(userInputNumber, answerList);

            // 스트라이크 볼 출력
            printStrikeAndBall(strikeAndBallArr);

            /* 힌트 출력
             * 3자리수는 10, 20, 30 마다 출력
             * 4자리수는 7, 14 ,21, 28 마다 출력
             * 5자리수는 6, 12 ,18, 24, 60 마다 출력
             * MAXTRY 는 파이널 상수 30
             */
            if (tryCount % (MAX_TRY / difficulty) == 0) {
                showHint(answerList, tryCount / (MAX_TRY / difficulty));
            }

            // 종료 로직, 정답 이거나 종료횟수까지 맞추지 못하면 시도횟수를 반환
            if (isAnswer(userInputNumber, answerList) || tryCount == MAX_TRY) {
                return tryCount;
            }
        }
    }

    // 유저가 문제없는 값을 입력할 때까지 무한 루프
    private int inputAnswer(int difficulty) {
        while (true) {
            System.out.println("숫자를 입력해주세요");
            String number = sc.nextLine();

            if (number.length() == difficulty) {
                try {
                    return Integer.parseInt(number);
                } catch (NumberFormatException e) {
                    System.out.println("숫자만 입력해주세요!");
                }
            } else {
                System.out.println("다시 입력해주세요");
            }
        }
    }

    // 정답판별
    private boolean isAnswer(int userInputNumber, ArrayList<Integer> answerList) {
        int answerNumber = listNumberToIntNumber(answerList);
        return answerNumber == userInputNumber;
    }

    // 난수로 생성된 배열을 인트형으로 변환하여 반환
    private int listNumberToIntNumber(ArrayList<Integer> randomNumberList) {
        int powNum = randomNumberList.size() - 1;
        int intNumber = 0;

        for (Integer integer : randomNumberList) {
            intNumber += integer * (int) Math.pow(10, (powNum--));
        }
        return intNumber;
    }

    // 스트라이크와 볼을 세어주는 로직
    private int[] countStrikeAndBall(int userInputNumber, ArrayList<Integer> answerList) {
        int[] userInputNumberArr = new int[answerList.size()];
        int index = userInputNumberArr.length - 1;

        // strikeAndBallArr[0] = 스트라이크
        // strikeAndBallArr[1] = 볼
        int[] strikeAndBallArr = new int[2];

        // 사용자가 입력한 숫자배열을 int로 변환
        for (int i = index; i >= 0; i--) {
            int num = userInputNumber % 10;
            userInputNumberArr[i] = num;
            userInputNumber /= 10;
        }

        // 스트라이크 볼 판별
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

    // 스트라이크 볼 출력
    private void printStrikeAndBall(int[] strikeAndBallArr) {
        StringBuilder tempStr = new StringBuilder();
        int strike = strikeAndBallArr[0];
        int ball = strikeAndBallArr[1];

        // 한줄로 출력해주기 위해 이런 로직
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

    // 힌트 출력
    private void showHint(ArrayList<Integer> answerList, int digit) {
        for (int i = 0; i < digit; i++) {
            System.out.printf("%d 번째 자리는 %d 입니다.\n", i + 1, answerList.get(i));
        }
    }

    /* 게임기록 보기
     * 최대시도횟수라면 실패
     * 아니라면 시도횟수를 보여줌
     */
    private void showGameRecord(ArrayList<Integer> tryCount, int MAX_TRY) {
        if (!tryCount.isEmpty()) {
            int index = 1;
            for (Integer i : tryCount) {
                if (i == MAX_TRY) {
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
