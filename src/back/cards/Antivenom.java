package back.cards;

import back.Board;
import back.Player;
import back.PlayerState;

public class Antivenom extends Card {

    private static final long serialVersionUID = 6436526438360639610L;
    public static final String NAME = "Anti-venom";

    public Antivenom(Board board) {
        super(board);
    }

    public String toString() {
        return NAME;
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, String string) {
        super.useCard(player1, player2, player3, string);
        player1.setState(PlayerState.HEALTHY);
    }
}
