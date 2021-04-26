package back.cards;

import java.util.ResourceBundle;

import back.ActionType;
import back.Board;
import back.Player;
import back.PlayerState;

public class Antivenom extends Card {

    private static final long serialVersionUID = 6436526438360639610L;

    public Antivenom(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
    }

    public String toString() {
        return stringsBundle.getString("Antivenom_name");
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {
        if (player1 != null) {
            super.useCard(player1, player2, player3, action);
            player1.setState(PlayerState.HEALTHY);
        }

    }

    @Override
    public boolean canBeUsed() {
        boolean flag = false;
        for (Player player : board.getPlayerList()) {
            flag = flag || player.getState() == PlayerState.SICK;
        }
        return flag;
    }
}
