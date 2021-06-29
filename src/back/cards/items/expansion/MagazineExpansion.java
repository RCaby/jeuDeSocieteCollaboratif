package back.cards.items.expansion;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import back.Board;
import back.Player;
import back.cards.items.Card;
import back.cards.items.CardType;

public class MagazineExpansion extends Card {
    public static final int NUMBER_THIS_IN_DECK = 1;

    public MagazineExpansion(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        isFromExpansion = true;
        revealedCardIcon = new ImageIcon("src/front/images/cards/expansion/MagazineRevealed.png");
        cardName = stringsBundle.getString("Magazine_name");
        cardDescription = stringsBundle.getString("Magazine_description");
        cardType = CardType.USELESS;
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action, Card card) {
        board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("UselessCard"), owner, this));
        super.useCard(player1, player2, player3, action, card);

    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_MAGAZINE;
    }

    @Override
    public int getCardImpactOnOpinionOnSee() {
        return IMPACT_MAGAZINE_SEE;
    }

}
