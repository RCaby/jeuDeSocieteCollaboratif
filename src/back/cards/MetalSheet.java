package back.cards;

import java.util.ResourceBundle;

import back.Board;

public class MetalSheet extends Card {
    private static final long serialVersionUID = -4745104393826579400L;

    public MetalSheet(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
    }

    public String toString() {
        return stringsBundle.getString("MetalSheet_name");
    }

    @Override
    public boolean canBeUsed() {
        return false;
    }

}
