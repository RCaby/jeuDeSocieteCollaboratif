package back.cards;

import java.util.ResourceBundle;

import back.ActionType;
import java.awt.event.ActionListener;
import back.Board;
import back.Player;
import back.PlayerState;

/**
 * The {@code AlarmClock} class represents the Alarm Clock Card.
 * 
 * <p>
 * The card AlarmClock cures a sick player. It is a single-use card, discarded
 * after utilisation.
 * 
 * <p>
 * The class {@code Antivenom} extends the abstract class {@link Card}.
 * 
 */
public class AlarmClock extends Card {

    private static final long serialVersionUID = -4056999539348867224L;
    public static final int NUMBER_THIS_IN_DECK = 1;

    /**
     * Generates a new {@code AlarmClock} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public AlarmClock(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        cardName = stringsBundle.getString("AlarmClock_name");
        cardDescription = stringsBundle.getString("AlarmClock_description");
        cardType = CardType.HELP;
    }

    /**
     * Simulates the utilisation of the card, herited from {@link Card}. Needs one
     * player as a target for the action.
     * 
     * @param player1 target of the action, not null, player has to be alive
     * @param player2 not needed for this card
     * @param player3 not needed for this card
     * @param action  not needed for this card
     */
    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {
        if (player1 != null && player1.getState() != PlayerState.DEAD) {
            board.setNextChief(player1);
            player1.addOpinionOn(owner, getCardImpactOnOpinionOnTarget(), board.getDifficulty(),
                    board.getMainBoardFront());
            board.getMainBoardFront()
                    .displayMessage(String.format(stringsBundle.getString("OneTarget"), owner, this, player1));
            board.getMainBoardFront()
                    .displayMessage(String.format(stringsBundle.getString("AlarmClock_smallDescription"), player1));
            super.useCard(player1, player2, player3, action);
        }
    }

    @Override
    public boolean[] getNeededParameters() {
        return new boolean[] { true, false, false, false };
    }

    @Override
    public ActionListener getActionListener() {
        return board.getMainBoardFront().new CardPlayerActionListenerOneTarget(this);
    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_ALARM_CLOCK;
    }

    public int getCardImpactOnOpinionTarget() {
        return IMPACT_ALARM_CLOCK_ON_TARGET;
    }

}
