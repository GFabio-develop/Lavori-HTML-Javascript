package com.blackjack.game.cards;

import java.util.*;

public class Deck {

    private final List<Card> cards;

    public Deck() {

        cards = new ArrayList<>();

        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }

        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {

        if (cards.isEmpty()) {
            throw new RuntimeException("Deck is empty");
        }

        return cards.remove(0);
    }

    public int remainingCards() {
        return cards.size();
    }
}
