package back.cards;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import back.Board;
import back.Player;

/**
 * The {@code Sandwich} class represents the Sandwich Card.
 * 
 * <p>
 * The card Sandwich adds one food ration. It is a single-use card, discarded
 * after utilization.
 * 
 * <p>
 * The class {@code Sandwich} extends the abstract class {@link Card}.
 * 
 */
public class Sandwich extends Card {
    private static final long serialVersionUID = -8278658344259908767L;
    public static final int NUMBER_THIS_IN_DECK = 7;

    /**
     * Generates a new {@code Sandwich} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public Sandwich(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        cardName = stringsBundle.getString("Sandwich_name");
        cardDescription = stringsBundle.getString("Sandwich_description");
        cardType = CardType.FOOD;
        revealedCardIcon = new ImageIcon("src/front/images/cards/SandwichRevealed.png");
    }

    /**
     * Simulates the utilization of the card, inherited from {@link Card}. Does not
     * need any parameter.
     * 
     * @param player1 not needed for this card
     * @param player2 not needed for this card
     * @param player3 not needed for this card
     * @param action  not needed for this card
     */
    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {
        board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("NoTarget"), owner, this));
        board.getMainBoardFront()
                .displayMessage(String.format(stringsBundle.getString("Sandwich_smallDescription"), owner, this));
        board.addFood(1);
        super.useCard(player1, player2, player3, action);
    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_SANDWICH;
    }
}
