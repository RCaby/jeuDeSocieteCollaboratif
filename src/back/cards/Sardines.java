package back.cards;

import java.util.ResourceBundle;

import back.ActionType;
import back.Board;
import back.Player;

public class Sardines extends Card {
    private static final long serialVersionUID = -1179600934965433180L;

    public Sardines(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
    }

    public String toString() {
        return stringsBundle.getString("Sardines_name");
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {
        super.useCard(player1, player2, player3, action);
        board.addFood(3);
    }
}
