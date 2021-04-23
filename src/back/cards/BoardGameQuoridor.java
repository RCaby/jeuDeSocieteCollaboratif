package back.cards;

import java.util.ResourceBundle;

import back.Board;

public class BoardGameQuoridor extends Card {
    private static final long serialVersionUID = -5704752866503737116L;

    public BoardGameQuoridor(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
    }

    public String toString() {
        return stringsBundle.getString("BoardGameQuoridor_name");
    }

    // Nothing
}
