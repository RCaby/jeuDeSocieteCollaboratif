package back.cards.expansion;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import back.Board;
import back.Player;
import back.cards.Card;
import back.cards.CardType;

public class BuoyExpansion extends Card {
    public static final int NUMBER_THIS_IN_DECK = 1;

    public BuoyExpansion(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        isFromExpansion = true;
        isSingleUse = false;
        revealedCardIcon = new ImageIcon("src/front/images/cards/expansion/BuoyRevealed.png");
        cardName = stringsBundle.getString("Buoy_name");
        cardDescription = stringsBundle.getString("Buoy_description");
        cardType = CardType.TOOL;
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {
        // TODO modify action card
        board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("NoTarget"), owner, this));
        board.getMainBoardFront().displayMessage(stringsBundle.getString("Buoy_smallDescription"));
        super.useCard(player1, player2, player3, action);

    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_BUOY;
    }

}
