package back.cards;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import back.Board;
import back.Player;

/**
 * The {@code OldBrief} class represents the Old Brief Card.
 * 
 * <p>
 * The card Old Brief is useless. It is a single-use card, discarded after
 * utilization.
 * 
 * <p>
 * The class {@code OldBrief} extends the abstract class {@link Card}.
 * 
 */
public class OldBrief extends Card {
    private static final long serialVersionUID = 1653723168765321873L;
    public static final int NUMBER_THIS_IN_DECK = 1;

    /**
     * Generates a new {@code OldBrief} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public OldBrief(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        cardName = stringsBundle.getString("OldBrief_name");
        cardDescription = stringsBundle.getString("OldBrief_description");
        cardType = CardType.USELESS;
        revealedCardIcon = new ImageIcon("src/front/images/cards/OldBriefRevealed.png");
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action, Card card) {
        board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("UselessCard"), owner, this));
        super.useCard(player1, player2, player3, action, card);
    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_OLD_BRIEF;
    }
}
