package back.cards;

import back.Board;
import back.Player;

public class KitBBQCannibal extends Card {
    private static final long serialVersionUID = -1603389040110928591L;
    public static final String NAME = "Kit BBQ Cannibal";

    public KitBBQCannibal(Board board) {
        super(board);
    }

    public String toString() {
        return NAME;
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, String string) {
        super.useCard(player1, player2, player3, string);
        board.addFood(2 * board.getNbPlayersAlive());
    }

    @Override
    public boolean canBeUsed() {
        return board.getDeadThisRound() > 0;
    }
}
