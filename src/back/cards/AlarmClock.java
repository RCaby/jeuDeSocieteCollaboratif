package back.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import java.awt.event.ActionListener;
import back.Board;
import back.Player;
import back.PlayerState;

/**
 * The {@code AlarmClock} class represents the Alarm Clock Card.
 * 
 * <p>
 * The card AlarmClock makes the target player to player first in the next
 * round. It is a single-use card, discarded after utilization.
 * 
 * <p>
 * The class {@code AlarmClock} extends the abstract class {@link Card}.
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
        revealedCardIcon = new ImageIcon("src/front/images/cards/AlarmClockRevealed.png");

    }

    /**
     * Simulates the utilization of the card, inherited from {@link Card}. Needs one
     * player as a target for the action.
     * 
     * @param player1 target of the action, not null, player has to be alive
     * @param player2 not needed for this card
     * @param player3 not needed for this card
     * @param action  not needed for this card
     */
    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action, Card card) {
        if (player1 != null && player1.getState() != PlayerState.DEAD) {
            board.setNextChief(player1);
            player1.addOpinionOn(owner, getCardImpactOnOpinionOnTarget(), board.getDifficulty(),
                    board.getMainBoardFront());
            board.getMainBoardFront()
                    .displayMessage(String.format(stringsBundle.getString("OneTarget"), owner, this, player1));
            board.getMainBoardFront()
                    .displayMessage(String.format(stringsBundle.getString("AlarmClock_smallDescription"), player1));
            super.useCard(player1, player2, player3, action, card);
        }
    }

    @Override
    public boolean[] getNeededParameters() {
        return new boolean[] { true, false, false, false, false };
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

    @Override
    public List<PlayerState> getRequiredState() {
        List<PlayerState> allowedStates = new ArrayList<>();
        allowedStates.add(PlayerState.HEALTHY);
        allowedStates.add(PlayerState.SICK_FROM_SNAKE);
        allowedStates.add(PlayerState.SICK_FROM_FOOD);
        return allowedStates;
    }

}
