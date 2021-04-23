package back.cards;

import java.util.ResourceBundle;

import back.Board;

public class Gourd extends Card {
    private static final long serialVersionUID = -6073595887835577054L;

    public Gourd(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        isSingleUse = false;
        discardOnDeath = true;
    }

    public String toString() {
        return stringsBundle.getString("Gourd_name");
    }

    // discard after death

    // permanent

    // board

    @Override
    public boolean canBeUsed() {
        return !isRevealed;
    }

}
