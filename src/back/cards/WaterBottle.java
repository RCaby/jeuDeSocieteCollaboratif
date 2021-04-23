package back.cards;

import java.util.ResourceBundle;

import back.Board;
import back.Player;

public class WaterBottle extends Card {
    private static final long serialVersionUID = -6627360822903550195L;

    public WaterBottle(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
    }

    public String toString() {
        return stringsBundle.getString("WaterBottle_name");
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, String string) {
        super.useCard(player1, player2, player3, string);
        board.addWater(1);
    }
}
