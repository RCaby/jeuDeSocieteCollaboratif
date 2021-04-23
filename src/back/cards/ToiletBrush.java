package back.cards;

import java.util.ResourceBundle;

import back.Board;

public class ToiletBrush extends Card {
    private static final long serialVersionUID = -5505039183789857500L;

    public ToiletBrush(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
    }

    public String toString() {
        return stringsBundle.getString("ToiletBrush_name");
    }

}
