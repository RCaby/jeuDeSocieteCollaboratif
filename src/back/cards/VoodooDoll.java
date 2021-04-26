package back.cards;

import java.util.Random;
import java.util.ResourceBundle;

import back.ActionType;
import back.Board;
import back.GamePhase;
import back.Player;
import back.PlayerState;

public class VoodooDoll extends Card {
    private static final long serialVersionUID = 8543007868222050206L;
    private Random random;

    public VoodooDoll(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        random = new Random();
    }

    public String toString() {
        return stringsBundle.getString("VoodooDoll_name");
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {
        if (!board.getDeadThisRound().isEmpty()) {
            super.useCard(player1, player2, player3, action);
            int pickedPlayer = random.nextInt(board.getDeadThisRound().size());
            Player player = board.getDeadThisRound().get(pickedPlayer);
            player.setState(PlayerState.HEALTHY);
            board.getDeadThisRound().remove(player);
        }
    }

    @Override
    public boolean canBeUsed() {
        return board.getPhase() == GamePhase.ROUND_BEGINNING && !board.getDeadThisRound().isEmpty();
    }
}
