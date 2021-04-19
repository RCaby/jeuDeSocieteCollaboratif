package back.cards;

import back.Board;
import back.Player;
import back.PlayerState;

public class RottenFish extends Card {
    private static final long serialVersionUID = 4118478202921704382L;
    public static final String NAME = "Rotten Fish";

    public RottenFish(Board board) {
        super(board);
    }

    public String toString() {
        return NAME;
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, String string) {
        super.useCard(player1, player2, player3, string);
        board.addFood(1);
        Card matchesFromOwner = player1.getMatches();
        Card matchesFromUser = player2.getMatches();
        if (matchesFromOwner != null || matchesFromUser != null) {
            // TODO
        } else {
            // TODO
        }
        player2.setState(PlayerState.SICK);

    }
}
