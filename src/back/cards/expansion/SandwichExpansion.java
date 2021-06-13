package back.cards.expansion;

import java.util.ResourceBundle;

import back.Board;
import back.cards.Sandwich;

public class SandwichExpansion extends Sandwich {
    public static final int NUMBER_THIS_IN_DECK = 2;

    public SandwichExpansion(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        isFromExpansion = true;
    }

}
