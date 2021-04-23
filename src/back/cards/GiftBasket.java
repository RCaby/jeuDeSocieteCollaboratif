package back.cards;

import java.util.ResourceBundle;

import back.Board;
import back.GamePhase;

public class GiftBasket extends Card {
    private static final long serialVersionUID = -925523317295840498L;

    public GiftBasket(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
    }

    public String toString() {
        return stringsBundle.getString("GiftBasket_name");
    }
    // board

    @Override
    public boolean canBeUsed() {
        return board.getPhase() == GamePhase.GOODS_DISTRIBUTION && (true);// To be ended
    }
}
