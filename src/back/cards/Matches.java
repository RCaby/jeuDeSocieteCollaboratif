package back.cards;

import java.util.ResourceBundle;

import back.Board;

public class Matches extends Card {
    private static final long serialVersionUID = -5512438010000301669L;

    public Matches(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
    }

    public String toString() {
        return stringsBundle.getString("Matches_name");
    }

    // Nothing for now
    @Override
    public boolean canBeUsed() {
        return false;
    }
}
