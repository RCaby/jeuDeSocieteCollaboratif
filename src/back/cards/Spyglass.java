package back.cards;

import back.Board;
import back.Player;

public class Spyglass extends Card {
    private static final long serialVersionUID = -4985929183848934161L;
    public static final String NAME = "Spyglass";

    public Spyglass(Board board) {
        super(board);
    }

    public String toString() {
        return NAME;
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, String string) {
        super.useCard(player1, player2, player3, string);
        // Nothing for now
    }

}
