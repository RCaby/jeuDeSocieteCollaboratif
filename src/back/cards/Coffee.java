package back.cards;

import java.util.ResourceBundle;

import back.ActionType;
import back.Board;
import back.Player;

public class Coffee extends Card {
    private static final long serialVersionUID = -8623588548777632699L;

    public Coffee(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);

    }

    public String toString() {
        return stringsBundle.getString("Coffee_name");
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {
        if (player1 != null) {
            super.useCard(player1, player2, player3, action);
            board.setTwicePlayingPlayer(player1);
        }
    }

}
