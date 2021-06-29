package back.cards.items;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import back.Board;
import back.Player;

/**
 * The {@code MetalSheet} class represents the Metal Sheet Card.
 * 
 * <p>
 * The card Metal Sheet protects its owner from one utilization of {@link Gun}.
 * It is a single-use card, discarded after utilization.
 * 
 * <p>
 * The class {@code MetalSheet} extends the abstract class {@link Card}.
 * 
 */
public class MetalSheet extends Card {
    private static final long serialVersionUID = -4745104393826579400L;
    public static final int NUMBER_THIS_IN_DECK = 2;

    /**
     * Generates a new {@code MetalSheet} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public MetalSheet(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        cardName = stringsBundle.getString("MetalSheet_name");
        cardDescription = stringsBundle.getString("MetalSheet_description");
        cardType = CardType.WEAPON;
        revealedCardIcon = new ImageIcon("src/front/images/cards/MetalSheetRevealed.png");
    }

    /**
     * Indicates whether the card can be played. This card cannot be used, as it is
     * only used in response of another card.
     */
    @Override
    public boolean canBeUsed() {
        return false;
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action, Card card) {
        board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("NoTarget"), owner, this));
        board.getMainBoardFront()
                .displayMessage(String.format(stringsBundle.getString("MetalSheet_smallDescription"), owner));
        super.useCard(player1, player2, player3, action, card);
    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_METAL_SHEET;
    }

    @Override
    public int getCardImpactOnOpinionOnSee() {
        return IMPACT_METAL_SHEET_SEE;
    }

}
