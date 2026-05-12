package game;

import cards.Card;
import cards.Deck;
import players.BotPlayer;
import players.Dealer;
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

                Card card = deck.drawCard();

                player.addCard(card);

                notifyBotCounters(players, card);
            }
        }
    }

    public void dealInitialCards(List<Player> players, Dealer dealer) {

        for (int i = 0; i < 2; i++) {

            for (Player player : players) {

                Card card = deck.drawCard();

                player.addCard(card);

                notifyBotCounters(players, card);
            }

            Card dealerCard = deck.drawCard();

            dealer.addCard(dealerCard);

            notifyBotCounters(players, dealerCard);
        }
    }

    private void notifyBotCounters(List<Player> players, Card card) {

        for (Player player : players) {

            if (player instanceof BotPlayer) {

                BotPlayer botPlayer = (BotPlayer) player;

                botPlayer.getCounter().observe(card);
            }
        }
    }

    public Deck getDeck() {
        return deck;
    }
}
