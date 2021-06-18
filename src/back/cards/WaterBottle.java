package back.cards;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import back.Board;
import back.Player;

/**
 * The {@code WaterBottle} class represents the Water Bottle Card.
 * 
 * <p>
 * The card Water Bottle adds one water ration. It is a single-use card,
 * discarded after utilization.
 * 
 * <p>
 * The class {@code WaterBottle} extends the abstract class {@link Card}.
 * 
 */
public class WaterBottle extends Card {
    private static final long serialVersionUID = -6627360822903550195L;
    public static final int NUMBER_THIS_IN_DECK = 7;

    /**
     * Generates a new {@code WaterBottle} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public WaterBottle(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        cardName = stringsBundle.getString("WaterBottle_name");
        cardDescription = stringsBundle.getString("WaterBottle_description");
        cardType = CardType.WATER;
        revealedCardIcon = new ImageIcon("src/front/images/cards/WaterBottleRevealed.png");
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
                .displayMessage(String.format(stringsBundle.getString("WaterBottle_smallDescription"), owner));
        board.addWater(1);
        super.useCard(player1, player2, player3, action, card);
    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_WATER_BOTTLE;
    }
}
