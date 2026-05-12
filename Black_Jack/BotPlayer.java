package players;

import ai.BotDecision;
import ai.CardCounter;

import java.util.Random;

public class BotPlayer extends Player {

    private final Personality personality;
    private final CardCounter counter;

    public BotPlayer(String name, Personality personality) {

        super(name);

        this.personality = personality;
        this.counter = new CardCounter();
    }

    public BotDecision makeDecision() {

        int handValue = calculateHandValue();

        switch (personality) {

            case SAFE:
                return handValue < 15
                        ? BotDecision.HIT
                        : BotDecision.STAND;

            case AGGRESSIVE:

                if (handValue <= 17) {
                    return BotDecision.HIT;
                }

                return BotDecision.ALL_IN;

            case MATHEMATICIAN:

                if (counter.getRunningCount() > 4) {
                    return BotDecision.DOUBLE;
                }

                return handValue < 16
                        ? BotDecision.HIT
                        : BotDecision.STAND;

            case CHAOTIC:

                Random random = new Random();

                return BotDecision.values()[
                        random.nextInt(BotDecision.values().length)
                ];

            default:
                return BotDecision.STAND;
        }
    }

    public Personality getPersonality() {
        return personality;
    }

    public CardCounter getCounter() {
        return counter;
    }
}