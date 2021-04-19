package back.cards;

import back.Board;

public class MetalSheet extends Card {
    private static final long serialVersionUID = -4745104393826579400L;
    public static final String NAME = "Metal Sheet";

    public MetalSheet(Board board) {
        super(board);
    }

    public String toString() {
        return NAME;
    }

    @Override
    public boolean canBeUsed() {
        return false;
    }

}
