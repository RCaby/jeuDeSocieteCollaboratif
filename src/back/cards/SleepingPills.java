package back.cards;

import back.Board;
import back.Player;

public class SleepingPills extends Card {
    private static final long serialVersionUID = -722036692167231970L;
    public static final String NAME = "Sleeping Pills";

    public SleepingPills(Board board) {
        super(board);
    }

    public String toString() {
        return NAME;
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, String string) {
        super.useCard(player1, player2, player3, string);
        board.giveCardToPlayer(player1, player1.robRandomCard());
        board.giveCardToPlayer(player2, player2.robRandomCard());
        board.giveCardToPlayer(player3, player3.robRandomCard());
    }
}
