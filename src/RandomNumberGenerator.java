import java.util.ArrayList;

public class RandomNumberGenerator {
    public ArrayList<Integer> generateNumber(int difficulty) {
        ArrayList<Integer> randomNumberList = new ArrayList<>();

        while (randomNumberList.size() < difficulty) {
            int randomNumber = (int) (Math.random() * 9 + 1);
            if (!randomNumberList.contains(randomNumber)) {
                randomNumberList.add(randomNumber);
            }
        }

        // 단순 테스트 용도
        // print 해주는 것은 맞지 않음!
        System.out.println(randomNumberList);

        return randomNumberList;
    }
}
