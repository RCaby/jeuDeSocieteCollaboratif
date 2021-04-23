package back.cards;

import java.util.ResourceBundle;

import back.Board;

public class Barometer extends Card {
    private static final long serialVersionUID = -1846990989485437371L;

    public Barometer(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
    }

    public String toString() {
        return stringsBundle.getString("Barometer_name");
    }

    // Board
}
