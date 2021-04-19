package back.cards;

import back.Board;
import back.Player;

public class WoodenPlank extends Card {
    private static final long serialVersionUID = 8325546132605573941L;
    public static final String NAME = "Wooden Plank";

    public WoodenPlank(Board board) {
        super(board);
    }

    public String toString() {
        return NAME;
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, String string) {
        super.useCard(player1, player2, player3, string);
        board.addPlank();
    }

}
