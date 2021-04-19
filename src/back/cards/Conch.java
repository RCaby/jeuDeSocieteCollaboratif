package back.cards;

import back.Board;

public class Conch extends Card {
    private static final long serialVersionUID = -2323509442041822515L;
    public static final String NAME = "Conch";

    public Conch(Board board) {
        super(board);
    }

    public String toString() {
        return NAME;
    }

    // Board
}
