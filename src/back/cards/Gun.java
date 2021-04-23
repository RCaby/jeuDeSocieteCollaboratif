package back.cards;

import java.util.ResourceBundle;

import back.Board;
import back.Player;
import back.PlayerState;

public class Gun extends Card {
    private static final long serialVersionUID = 8554178826025991327L;

    public Gun(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        isSingleUse = false;
    }

    public String toString() {
        return stringsBundle.getString("Gun_name");
    }

    // PLayer x2

    // not discarded after death
    // permanent

    @Override
    public void useCard(Player player1, Player player2, Player player3, String string) {
        if (player1 != null && player2 != null) {
            Card card = player1.getCartridge();
            if (card != null) {
                card.useCard(player1, player2, player3, string);
                Card metalSheet = player2.getMetalSheet();
                if (metalSheet != null) {
                    metalSheet.useCard(player1, player2, player3, string);
                } else {
                    board.setDeadThisRound(board.getDeadThisRound() + 1);
                    player2.setState(PlayerState.DEAD);
                }
            }
        }
    }

    @Override
    public boolean canBeUsed() {
        return board.getNbPlayersAlive() > 1 && owner != null && owner.getCartridge() != null;
    }
}
