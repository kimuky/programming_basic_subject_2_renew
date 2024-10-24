# 숫자 야구 게임_리뉴얼

## 프로젝트 정보
- 스파르타 내일배움캠프 Spring 7기
- 자바 기초 과제 2
- 개발 기간: 2024.10.23 (20:00 ~ 24:00)
- https://github.com/kimuky/programming_basic_subject_2 -> 처음 만든 코드가 너무 복잡해서 리뉴얼한 프로젝트

## 리뉴얼을 한 이유?
 - 기존 코드가 가독성이 매우 떨어지고 복잡하다고 생각
   - 코드가 읽기 힘듦
   - 로직 자체도 너무 복잡함
   - 절차지향적으로 짜여져 있음

## 리뉴얼을 어떻게 진행?
  - 과제를 최대한 큰 단위로 크게 쪼개기 시작하였음
    - 필수 기능 정리
      - 정답 숫자 생성
      - 정답 입력
      - 스트라이크 볼 아웃 판별
      - 이어하기 While
    - 도전 기능 정리
      - lv 3 게임 기록 확인
      - lv 4 게임 난이도 조절
        
  - 하향식으로 접근하여 큰 단위부터 작은 단위로 접근

## 리뉴얼 자체 평가
 - 가독성 평가
    - 전보다는 더 읽기 편해진 것 같고, 불필요한 클래스, 중복 로직을 제거함으로써 개선된 것 같음
 - 코드의 복잡도
   - 중첩 if, while 문 안에 무분별한 if else 배치 등이 사라져 복잡성이 조금 제거되어진 것 같음
 - 절차지향적
     - while 문 안에서 절차지향적으로 짜여져있었는데 메소드를 정리하여 놓음으로써 객체지향적으로 접근이 된 것 같음

## 프로젝트 소개

- 숫자를 통해 야구를 하는 게임

