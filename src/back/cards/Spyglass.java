package back.cards;

import java.util.ResourceBundle;

import back.Board;
import back.Player;

public class Spyglass extends Card {
    private static final long serialVersionUID = -4985929183848934161L;

    public Spyglass(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
    }

    public String toString() {
        return stringsBundle.getString("Spyglass_name");
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, String string) {
        super.useCard(player1, player2, player3, string);
        // Nothing for now
    }

}
