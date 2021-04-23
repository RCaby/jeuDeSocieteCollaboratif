package back.cards;

import java.util.ResourceBundle;

import back.Board;

public class Club extends Card {
    private static final long serialVersionUID = -8286765403755107392L;

    public Club(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        isSingleUse = false;
        discardOnDeath = true;

    }

    public String toString() {
        return stringsBundle.getString("Club_name");
    }

    // permanent
    // discardOnDeath
    // board

    @Override
    public boolean canBeUsed() {
        return !isRevealed;
    }

}
