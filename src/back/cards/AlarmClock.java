package back.cards;

import java.util.ResourceBundle;

import back.ActionType;
import back.Board;
import back.Player;
import back.PlayerState;

public class AlarmClock extends Card {

    private static final long serialVersionUID = -4056999539348867224L;

    public AlarmClock(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
    }

    public String toString() {
        return stringsBundle.getString("AlarmClock_name");
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {
        if (player1 != null && player1.getState() != PlayerState.DEAD) {
            super.useCard(player1, player2, player3, action);
            board.setNextChief(player1);
            System.out.println(player1 + " will be the next chief !");
        }
    }

}
