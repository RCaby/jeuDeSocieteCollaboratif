package back.cards;

import java.util.ResourceBundle;

import back.Board;

public class OldBrief extends Card {
    private static final long serialVersionUID = 1653723168765321873L;

    public OldBrief(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
    }

    public String toString() {
        return stringsBundle.getString("OldBrief");
    }

}
