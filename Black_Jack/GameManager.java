package game;

import players.*;

import java.util.*;

public class GameManager {

    private final List<Player> players;
    private final Dealer dealer;

    public GameManager() {

        players = new ArrayList<>();
        dealer = new Dealer();
    }

    public void startGame() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== BLACKJACK BALATRO ===");

        System.out.print("Insert your name: ");
        String name = scanner.nextLine();

        players.add(new HumanPlayer(name));

        System.out.print("How many bots? (1-4): ");
        int botCount = scanner.nextInt();

        botCount = Math.max(1, Math.min(botCount, 4));

        Personality[] personalities = Personality.values();

        for (int i = 0; i < botCount; i++) {

            Personality personality =
                    personalities[i % personalities.length];

            players.add(new BotPlayer(
                    "Bot_" + (i + 1),
                    personality
            ));
        }

        gameLoop();
    }

    private void gameLoop() {

        RoundManager roundManager = new RoundManager();

        roundManager.dealInitialCards(players);

        for (Player player : players) {

            System.out.println();
            System.out.println(player.getName());
            System.out.println(player.getHand());
            System.out.println("Value: " + player.calculateHandValue());
        }
    }
}