package back.cards.expansion;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import back.Board;
import back.Player;
import back.PlayerState;
import back.cards.Card;
import back.cards.CardType;

public class TaserExpansion extends Card {
    public static final int NUMBER_THIS_IN_DECK = 1;

    public TaserExpansion(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        isFromExpansion = true;
        revealedCardIcon = new ImageIcon("src/front/images/cards/expansion/TaserRevealed.png");
        cardName = stringsBundle.getString("Taser_name");
        cardDescription = stringsBundle.getString("Taser_description");
        cardType = CardType.THREAT;
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {
        if (player1 != null && true) { // TODO
            board.getMainBoardFront()
                    .displayMessage(String.format(stringsBundle.getString("OneTarget"), owner, this, player1));
            board.getMainBoardFront().displayMessage(stringsBundle.getString("Taser_smallDescription"));
            super.useCard(player1, player2, player3, action);
        }

    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_TASER;
    }

    @Override
    public int getCardImpactOnOpinionOnTarget() {
        return IMPACT_TASER_ON_TARGET;
    }

    @Override
    public boolean canBeUsed() {

        for (Player player : board.getPlayerList()) {
            if (!player.equals(owner) && player.getState() != PlayerState.DEAD
                    && !player.getInventoryRevealed().isEmpty()) {
                return true;
            }
        }
        return false;
    }

}
