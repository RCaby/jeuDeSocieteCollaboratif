package back.cards.expansion;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import back.Board;
import back.Player;
import back.cards.Card;
import back.cards.CardType;

public class WhipExpansion extends Card {

    public static final int NUMBER_THIS_IN_DECK = 1;

    public WhipExpansion(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        isFromExpansion = true;
        revealedCardIcon = new ImageIcon("src/front/images/cards/expansion/WhipRevealed.png");
        cardName = stringsBundle.getString("Whip_name");
        cardDescription = stringsBundle.getString("Whip_description");
        cardType = CardType.THREAT;
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {
        // TODO
        // board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("NoTarget"),
        // owner, this));
        // board.getMainBoardFront().displayMessage(stringsBundle.getString("FishBowl_smallDescription"));
        super.useCard(player1, player2, player3, action);

    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_WHIP;
    }

    @Override
    public boolean canBeUsed() {
        // TODO
        return true;
    }

}
