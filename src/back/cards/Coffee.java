package back.cards;

import java.util.ResourceBundle;

import back.Board;

public class Coffee extends Card {
    private static final long serialVersionUID = -8623588548777632699L;

    public Coffee(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);

    }

    public String toString() {
        return stringsBundle.getString("Coffee_name");
    }

    // board

}
