package back.cards;

import java.util.ResourceBundle;

import back.Board;
import back.GamePhase;
import back.Player;
import back.PlayerState;

public class VoodooDoll extends Card {
    private static final long serialVersionUID = 8543007868222050206L;

    public VoodooDoll(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
    }

    public String toString() {
        return stringsBundle.getString("VoodooDoll_name");
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, String string) {
        if (player1 != null) {
            super.useCard(player1, player2, player3, string);
            player1.setState(PlayerState.HEALTHY);
            board.setDeadThisRound(board.getDeadThisRound() - 1);
        }
    }

    @Override
    public boolean canBeUsed() {
        return board.getPhase() == GamePhase.ROUND_BEGINNING;
    }
}
