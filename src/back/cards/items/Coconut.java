package back.cards.items;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import back.Board;
import back.Player;

/**
 * The {@code Coconut} class represents the Coconut Card.
 * 
 * <p>
 * The card Coconut adds three water rations. It is a single-use card, discarded
 * after utilization.
 * 
 * <p>
 * The class {@code Coconut} extends the abstract class {@link Card}.
 * 
 */
public class Coconut extends Card {
    private static final long serialVersionUID = 9141126346453648197L;
    public static final int NUMBER_THIS_IN_DECK = 1;

    /**
     * Generates a new {@code Coconut} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public Coconut(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        cardName = stringsBundle.getString("Coconut_name");
        cardDescription = stringsBundle.getString("Coconut_description");
        cardType = CardType.WATER;
        revealedCardIcon = new ImageIcon("src/front/images/cards/CoconutRevealed.png");
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
    public void useCard(Player player1, Player player2, Player player3, ActionType action, Card card) {
        board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("NoTarget"), owner, this));
        board.getMainBoardFront()
                .displayMessage(String.format(stringsBundle.getString("Coconut_smallDescription"), owner));

        board.addWater(3);
        super.useCard(player1, player2, player3, action, card);
    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_COCONUT;
    }

}
