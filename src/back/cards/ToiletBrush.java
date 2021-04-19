package back.cards;

import back.Board;

public class ToiletBrush extends Card {
    private static final long serialVersionUID = -5505039183789857500L;
    public static final String NAME = "Toilet Brush";

    public ToiletBrush(Board board) {
        super(board);
    }

    public String toString() {
        return NAME;
    }

}
