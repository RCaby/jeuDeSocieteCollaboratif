package back.cards.expansion;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import back.Board;
import back.Player;
import back.cards.Axe;
import back.cards.Card;
import back.cards.CardType;

public class HoneExpansion extends Card {
    public static final int NUMBER_THIS_IN_DECK = 1;

    public HoneExpansion(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        isFromExpansion = true;
        revealedCardIcon = new ImageIcon("src/front/images/cards/expansion/HoneRevealed.png");
        cardName = stringsBundle.getString("Hone_name");
        cardDescription = stringsBundle.getString("Hone_description");
        cardType = CardType.WEAPON;
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action, Card card) {
        var axe = owner.getCardType(Axe.class);
        var indexOfOwner = board.getPlayerList().indexOf(owner);
        var neighborAfter = board.getPlayerAliveAfterBefore(indexOfOwner, true);
        var neighborBefore = board.getPlayerAliveAfterBefore(indexOfOwner, false);
        if (axe != null && player1 != null && (player1.equals(neighborAfter) || player1.equals(neighborBefore))) {
            board.killPlayer(player1);
            owner.discardCard(axe);
            board.getMainBoardFront()
                    .displayMessage(String.format(stringsBundle.getString("OneTarget"), owner, this, player1));
            board.getMainBoardFront()
                    .displayMessage(String.format(stringsBundle.getString("Hone_smallDescription"), owner, player1));
            super.useCard(player1, player2, player3, action, card);
        }

    }

    @Override
    public boolean[] getNeededParameters() {
        return new boolean[] { true, false, false, false, false };
    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_HONE;
    }

    @Override
    public int getCardImpactOnOpinionOnTarget() {
        return IMPACT_HONE_ON_TARGET;
    }

    @Override
    public boolean canBeUsed() {
        return owner.getCardType(Axe.class) != null && board.getNbPlayersAlive() > 1;
    }

}
