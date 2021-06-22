package back.cards.expansion;

import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import back.Board;
import back.Player;
import back.PlayerState;
import back.cards.Card;
import back.cards.CardType;

public class TaserExpansion extends Card {
    public static final int NUMBER_THIS_IN_DECK = 10;

    public TaserExpansion(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        isFromExpansion = true;
        revealedCardIcon = new ImageIcon("src/front/images/cards/expansion/TaserRevealed.png");
        cardName = stringsBundle.getString("Taser_name");
        cardDescription = stringsBundle.getString("Taser_description");
        cardType = CardType.THREAT;
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action, Card card) {
        if (player1 != null && card != null) {
            card.setOwner(owner);
            player1.removeCard(card);
            owner.addCardToInventory(card);
            board.getMainBoardFront()
                    .displayMessage(String.format(stringsBundle.getString("OneTarget"), owner, this, player1));
            board.getMainBoardFront().displayMessage(
                    String.format(stringsBundle.getString("Taser_smallDescription"), owner, card, player1));
            super.useCard(player1, player2, player3, action, card);
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
    public boolean[] getNeededParameters() {
        return new boolean[] { true, false, false, false, true };
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

    @Override
    public ActionListener getActionListener() {
        return board.getMainBoardFront().new CardPlayerActionListenerOneTargetOneCard(this);
    }

}
