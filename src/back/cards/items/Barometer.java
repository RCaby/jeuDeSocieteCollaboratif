package back.cards.items;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import back.Board;
import back.Player;

/**
 * The {@code Barometer} class represents the Barometer Card.
 * 
 * <p>
 * The card Barometer allows its owner to see the next two weather values. It is
 * a single-use card, discarded after utilization.
 * 
 * <p>
 * The class {@code Barometer} extends the abstract class {@link Card}.
 * 
 */
public class Barometer extends Card {
    private static final long serialVersionUID = -1846990989485437371L;
    public static final int NUMBER_THIS_IN_DECK = 1;

    /**
     * Generates a new {@code Barometer} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public Barometer(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        cardName = stringsBundle.getString("Barometer_name");
        cardDescription = stringsBundle.getString("Barometer_description");
        cardType = CardType.SPY;
        revealedCardIcon = new ImageIcon("src/front/images/cards/BarometerRevealed.png");
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

        List<Integer> toBeDisplayed = new ArrayList<>();
        int n = board.getRound();
        if (n <= 9) {
            toBeDisplayed.add(board.getWeather(n + 1));
            toBeDisplayed.add(board.getWeather(n + 2));
        } else if (n == 10) {
            toBeDisplayed.add(board.getWeather(n + 1));
        }
        board.setBarometerList(toBeDisplayed);
        board.showBarometerList(owner);
        board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("NoTarget"), owner, this));
        board.getMainBoardFront()
                .displayMessage(String.format(stringsBundle.getString("Barometer_smallDescription"), owner));

        super.useCard(player1, player2, player3, action, card);
    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_BAROMETER;
    }

    @Override
    public int getCardImpactOnOpinionOnSee() {
        return IMPACT_BAROMETER_SEE;
    }
}
