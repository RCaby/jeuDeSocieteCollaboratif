package back.cards;

import back.Board;

public class CrystalBall extends Card {
    private static final long serialVersionUID = -8481863323618034059L;
    public static final String NAME = "Crystal Ball";

    public String toString() {
        return NAME;
    }

    public CrystalBall(Board board) {
        super(board);
        isSingleUse = false;
        discardOnDeath = true;
    }

    // Board
    // Permanent
    // DiscardOnDeath

    @Override
    public boolean canBeUsed() {
        return !isRevealed;
    }
}
