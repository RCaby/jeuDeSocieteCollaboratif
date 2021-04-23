package back.cards;

import java.util.ResourceBundle;

import back.Board;

public class LuxuryCarKey extends Card {
    private static final long serialVersionUID = -2727390028133337201L;

    public LuxuryCarKey(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
    }

    public String toString() {
        return stringsBundle.getString("LuxuryCarKey_name");
    }
}
