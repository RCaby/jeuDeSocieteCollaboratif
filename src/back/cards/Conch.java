package back.cards;

import java.util.ResourceBundle;

import back.Board;

public class Conch extends Card {
    private static final long serialVersionUID = -2323509442041822515L;

    public Conch(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
    }

    public String toString() {
        return stringsBundle.getString("Conch_name");
    }

    // Board

}
