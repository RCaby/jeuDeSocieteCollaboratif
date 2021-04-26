package back.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import back.ActionType;
import back.Board;
import back.Player;
import back.PlayerState;

public class Spyglass extends Card {
    private static final long serialVersionUID = -4985929183848934161L;

    public Spyglass(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
    }

    public String toString() {
        return stringsBundle.getString("Spyglass_name");
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {
        super.useCard(player1, player2, player3, action);
        List<Card> cardList = new ArrayList<>();
        for (Player player : board.getPlayerList()) {
            if (player.getState() != PlayerState.DEAD) {
                for (int index = 0; index < player.getCardNumber(); index++) {
                    cardList.add(player.getCard(index));
                }
            }
        }
        System.out.println(cardList);
    }

}
