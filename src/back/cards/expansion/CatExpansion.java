package back.cards.expansion;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import back.Board;
import back.Player;
import back.cards.Card;
import back.cards.CardType;

public class CatExpansion extends Card {
    public static final int NUMBER_THIS_IN_DECK = 1;

    public CatExpansion(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        isFromExpansion = true;
        revealedCardIcon = new ImageIcon("src/front/images/cards/expansion/CatRevealed.png");
        cardName = stringsBundle.getString("Cat_name");
        cardDescription = stringsBundle.getString("Cat_description");
        cardType = CardType.PROTECTION;
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {
        board.addFood(2);
        board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("NoTarget"), owner, this));
        board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("Cat_smallDescription"), owner));
        super.useCard(player1, player2, player3, action);

    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_CAT;
    }

    @Override
    public boolean canBeUsed() {
        return !board.isThereEnoughGoodsForAll(false);
    }

}
