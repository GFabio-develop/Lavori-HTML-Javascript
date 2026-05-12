package com.blackjack.game.ai;

import com.blackjack.game.players.BotPlayer;
import com.blackjack.game.players.Personality;

public class BettingAI {

    public int calculateBet(BotPlayer botPlayer) {

        int chips = botPlayer.getChips();
        Personality personality = botPlayer.getPersonality();
        int count = botPlayer.getCounter().getRunningCount();

        int bet;

        switch (personality) {

            case SAFE:
                bet = 50;
                break;

            case AGGRESSIVE:
                bet = 200;
                break;

            case MATHEMATICIAN:
                bet = count > 3 ? 300 : 100;
                break;

            case CHAOTIC:
                bet = 150;
                break;

            case BLUFFER:
                bet = 250;
                break;

            default:
                bet = 100;
                break;
        }

        if (bet > chips) {
            bet = chips;
        }

        if (bet < 10 && chips >= 10) {
            bet = 10;
        }

        return bet;
    }
}
