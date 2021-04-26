package back.cards;

import java.util.ResourceBundle;

import back.ActionType;
import back.Board;
import back.Player;

public class Conch extends Card {
    private static final long serialVersionUID = -2323509442041822515L;

    public Conch(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
    }

    public String toString() {
        return stringsBundle.getString("Conch_name");
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {
        super.useCard(player1, player2, player3, action);
        if (owner != null) {
            board.setChief(owner);
        }

    }
    // Board

}
