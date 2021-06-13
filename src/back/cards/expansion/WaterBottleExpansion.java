package back.cards.expansion;

import java.util.ResourceBundle;

import back.Board;
import back.cards.WaterBottle;

public class WaterBottleExpansion extends WaterBottle {
    public static final int NUMBER_THIS_IN_DECK = 2;

    public WaterBottleExpansion(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        isFromExpansion = true;
    }

}
