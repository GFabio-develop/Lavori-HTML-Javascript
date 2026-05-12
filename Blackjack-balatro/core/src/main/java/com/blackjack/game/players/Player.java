package com.blackjack.game.players;

import com.blackjack.game.cards.Card;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {

    protected String name;
    protected List<Card> hand;
    protected int chips;
    protected int currentBet;
    protected boolean standing;

    public Player(String name) {

        this.name = name;
        this.hand = new ArrayList<>();
        this.chips = 1000;
        this.currentBet = 0;
        this.standing = false;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public List<Card> getHand() {
        return hand;
    }

    public int calculateHandValue() {

        int total = 0;
        int aces = 0;

        for (Card card : hand) {

            total += card.getValue();

            if (card.getRank().name().equals("ACE")) {
                aces++;
            }
        }

        while (total > 21 && aces > 0) {
            total -= 10;
            aces--;
        }

        return total;
    }

    public boolean isBust() {
        return calculateHandValue() > 21;
    }

    public void clearHand() {
        hand.clear();
        standing = false;
        currentBet = 0;
    }

    public int getChips() {
        return chips;
    }

    public void addChips(int amount) {
        chips += amount;
    }

    public void removeChips(int amount) {
        chips -= amount;
    }

    public String getName() {
        return name;
    }

    public int getCurrentBet() {
        return currentBet;
    }

    public void setCurrentBet(int currentBet) {
        this.currentBet = currentBet;
    }

    public boolean isStanding() {
        return standing;
    }

    public void setStanding(boolean standing) {
        this.standing = standing;
    }
}