![image](https://github.com/user-attachments/assets/d7da27fe-a8d5-4f92-bbb1-1059db9b22cb)

  - 0)자리수 설정: 3, 4, 5 자리수 선택 가능
    
    ![image](https://github.com/user-attachments/assets/e744425e-8a7e-4445-8092-001d4ad122e1)

  - 1)게임 시작하기: 생성된 난수를 맞추는 게임
       - 스트라이크: 몇 번째 자리수, 숫자가 동일
       - 볼: 자리수 틀림, 숫자는 동일
         
       ![image](https://github.com/user-attachments/assets/36914624-420e-4e65-b679-27b168a3c082)

  - 2)게임 기록 보기: 시도횟수를 확인 가능

    ![image](https://github.com/user-attachments/assets/a15a9722-7eeb-4810-8537-9ce68a72cfa9)

  - 3)게임 종료

    ![image](https://github.com/user-attachments/assets/211c7072-88fa-4a52-8c79-11eeb1677860)


## 리뉴얼 전, 트러블슈팅

<details>
  <summary><b>1) 스트라이크, 볼 판별 로직 문제</b></summary>
  
  - 1.개요
    
    - 스트라이크 볼 판별에서 Arrays.BinarySearch()를 통해 탐색을 진행

  - 2.문제 상황

    - HashSet을 통해 난수를 저정하고 값 비교를 위해 배열로 변환 (HashSet -> Array)
    - 이후 사용자가 입력한 값을 배열로 변환하고 Arrays.BinarySearch()를 통해 탐색을 진행을 하며 스트라이크 볼 판별
    - but, 난수를 저장한 HashSet->Array는 정렬되지 않음 Arrays.BinarySearch()를 쓰면 정상적으로 이진탐색하지 못함
    - 해당 수가 있음에도 내가 구현한 로직에서는 작동하지 않음 => 스트라이크, 볼 판별 불가
      
      -> 전체적인 로직을 수정해야함

    ```java
    // 난수 생성 클래스
    public class RandomNumberGenerator {
    int[] numberArr = new int[3];
    public int getRandomNumber () {
        Set<Integer> numberSet = new HashSet<>();
        int resultNumber = 0;
        while (numberSet.size()<3) {
            // 1 ~ 9
            int number = (int)(Math.random()*8)+1;
            numberSet.add(number);
        }
        System.out.println(numberSet);
        int pow = 2;
        int index =0;
        for (Integer i : numberSet) {
            int num = (int)Math.pow(10, pow--)*i;
            resultNumber += num;
            numberArr[index++] = i;
        }
        return resultNumber;
    }

    // 스트라이크 볼 카운트 클래스
    public void countStrikeBall(int[] answerArr, int[] validNumberArr) {
        int strikeCounter = 0;
        int ballCounter = 0;
        for (int i = 0; i < answerArr.length; i++) {
            int arrIndex = Arrays.binarySearch(answerArr, validNumberArr[i]);
            if (arrIndex >= 0) {
                if (arrIndex == i) {
                    strikeCounter += 1;
                } else {
                    ballCounter += 1;
                }
            }
        }
    }
    
    ```
   
  - 3.해결

    - 스트라이크, 볼 판별 시에는 자리수, 수가 있는지 판별해야함
    - 그렇기에 인덱스와 그 수가 있는지 탐색을 해야함
    - 배열은 앞서 했던 것처럼 Arrays.BinarySearch()을 지원하지만 <b>정렬이 되어진 배열만</b> 정상적으로 작동
    - set은 iterator가 있지만 해당 인덱스, 수가 있는지 로직을 짜면 복잡해질 것을 우려
    - set -> list 변환 후, Collections.shuffle()을 통해 섞고 indexOf() 통해 인덱스와 그 수가 있는지를 판별

    ```java
    // 난수 생성 클래스
    public int getRandomNumber() {
        Set<Integer> numberSet = new HashSet<>();
        int resultNumber = 0;

        while (numberSet.size() < digit) {
            // 1 ~ 9
            int number = (int) (Math.random() * 9) + 1;
            numberSet.add(number);
        }
        List<Integer> numberList = new ArrayList<>(numberSet);
        Collections.shuffle(numberList);

        System.out.println(numberList);

        int pow = digit - 1;
        int index = 0;
        for (Integer i : numberList) {
            int num = (int) Math.pow(10, pow--) * i;
    ```

  - 4.결론
    - 컬렉션 프레임워크에 대한 개념 부족
    - 메소드에 대한 개념 부족
  
       
</details>

<details>
  <summary><b>2) next(), nextLine() 스캔 문제</b></summary>

  - 1.개요
    - 사용자가 게임을 시작하고 수를 입력할 때, Whitespace와 함께 입력하면 경고가 여러번 출력
  
  - 2.문제 상황
    - next()는 공백을 기준으로 데이터를 입력 받음
    - 스페이스에 따른 입력이 많아짐 -> 그에 따른 로직도 경고가 다중으로 출력 ex.) 4 5 6

    ```java
    // inputNumber()
    public String inputNumber() {
        System.out.println("숫자를 입력해주세요:");
        return sc.next();
    }
    // 게임 로직
     private void gameStart() {
      boolean isCorrect = false;
      int answer = randomNumberGenerator.getRandomNumber();
      while (!isCorrect) {
          String stringNumber = inputRequester.inputNumber();
          if (validator.isValidNumber(stringNumber)) {
              if (validator.isAnswer(randomNumberGenerator, answer)) {
                  gameAnnouncement.printCongratulationMessage();
                  gameRecorder.saveTryCounter(validator.tryCounter);
                  isCorrect = true;
              }
          }
      }
    }
    ```

   
  - 3.해결
    - next() -> nextLine()으로 바꿔 개행문자 기준으로 입력을 받음

  - 4.결론
    - next, nextLine에 대한 개념 정립이 정확히 되지않아 이런 실수가 발생하는 것
    - 그렇기에 다중으로 입력을 원하지 않는 이상 nextLine()을 활용
      
</details>

