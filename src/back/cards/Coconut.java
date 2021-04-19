package back.cards;

import back.Board;

public class Coconut extends Card {
    private static final long serialVersionUID = 9141126346453648197L;
    public static final String NAME = "Coconut";

    public Coconut(Board board) {
        super(board);
    }

    public String toString() {
        return NAME;
    }

    // Board
}
