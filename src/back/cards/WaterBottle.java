package back.cards;

import back.Board;
import back.Player;

public class WaterBottle extends Card {
    private static final long serialVersionUID = -6627360822903550195L;
    public static final String NAME = "Water Bottle";

    public WaterBottle(Board board) {
        super(board);
    }

    public String toString() {
        return NAME;
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, String string) {
        super.useCard(player1, player2, player3, string);
        board.addWater(1);
    }
}
