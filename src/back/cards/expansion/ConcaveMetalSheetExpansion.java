package back.cards.expansion;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import back.Board;
import back.Player;
import back.cards.Card;
import back.cards.CardType;

public class ConcaveMetalSheetExpansion extends Card {
    public static final int NUMBER_THIS_IN_DECK = 1;

    public ConcaveMetalSheetExpansion(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        isFromExpansion = true;
        revealedCardIcon = new ImageIcon("src/front/images/cards/expansion/ConcaveMetalSheetRevealed.png");
        cardName = stringsBundle.getString("ConcaveMetalSheet_name");
        cardDescription = stringsBundle.getString("ConcaveMetalSheet_description");
        cardType = CardType.WEAPON;
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {
        board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("NoTarget"), owner, this));
        board.getMainBoardFront().displayMessage(stringsBundle.getString("ConcaveMetalSheet_smallDescription"));
        super.useCard(player1, player2, player3, action);

    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_CONCAVE_METAL_SHEET;
    }

    @Override
    public boolean canBeUsed() {
        return false;
    }

}
