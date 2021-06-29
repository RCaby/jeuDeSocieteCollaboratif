package back.cards.items.expansion;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import back.Board;
import back.Player;
import back.cards.items.Card;
import back.cards.items.CardType;

public class FishBowlExpansion extends Card {

    public static final int NUMBER_THIS_IN_DECK = 1;

    public FishBowlExpansion(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        isFromExpansion = true;
        revealedCardIcon = new ImageIcon("src/front/images/cards/expansion/FishBowlRevealed.png");
        cardName = stringsBundle.getString("FishBowl_name");
        cardDescription = stringsBundle.getString("FishBowl_description");
        cardType = CardType.PROTECTION;
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action, Card card) {
        board.addWater(2);
        board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("NoTarget"), owner, this));
        board.getMainBoardFront()
                .displayMessage(String.format(stringsBundle.getString("FishBowl_smallDescription"), owner));
        super.useCard(player1, player2, player3, action, card);

    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_FISH_BOWL;
    }

    @Override
    public int getCardImpactOnOpinionOnSee() {
        return IMPACT_FISH_BOWL_SEE;
    }

    @Override
    public boolean canBeUsed() {
        return !board.isThereEnoughGoodsForAll(false);
    }
}
