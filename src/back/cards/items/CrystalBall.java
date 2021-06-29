package back.cards.items;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import back.Board;
import back.Player;

/**
 * The {@code CrystalBall} class represents the CrystalBall Card.
 * 
 * <p>
 * The card Crystal Ball allows its owner to be last player to vote. It is not a
 * single-use card, discarded after the death of its owner.
 * 
 * <p>
 * The class {@code CrystalBall} extends the abstract class {@link Card}.
 * 
 */
public class CrystalBall extends Card {
    private static final long serialVersionUID = -8481863323618034059L;
    public static final int NUMBER_THIS_IN_DECK = 1;

    /**
     * Generates a new {@code CrystalBall} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public CrystalBall(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        isSingleUse = false;
        discardOnDeath = true;
        cardName = stringsBundle.getString("CrystalBall_name");
        cardDescription = stringsBundle.getString("CrystalBall_description");
        cardType = CardType.TOOL;
        revealedCardIcon = new ImageIcon("src/front/images/cards/CrystalBallRevealed.png");
    }

    /**
     * Indicates whether the card can be played. This card can be played only to
     * reveal it to the other player.
     */
    @Override
    public boolean canBeUsed() {
        return !isRevealed;
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action, Card card) {
        board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("RevealsCard"), owner, this));
        board.getMainBoardFront()
                .displayMessage(String.format(stringsBundle.getString("CrystalBall_smallDescription"), owner));
        super.useCard(player1, player2, player3, action, card);
    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_CRYSTAL_BALL;
    }

    @Override
    public int getCardImpactOnOpinionOnSee() {
        return IMPACT_CRYSTAL_BALL_SEE;
    }
}
