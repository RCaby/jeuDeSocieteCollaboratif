package back.cards;

import java.util.ResourceBundle;

import back.ActionType;
import back.Board;
import back.Player;

public class VegetableMill extends Card {
    private static final long serialVersionUID = -660399664086162903L;

    public VegetableMill(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
    }

    public String toString() {
        return stringsBundle.getString("VegetableMill_name");
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {
        super.useCard(player1, player2, player3, action);
        board.removeFood(2);
        board.addWater(2);
    }

    @Override
    public boolean canBeUsed() {
        return board.getFoodRations() >= 2;
    }
}
