package back.cards;

import back.Board;

public class BoardGameQuoridor extends Card {
    private static final long serialVersionUID = -5704752866503737116L;
    public static final String NAME = "Board Game Quoridor";

    public BoardGameQuoridor(Board board) {
        super(board);
    }

    public String toString() {
        return NAME;
    }

    // Nothing
}
