package back.cards;

import back.Board;

public class Flashlight extends Card {
    private static final long serialVersionUID = 669754759637594734L;
    public static final String NAME = "Flashlight";

    public Flashlight(Board board) {
        super(board);
    }

    public String toString() {
        return NAME;
    }

    // Board
}
