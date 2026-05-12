package com.blackjack.game.betting;

import com.blackjack.game.players.Player;

public class BettingSystem {

    public void rewardWinner(Player player, int amount) {
        player.addChips(amount);
    }

    public void doubleOrNothing(Player player, boolean win) {

        if (win) {
            player.addChips(player.getChips());
        }
        else {
            player.removeChips(player.getChips());
        }
    }
}
