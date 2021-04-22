package back.cards;

import back.Board;
import back.GamePhase;

public class GiftBasket extends Card {
    private static final long serialVersionUID = -925523317295840498L;
    public static final String NAME = "Gift Basket";

    public GiftBasket(Board board) {
        super(board);
    }

    public String toString() {
        return NAME;
    }
    // board

    @Override
    public boolean canBeUsed() {
        return board.getPhase() == GamePhase.GOODS_DISTRIBUTION && (true);// To be ended
    }
}
