package com.blackjack.game.players;

public class Dealer extends Player {

    public Dealer() {
        super("Dealer");
    }

    public boolean shouldDraw() {
        return calculateHandValue() < 17;
    }
}
