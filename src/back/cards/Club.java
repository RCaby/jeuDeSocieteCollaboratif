package back.cards;

import back.Board;

public class Club extends Card {
    private static final long serialVersionUID = -8286765403755107392L;
    public static final String NAME = "Club";

    public Club(Board board) {
        super(board);
        isSingleUse = false;
        discardOnDeath = true;
    }

    public String toString() {
        return NAME;
    }

    // permanent
    // discardOnDeath
    // board

    @Override
    public boolean canBeUsed() {
        return !isRevealed;
    }

}
