package back.cards;

import java.util.ResourceBundle;

import back.Board;

public class Cartridge extends Card {
    private static final long serialVersionUID = 4958491342904749988L;

    public Cartridge(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
    }

    public String toString() {
        return stringsBundle.getString("Cartridge_name");
    }

    // nothing

    @Override
    public boolean canBeUsed() {
        return false;
    }
}
