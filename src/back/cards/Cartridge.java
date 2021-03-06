package back.cards;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import back.Board;
import back.Player;

/**
 * The {@code Cartridge} class represents the Cartridge Card.
 * 
 * <p>
 * The card Cartridge allows its owner to attempt a kill with the {@link Gun}.
 * It is a single-use card, discarded after utilization.
 * 
 * <p>
 * The class {@code Cartridge} extends the abstract class {@link Card}.
 * 
 */
public class Cartridge extends Card {
    private static final long serialVersionUID = 4958491342904749988L;
    public static final int NUMBER_THIS_IN_DECK = 6;

    /**
     * Generates a new {@code Cartridge} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public Cartridge(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        cardName = stringsBundle.getString("Cartridge_name");
        cardDescription = stringsBundle.getString("Cartridge_description");
        cardType = CardType.WEAPON;
        revealedCardIcon = new ImageIcon("src/front/images/cards/CartridgeRevealed.png");
    }

    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {
        board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("NoTarget"), owner, this));
        board.getMainBoardFront()
                .displayMessage(String.format(stringsBundle.getString("Cartridge_smallDescription"), owner));
        super.useCard(player1, player2, player3, action);
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
    public int getCardImpactOnOpinion() {
        return IMPACT_CARTRIDGE;
    }
}
