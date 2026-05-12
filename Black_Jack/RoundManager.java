package game;

import cards.Deck;
import players.Player;

import java.util.List;

public class RoundManager {

    private final Deck deck;

    public RoundManager() {
        deck = new Deck();
    }

    public void dealInitialCards(List<Player> players) {

        for (int i = 0; i < 2; i++) {

            for (Player player : players) {
                player.addCard(deck.drawCard());
            }
        }
    }

    public Deck getDeck() {
        return deck;
    }
}