package back.cards.expansion;

import java.util.ResourceBundle;

import back.Board;
import back.cards.Gun;

public class GunExpansion extends Gun {

    public static final int NUMBER_THIS_IN_DECK = 10;

    public GunExpansion(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        isFromExpansion = true;
    }

}
