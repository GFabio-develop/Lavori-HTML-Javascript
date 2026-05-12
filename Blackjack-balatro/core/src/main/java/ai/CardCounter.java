package ai;

import cards.Card;

public class CardCounter {

    private int runningCount;

    public void observe(Card card) {

        int value = card.getValue();

        if (value >= 2 && value <= 6) {
            runningCount++;
        }
        else if (value == 10 || value == 11) {
            runningCount--;
        }
    }

    public int getRunningCount() {
        return runningCount;
    }
}