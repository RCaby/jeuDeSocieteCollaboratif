package back.cards;

import back.Board;

public class Coffee extends Card {
    private static final long serialVersionUID = -8623588548777632699L;
    public static final String NAME = "Coffee";

    public Coffee(Board board) {
        super(board);
    }

    public String toString() {
        return NAME;
    }

    // board
}
