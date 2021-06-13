package back.cards.expansion;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import back.Board;
import back.Player;
import back.cards.Card;
import back.cards.CardType;

public class MagazineExpansion extends Card {
    public static final int NUMBER_THIS_IN_DECK = 1;

    protected MagazineExpansion(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        isFromExpansion = true;
        revealedCardIcon = new ImageIcon("src/front/images/cards/expansion/MagazineRevealed.png");
        cardName = stringsBundle.getString("Magazine_name");
        cardDescription = stringsBundle.getString("Magazine_description");
        cardType = CardType.USELESS;
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {
        board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("UselessCard"), owner, this));
        board.getMainBoardFront().displayMessage(stringsBundle.getString("Magazine_smallDescription"));
        super.useCard(player1, player2, player3, action);

    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_MAGAZINE;
    }

}
