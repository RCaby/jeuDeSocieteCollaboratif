package back.cards;

import back.Board;

public class Gourd extends Card {
    private static final long serialVersionUID = -6073595887835577054L;
    public static final String NAME = "Gourd";

    public Gourd(Board board) {
        super(board);
        isSingleUse = false;
        discardOnDeath = true;
    }

    public String toString() {
        return NAME;
    }

    // discard after death

    // permanent

    // board

    @Override
    public boolean canBeUsed() {
        return !isRevealed;
    }

}
