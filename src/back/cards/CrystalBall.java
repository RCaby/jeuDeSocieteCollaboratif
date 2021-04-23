package back.cards;

import java.util.ResourceBundle;

import back.Board;

public class CrystalBall extends Card {
    private static final long serialVersionUID = -8481863323618034059L;

    public String toString() {
        return stringsBundle.getString("CrystalBall_name");
    }

    public CrystalBall(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
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
