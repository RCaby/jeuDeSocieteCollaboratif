package back.cards;

import java.util.ResourceBundle;

import back.ActionType;
import back.Board;
import back.Player;
import back.PlayerState;

public class RottenFish extends Card {
    private static final long serialVersionUID = 4118478202921704382L;

    public RottenFish(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
    }

    public String toString() {
        return stringsBundle.getString("RottenFish_name");
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {
        super.useCard(player1, player2, player3, action);
        if (player1 != null) {
            board.addFood(1);
            if (!board.getMatchesUsedThisRound()) {
                player1.setState(PlayerState.SICK);
            } else {
                board.setMatchesUsedThisRound(false);
            }

        }

    }
}
