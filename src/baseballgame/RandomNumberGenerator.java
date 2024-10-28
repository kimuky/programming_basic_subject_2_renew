package baseballgame;

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
        return randomNumberList;
    }
}
