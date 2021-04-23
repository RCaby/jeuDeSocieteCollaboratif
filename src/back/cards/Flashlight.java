package back.cards;

import java.util.ResourceBundle;

import back.Board;

public class Flashlight extends Card {
    private static final long serialVersionUID = 669754759637594734L;

    public Flashlight(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
    }

    public String toString() {
        return stringsBundle.getString("Flashlight_name");
    }

    // Board
}
