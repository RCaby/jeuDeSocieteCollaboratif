package back.cards;

import back.Board;
import back.Player;

public class Pendulum extends Card {
    private static final long serialVersionUID = 9023151678122174623L;
    public static final String NAME = "Pendulum";

    public Pendulum(Board board) {
        super(board);
    }

    public String toString() {
        return NAME;
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, String string) {
        super.useCard(player1, player2, player3, string);
        // Nothing for now
    }

    @Override
    public boolean canBeUsed() {
        return board.getPlayerList().size() > 1;
    }
}
