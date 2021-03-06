package back.cards;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import back.Board;
import back.Player;

/**
 * The {@code Coffee} class represents the Coffee Card.
 * 
 * <p>
 * The card Coffee allows its owner to do two action during this round. It is a
 * single-use card, discarded after utilization.
 * 
 * <p>
 * The class {@code Coffee} extends the abstract class {@link Card}.
 * 
 */
public class Coffee extends Card {
    private static final long serialVersionUID = -8623588548777632699L;
    public static final int NUMBER_THIS_IN_DECK = 1;

    /**
     * Generates a new {@code Coffee} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public Coffee(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        cardName = stringsBundle.getString("Coffee_name");
        cardDescription = stringsBundle.getString("Coffee_description");
        cardType = CardType.THREAT;
        revealedCardIcon = new ImageIcon("src/front/images/cards/CoffeeRevealed.png");
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
        if (owner != null) {
            board.setTwicePlayingPlayer(owner);
            board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("NoTarget"), owner, this));
            board.getMainBoardFront()
                    .displayMessage(String.format(stringsBundle.getString("Coffee_smallDescription"), owner));
            super.useCard(player1, player2, player3, action);
        }

    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_COCONUT;
    }

}
