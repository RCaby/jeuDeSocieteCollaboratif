package back.cards;

import back.Board;

public class OldBrief extends Card {
    private static final long serialVersionUID = 1653723168765321873L;
    public static final String NAME = "Old Brief";

    public OldBrief(Board board) {
        super(board);
    }

    public String toString() {
        return NAME;
    }

}
