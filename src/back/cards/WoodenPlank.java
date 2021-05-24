package back.cards;

import java.util.ResourceBundle;

import back.ActionType;
import back.Board;
import back.Player;

/**
 * The {@code WoodenPlank} class represents the Wooden Plank Card.
 * 
 * <p>
 * The card Wooden Plank adds one plank. It is a single-use card, discarded
 * after utilisation.
 * 
 * <p>
 * The class {@code WoodenPlank} extends the abstract class {@link Card}.
 * 
 */
public class WoodenPlank extends Card {
    private static final long serialVersionUID = 8325546132605573941L;
    public static final int NUMBER_THIS_IN_DECK = 1;

    /**
     * Generates a new {@code WoodenPlank} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public WoodenPlank(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        cardName = stringsBundle.getString("WoodenPlank_name");
        cardDescription = stringsBundle.getString("WoodenPlank_description");

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
                .displayMessage(String.format(stringsBundle.getString("WoodenPlank_smallDescription"), owner));
        board.addPlank();
        super.useCard(player1, player2, player3, action);
    }

}
