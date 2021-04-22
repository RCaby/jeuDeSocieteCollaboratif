package back.cards;

import back.Board;
import back.Player;

public class Coconut extends Card {
    private static final long serialVersionUID = 9141126346453648197L;
    public static final String NAME = "Coconut";

    public Coconut(Board board) {
        super(board);
    }

    public String toString() {
        return NAME;
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, String string) {
        super.useCard(player1, player2, player3, string);
        board.addWater(3);
    }

    // Board
}
