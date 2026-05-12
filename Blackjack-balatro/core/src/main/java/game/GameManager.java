package game;

import ai.BettingAI;
import ai.BotDecision;
import betting.ChipManager;
import cards.Card;
import players.*;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private final List<Player> players;
    private final Dealer dealer;
    private final ChipManager chipManager;
    private final BettingAI bettingAI;

    private RoundManager roundManager;
    private GameState gameState;

    private int playerBet;

    public GameManager() {
        players = new ArrayList<>();
        dealer = new Dealer();
        chipManager = new ChipManager();
        bettingAI = new BettingAI();

        playerBet = 50;
        gameState = GameState.MENU;
    }

    public void startDemoGame() {
        players.clear();
        dealer.clearHand();

        HumanPlayer humanPlayer = new HumanPlayer("Player");
        BotPlayer bot1 = new BotPlayer("Bot_1", Personality.SAFE);
        BotPlayer bot2 = new BotPlayer("Bot_2", Personality.AGGRESSIVE);

        players.add(humanPlayer);
        players.add(bot1);
        players.add(bot2);

        startNewRound();
    }

    public void startNewRound() {
        dealer.clearHand();

        for (Player player : players) {
            player.clearHand();
        }

        placePlayerBet();
        placeBotBets();

        roundManager = new RoundManager();
        roundManager.dealInitialCards(players, dealer);

        gameState = GameState.PLAYER_TURN;
    }

    private void placePlayerBet() {
        if (players.isEmpty()) {
            return;
        }

        Player humanPlayer = players.get(0);

        if (!chipManager.placeBet(humanPlayer, playerBet)) {
            chipManager.placeBet(humanPlayer, ChipManager.MIN_BET);
            playerBet = ChipManager.MIN_BET;
        }
    }

    private void placeBotBets() {
        for (Player player : players) {

            if (player instanceof BotPlayer) {
                BotPlayer botPlayer = (BotPlayer) player;

                int bet = bettingAI.calculateBet(botPlayer);

                chipManager.placeBet(botPlayer, bet);
            }
        }
    }

    public void increasePlayerBet() {
        if (players.isEmpty()) {
            return;
        }

        Player humanPlayer = players.get(0);

        if (gameState != GameState.PLAYER_TURN) {
            return;
        }

        int newBet = humanPlayer.getCurrentBet() + 10;

        if (newBet > ChipManager.MAX_BET) {
            return;
        }

        if (humanPlayer.getChips() < 10) {
            return;
        }

        humanPlayer.removeChips(10);
        humanPlayer.setCurrentBet(newBet);

        playerBet = newBet;
    }

    public void decreasePlayerBet() {
        if (players.isEmpty()) {
            return;
        }

        Player humanPlayer = players.get(0);

        if (gameState != GameState.PLAYER_TURN) {
            return;
        }

        int currentBet = humanPlayer.getCurrentBet();

        if (currentBet <= ChipManager.MIN_BET) {
            return;
        }

        int newBet = currentBet - 10;

        humanPlayer.addChips(10);
        humanPlayer.setCurrentBet(newBet);

        playerBet = newBet;
    }

    public int getPlayerBet() {
        return playerBet;
    }

    public void playerHit() {
        if (gameState != GameState.PLAYER_TURN) {
            return;
        }

        if (players.isEmpty()) {
            return;
        }

        Player humanPlayer = players.get(0);

        if (humanPlayer.isStanding()) {
            return;
        }

        Card card = roundManager.getDeck().drawCard();
        humanPlayer.addCard(card);

        if (humanPlayer.isBust()) {
            humanPlayer.setStanding(true);
            startBotTurns();
            startDealerTurn();
        }
    }

    public void playerStand() {
        if (gameState != GameState.PLAYER_TURN) {
            return;
        }

        if (players.isEmpty()) {
            return;
        }

        Player humanPlayer = players.get(0);
        humanPlayer.setStanding(true);

        startBotTurns();
        startDealerTurn();
    }

    private void startBotTurns() {
        for (Player player : players) {

            if (player instanceof BotPlayer) {

                BotPlayer botPlayer = (BotPlayer) player;

                while (!botPlayer.isStanding() && !botPlayer.isBust()) {

                    BotDecision decision = botPlayer.makeDecision();

                    if (decision == BotDecision.HIT) {
                        Card card = roundManager.getDeck().drawCard();
                        botPlayer.addCard(card);
                    }
                    else if (decision == BotDecision.DOUBLE) {
                        Card card = roundManager.getDeck().drawCard();
                        botPlayer.addCard(card);
                        botPlayer.setStanding(true);
                    }
                    else {
                        botPlayer.setStanding(true);
                    }
                }
            }
        }
    }

    private void startDealerTurn() {
        gameState = GameState.DEALER_TURN;

        while (dealer.shouldDraw()) {
            Card card = roundManager.getDeck().drawCard();
            dealer.addCard(card);
        }

        gameState = GameState.RESULTS;

        applyResultsToChips();
    }

    private void applyResultsToChips() {
        for (Player player : players) {

            String result = getResultFor(player);
            int bet = player.getCurrentBet();

            if (result.equals("WIN")) {
                player.addChips(bet * 2);
            }
            else if (result.equals("PUSH")) {
                player.addChips(bet);
            }
        }
    }

    public String getResultFor(Player player) {
        if (gameState != GameState.RESULTS) {
            return "";
        }

        int playerValue = player.calculateHandValue();
        int dealerValue = dealer.calculateHandValue();

        if (playerValue > 21) {
            return "BUST";
        }

        if (dealerValue > 21) {
            return "WIN";
        }

        if (playerValue > dealerValue) {
            return "WIN";
        }

        if (playerValue < dealerValue) {
            return "LOSE";
        }

        return "PUSH";
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public GameState getGameState() {
        return gameState;
    }
}