<details>
  <summary><b>3) 사용자 입력값에 중복 숫자, 문자열 입력 시, 오류 출력 문제</b></summary>

  - 1.개요
    - 테스트케이스를 넣어보던 도중, "4456"을 입력 시, 오류가 뜨지 않음
      
  - 2.문제 상황
    - 로직의 처리가 아주 느슨함
    - 로직의 순서가 이상

    ```java
    private boolean isDuplicateNumber(String stringNumber) {
      Set<String> numberSet = new HashSet<>(Arrays.asList(stringNumber.split("")));
  
      if (stringNumber.length() == 3) {          
            return numberSet.size() == 3;
      } else {
            return false;
      }
    }
    ```
    
  - 3.해결
    - 로직을 생각해서 순서를 교체
    - 이렇게 된다면 4456 입력 시에도 오류를 출력해줄 수 있을 것
   
    ```java
    private boolean isDuplicateNumber(String stringNumber) {
     
      if (stringNumber.length() == 3) {
         Set<String> numberSet = new HashSet<>(Arrays.asList(stringNumber.split("")));          
            return numberSet.size() == 3;
      } else {
            return false;
      }
    }
    ```
    
  - 4.결론
    - 문제가 발생될만한 상황에 적절한 오류메시지를 출력해주는 것도 좋지만 먼저 큰 범위부터 좁혀나가면서 출력해줄 것
    
</details>

## 리뉴얼 후, 트러블슈팅

<details>
  <summary><b>1) Enum 클래스 추가한 후, 리턴값에 대한 문제</b></summary>

  - 1.개요
    - 사용자 입력값을 받아서 enum 클래스에서 처리
      
  - 2.문제 상황
    - return을 해주어야하는데 GameOption으로 리턴을 해주어야함
    - 그러면 enum에 있는 상수값 아니면 null을 반환해주어야함

    ```java
    // GameOption
    public enum GameOption {
      SET_DIFFICULTY("0"),
      START_GAME("1"),
      SHOW_RECORD ("2"),
      EXIT("3");
  
      private final String option;
  
      GameOption(String option) {
          this.option = option;
      }
  
      public String getOption() {
          return option;
      }
  
      // 여러가지 방법이 있겠지만 현로직상에는 문제 없지만
      //TODO 리턴에 대해서는 고민해보아야할 것
      public static GameOption option (String option) {
          for (GameOption game : GameOption.values()) {
              if (game.getOption().equals(option)) {
                  return game;
              }
          }
          return null;
      }
    }

    ```

    ```java
    // main
    while (isRunning) {
            System.out.println("0. 게임 난이도 설정 1. 게임 시작하기 2. 게임 기록보기 3. 종료하기");
            String option = sc.nextLine();

            // option 을 enum 을 통해 관리
            GameOption gameOption = GameOption.option(option);

            switch (gameOption) {
                case SET_DIFFICULTY:
                    gameSetting.setDifficulty();
                    break;
                case START_GAME:
                    int result = gameStart(gameSetting, randomNumberGenerator); // 게임결과를 result 에 저장
                    tryCount.add(result); // 해당 결과를 콜렉션에 저장
                    break;
                case SHOW_RECORD:
                    showGameRecord(tryCount, gameSetting.getMAX_TRY());
                    break;
                case EXIT:
                    isRunning = false;
                case ERROR: // 0, 1 ,2 , 3 중 입력하지않으면 error
                    System.out.println("0, 1, 2, 3 중에 입력해주세요");
            }
        }
    ```
    
    
  - 3.해결
    - 사용자 값을 먼저 문제가 없는 값을 입력할 때까지 무한루프로 입력을 받고 처리한다.
      - 하지만 이렇게 처리할 시, Switch 문을 하나 더 만들어야하는데.... (더이상 생각이 나지 않음)
    - 그러면 에러 상수를 하나 더 만들어서 err를 반환해줘서 밑에 switch 문에 넘겨주자!?
      - 당장의 내가 한 로직에서는 문제가 안되지만 정말 찝찝한 처리이다..
   
    ```java
    public enum GameOption {
      SET_DIFFICULTY("0"),
      START_GAME("1"),
      SHOW_RECORD ("2"),
      EXIT("3"),
      ERROR("4");
  
      private final String option;
  
      GameOption(String option) {
          this.option = option;
      }
  
      public String getOption() {
          return option;
      }
  
      // 여러가지 방법이 있겠지만 현로직상에는 문제 없지만
      //TODO 리턴에 대해서는 고민해보아야할 것
      public static GameOption option (String option) {
          for (GameOption game : GameOption.values()) {
              if (game.getOption().equals(option)) {
                  return game;
              }
          }
          return ERROR;
      }
    }

    ```
    
  - 4.결론
    - 모르겠다..? 도저히 Switch 문 하나로 처리할려면 이 방법 말고는 없어보이는데... 일단 구현은 된다.... 
    
