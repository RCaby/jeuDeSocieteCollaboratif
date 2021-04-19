package back.cards;

import back.Board;

public class Barometer extends Card {
    private static final long serialVersionUID = -1846990989485437371L;
    public static final String NAME = "Barometer";

    public Barometer(Board board) {
        super(board);
    }

    public String toString() {
        return NAME;
    }

    // Board
}
