package betting;

import players.Player;

public class ChipManager {

    public static final int MIN_BET = 10;
    public static final int MAX_BET = 10000;

    public boolean placeBet(Player player, int amount) {

        if (amount < MIN_BET || amount > MAX_BET) {
            return false;
        }

        if (player.getChips() < amount) {
            return false;
        }

        player.removeChips(amount);
        player.setCurrentBet(amount);

        return true;
    }
}
