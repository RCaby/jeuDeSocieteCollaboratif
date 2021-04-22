package back.cards;

import back.Board;
import back.Player;
import back.PlayerState;

public class StagnantWater extends Card {
    private static final long serialVersionUID = 206603853100641733L;
    public static final String NAME = "Stagnant Water";

    public StagnantWater(Board board) {
        super(board);
    }

    public String toString() {
        return NAME;
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, String string) {
        if (player1 != null) {
            super.useCard(player1, player2, player3, string);
            board.addWater(1);
            player1.setState(PlayerState.SICK);// See RottenFish}
        }
    }
}
