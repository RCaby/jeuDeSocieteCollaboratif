package back.cards;

import back.Board;
import back.Player;

public class Sardines extends Card {
    private static final long serialVersionUID = -1179600934965433180L;
    public static final String NAME = "Sardines";

    public Sardines(Board board) {
        super(board);
    }

    public String toString() {
        return NAME;
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, String string) {
        super.useCard(player1, player2, player3, string);
        board.addFood(3);
    }
}
