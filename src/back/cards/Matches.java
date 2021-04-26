package back.cards;

import java.util.ResourceBundle;

import back.ActionType;
import back.Board;
import back.Player;

public class Matches extends Card {
    private static final long serialVersionUID = -5512438010000301669L;

    public Matches(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
    }

    public String toString() {
        return stringsBundle.getString("Matches_name");
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {
        super.useCard(player1, player2, player3, action);
        board.setMatchesUsedThisRound(true);
    }

    // Nothing for now
    @Override
    public boolean canBeUsed() {
        return true;
    }
}
