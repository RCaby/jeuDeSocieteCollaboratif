package back.cards;

import back.Board;

public class Matches extends Card {
    private static final long serialVersionUID = -5512438010000301669L;
    public static final String NAME = "Matches";

    public Matches(Board board) {
        super(board);
    }

    public String toString() {
        return NAME;
    }

    // Nothing for now
    @Override
    public boolean canBeUsed() {
        return false;
    }
}
