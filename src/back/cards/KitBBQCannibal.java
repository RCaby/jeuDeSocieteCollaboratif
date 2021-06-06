package back.cards;

import java.util.ResourceBundle;

import back.ActionType;
import back.Board;
import back.Player;

/**
 * The {@code KitBBQCannibal} class represents the Kit BBQ Cannibal Card.
 * 
 * <p>
 * The card Kit BBQ Cannibal adds two food rations for each player dead this
 * round. It is a single-use card, discarded after utilisation.
 * 
 * <p>
 * The class {@code KitBBQCannibal} extends the abstract class {@link Card}.
 * 
 */
public class KitBBQCannibal extends Card {
    private static final long serialVersionUID = -1603389040110928591L;
    public static final int NUMBER_THIS_IN_DECK = 1;

    /**
     * Generates a new {@code KitBBQCannibal} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public KitBBQCannibal(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        cardName = stringsBundle.getString("KitBBQCannibal_name");
        cardDescription = stringsBundle.getString("KitBBQCannibal_description");
        cardType = CardType.FOOD;
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

        int foodGot = 2 * board.getDeadThisRound().size();
        board.addFood(foodGot);
        board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("NoTarget"), owner, this));
        board.getMainBoardFront().displayMessage(
                String.format(stringsBundle.getString("KitBBQCannibal_smallDescription"), owner, foodGot));
        super.useCard(player1, player2, player3, action);
    }

    /**
     * Indicates whether the card can be played. This card can be played only at the
     * end of a round and if someone is dead during this round.
     */
    @Override
    public boolean canBeUsed() {
        return !board.getDeadThisRound().isEmpty();
    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_KIT_BBQ_CANNIBAL;
    }
}
