package back.cards;

import back.Board;
import back.Player;

public class Sandwich extends Card {
    private static final long serialVersionUID = -8278658344259908767L;
    public static final String NAME = "Sandwich";

    public Sandwich(Board board) {
        super(board);
    }

    public String toString() {
        return NAME;
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, String string) {
        super.useCard(player1, player2, player3, string);
        board.addFood(1);
    }
}
