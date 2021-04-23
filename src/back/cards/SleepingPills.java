package back.cards;

import java.util.ResourceBundle;

import back.Board;
import back.Player;

public class SleepingPills extends Card {
    private static final long serialVersionUID = -722036692167231970L;

    public SleepingPills(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
    }

    public String toString() {
        return stringsBundle.getString("SleepingPills_name");
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, String string) {
        if (player1 != null && player2 != null && player3 != null) {
            super.useCard(player1, player2, player3, string);
            board.giveCardToPlayer(player1, player1.robRandomCard());
            board.giveCardToPlayer(player2, player2.robRandomCard());
            board.giveCardToPlayer(player3, player3.robRandomCard());
        }
    }
}
