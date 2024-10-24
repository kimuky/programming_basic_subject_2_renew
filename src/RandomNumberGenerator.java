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

        // TODO: 결과값 출력해주는 코드 (코드가 잘 작동하는지 확인해보기 위함) - 지워줄것
        System.out.println(randomNumberList);

        return randomNumberList;
    }
}
