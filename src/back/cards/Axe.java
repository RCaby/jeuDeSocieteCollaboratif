package back.cards;

import back.Board;

public class Axe extends Card {
    private static final long serialVersionUID = -20152998682170229L;
    public static final String NAME = "Axe";

    public Axe(Board board) {
        super(board);
        isSingleUse = false;
        discardOnDeath = true;
    }

    public String toString() {
        return NAME;
    }

    @Override
    public String getCardName() {
        return NAME;
    }

    // permanent
    // board

    @Override
    public boolean canBeUsed() {
        return !isRevealed;
    }
}
