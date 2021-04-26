package back.cards;

import java.util.ResourceBundle;

import back.ActionType;
import back.Board;
import back.Player;
import back.PlayerState;

public class StagnantWater extends Card {
    private static final long serialVersionUID = 206603853100641733L;

    public StagnantWater(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
    }

    public String toString() {
        return stringsBundle.getString("StagnantWater_name");
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {
        if (player1 != null) {

            super.useCard(player1, player2, player3, action);
            board.addWater(1);
            if (!board.getMatchesUsedThisRound()) {
                player1.setState(PlayerState.SICK);

            } else {
                board.setMatchesUsedThisRound(false);
            }

        }
    }
}
