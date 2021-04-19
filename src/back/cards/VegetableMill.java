package back.cards;

import back.Board;
import back.Player;

public class VegetableMill extends Card {
    private static final long serialVersionUID = -660399664086162903L;
    public static final String NAME = "Vegetable Mill";

    public VegetableMill(Board board) {
        super(board);
    }

    public String toString() {
        return NAME;
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, String string) {
        super.useCard(player1, player2, player3, string);
        board.removeFood(2);
        board.addWater(2);
    }

    @Override
    public boolean canBeUsed() {
        return board.getFoodRations() >= 2;
    }
}
