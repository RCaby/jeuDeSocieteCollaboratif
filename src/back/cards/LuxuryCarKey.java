package back.cards;

import back.Board;

public class LuxuryCarKey extends Card {
    private static final long serialVersionUID = -2727390028133337201L;
    public static final String NAME = "Luxury Car Key";

    public LuxuryCarKey(Board board) {
        super(board);
    }

    public String toString() {
        return NAME;
    }
}
