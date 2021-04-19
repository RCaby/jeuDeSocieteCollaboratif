package back.cards;

import back.Board;

public class FishingRod extends Card {
    private static final long serialVersionUID = -2072913005557800074L;
    public static final String NAME = "Fishing Rod";

    public String toString() {
        return NAME;
    }

    public FishingRod(Board board) {
        super(board);
        isSingleUse = false;
        discardOnDeath = true;
    }

    // board

    // permanent

    // discardDeath

}
