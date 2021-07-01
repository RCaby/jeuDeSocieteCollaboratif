package back.cards.items.expansion;

import java.util.ResourceBundle;

import back.Board;
import back.cards.items.Gun;

public class GunExpansion extends Gun {

    public static final int NUMBER_THIS_IN_DECK = 1;

    public GunExpansion(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        isFromExpansion = true;
    }

}