package back.cards;

import java.util.ResourceBundle;

import back.Board;

public class FishingRod extends Card {
    private static final long serialVersionUID = -2072913005557800074L;

    public String toString() {
        return stringsBundle.getString("FishingRod_name");
    }

    public FishingRod(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        isSingleUse = false;
        discardOnDeath = true;
    }

    // board

    // permanent

    // discardDeath

    @Override
    public boolean canBeUsed() {
        return !isRevealed;
    }

}