</details>


<details>
  <summary><b>2) 힌트 로직 처리의 문제 </b></summary>

  - 1.개요
    - 사용자가 숫자를 입력해 랜덤 숫자를 맞출 때
      - 힌트는 언제 줄 것이며?
      - 조건문은 어떻게 미끄럽게 처리할 것인지?
      
  - 2.문제 상황
    - 힌트는 언제 줄것?
      - 3, 4, 5 자릿수라 힌트를 게임 끝나는 조건 전으로 지급을 해야하는데
      - MAX_TRY를 30으로 고정 하고 계산했을 때 (문제 발생)
      - 3자리수가 힌트 제공은 시도횟수가 (10, 20, 30) 때 제공
      - 4자리수가 힌트 제공은 시도횟수가 (6, 12, 18, 24) 때 제공 - 정답 노출
      - 5자리수가 힌트 제공은 시도횟수가 (6, 12, 18, 24 ,30) 때 제공
        
      ```java
      if (tryCount % (MAX_TRY / difficulty) == 0) {
          showHint(answerList, tryCount / (MAX_TRY / difficulty));
      }
      ```

  - 3.해결
    - 30으로 하는 것 보다는 최소공배수(60) 를 통해 계산
    - 물론 자릿수를 지정하는게 늘어나면 각 자릿수에 대해서 열거형 만든다음
    - 최소공배수 구하는 로직까지 구현해야하지만 문제 조건은 3,4,5이기에 60으로 지정했습니다.
    - MAX_TRY를 60으로 고정 하고 계산했을 때 (문제 해결)
      - 3자리수가 힌트 제공은 시도횟수가 (20, 40, 60) 때 제공
      - 4자리수가 힌트 제공은 시도횟수가 (15, 30, 45, 60) 때 제공
      - 5자리수가 힌트 제공은 시도횟수가 (12, 24, 36, 48 , 60) 때 제공
      
      ```java
      // 해당 로직을 수정
      if (tryCount % (MAX_TRY / difficulty) == 0 && tryCount < MAX_TRY) {
         showHint(answerList, tryCount / (MAX_TRY / difficulty));
      }
      ```

      ```java
      // 전체 로직
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

             int[] strikeAndBallArr = countStrikeAndBall(userInputNumber, answerList);

             printStrikeAndBall(strikeAndBallArr);

             // TODO: 로직이 어색.. 오류는 나지 않지만 추가로 고민해볼것
             // 현 상황에서는 버그가 나지 않지만 최소공배수 기준으로 나눠서 보여주는 방법도..
             if (tryCount % (MAX_TRY / difficulty) == 0 && tryCount < MAX_TRY) {
                 showHint(answerList, tryCount / (MAX_TRY / difficulty));
             }

             // 종료 로직, 정답 이거나 종료횟수까지 맞추지 못하면 시도횟수를 반환
             if (isAnswer(userInputNumber, answerList) || tryCount == MAX_TRY) {
                 return tryCount;
             }
         }
      }
      ```

  - 4.결론
    - 저렇게 처리하는게 올바른 로직인지는 모르겠지만 아직 이상한 부분이 많기에 고민해보고 있습니다.
    - 전체 로직도 더 다듬어야 할 부분이 보입니다..


    
</details>


