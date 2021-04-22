package back.cards;

import back.Board;

public class Cartridge extends Card {
    private static final long serialVersionUID = 4958491342904749988L;
    public static final String NAME = "Cartridge";

    public Cartridge(Board board) {
        super(board);
    }

    public String toString() {
        return NAME;
    }

    // nothing

    @Override
    public boolean canBeUsed() {
        return false;
    }
}
