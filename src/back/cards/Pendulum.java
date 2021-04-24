package back.cards;

import java.util.ResourceBundle;

import back.Board;
import back.Player;

public class Pendulum extends Card {
    private static final long serialVersionUID = 9023151678122174623L;

    public Pendulum(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
    }

    public String toString() {
        return stringsBundle.getString("Pendulum_name");
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, String string) {
        super.useCard(player1, player2, player3, string);
        // Imposer une action à un aurte joueur
    }

    @Override
    public boolean canBeUsed() {
        return board.getPlayerList().size() > 1;
    }
}
