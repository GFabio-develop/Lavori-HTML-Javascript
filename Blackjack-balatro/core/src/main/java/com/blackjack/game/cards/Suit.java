package com.blackjack.game.cards;

public enum Suit {

    SPADES(1),
    CLUBS(2),
    DIAMONDS(3),
    HEARTS(4);

    private final int priority;

    Suit(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
