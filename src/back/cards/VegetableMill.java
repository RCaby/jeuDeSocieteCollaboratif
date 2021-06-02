package back.cards;

import java.util.ResourceBundle;

import back.ActionType;
import back.Board;
import back.Player;

/**
 * The {@code VegetableMill} class represents the Vegetable Mill Card.
 * 
 * <p>
 * The card Vegetable Mill converts two food rations in two water rations. It is
 * a single-use card, discarded after utilisation.
 * 
 * <p>
 * The class {@code VegetableMill} extends the abstract class {@link Card}.
 * 
 */
public class VegetableMill extends Card {
    private static final long serialVersionUID = -660399664086162903L;
    public static final int NUMBER_THIS_IN_DECK = 1;

    /**
     * Generates a new {@code VegetableMill} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public VegetableMill(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        cardName = stringsBundle.getString("VegetableMill_name");
        cardDescription = stringsBundle.getString("VegetableMill_description");
    }

    /**
     * Simulates the utilisation of the card, herited from {@link Card}. Does not
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
                .displayMessage(String.format(stringsBundle.getString("VegetableMill_smallDescription"), owner));
        board.removeFood(2);
        board.addWater(2);
        super.useCard(player1, player2, player3, action);
    }

    /**
     * Indicates whether the card can be played. This card can be played at any
     * time, as long as there are at least two food rations.
     */
    @Override
    public boolean canBeUsed() {
        return board.getFoodRations() >= 2;
    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_VEGETABLE_MILL;
    }
}
