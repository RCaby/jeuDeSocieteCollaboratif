package back.cards.items.expansion;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import back.Board;
import back.Player;
import back.cards.items.Card;
import back.cards.items.CardType;

public class ExpandingBulletExpansion extends Card {
    public static final int NUMBER_THIS_IN_DECK = 1;

    public ExpandingBulletExpansion(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        isFromExpansion = true;
        revealedCardIcon = new ImageIcon("src/front/images/cards/expansion/ExpandingBulletRevealed.png");
        cardName = stringsBundle.getString("ExpandingBullet_name");
        cardDescription = stringsBundle.getString("ExpandingBullet_description");
        cardType = CardType.WEAPON;
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action, Card card) {
        board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("NoTarget"), owner, this));
        board.getMainBoardFront()
                .displayMessage(String.format(stringsBundle.getString("ExpandingBullet_smallDescription"), owner));
        super.useCard(player1, player2, player3, action, card);

    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_EXPANDING_BULLET;
    }

    @Override
    public int getCardImpactOnOpinionOnSee() {
        return IMPACT_EXPANDING_BULLET_SEE;
    }

    @Override
    public boolean canBeUsed() {
        return false;
    }

}
