package back.cards.items.expansion;

import java.util.ResourceBundle;

import back.Board;
import back.cards.items.Cartridge;

public class CartridgeExpansion extends Cartridge {
    public static final int NUMBER_THIS_IN_DECK = 10;

    public CartridgeExpansion(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        isFromExpansion = true;
    }

}
