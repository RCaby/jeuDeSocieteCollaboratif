package back.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;

import back.ActionType;
import back.Board;
import back.Player;
import back.PlayerState;

import java.awt.event.ActionListener;

/**
 * The {@code Pendulum} class represents the Pendulum Card.
 * 
 * <p>
 * The card Pendulum imposes the next action of the target player. It is a
 * single-use card, discarded after utilization.
 * 
 * <p>
 * The class {@code Pendulum} extends the abstract class {@link Card}.
 * 
 */
public class Pendulum extends Card {
    private static final long serialVersionUID = 9023151678122174623L;
    public static final int NUMBER_THIS_IN_DECK = 1;

    /**
     * Generates a new {@code Pendulum} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public Pendulum(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        cardName = stringsBundle.getString("Pendulum_name");
        cardDescription = stringsBundle.getString("Pendulum_description");
        cardType = CardType.THREAT;
        revealedCardIcon = new ImageIcon("src/front/images/cards/PendulumRevealed.png");
    }

    /**
     * Simulates the utilization of the card, inherited from {@link Card}. Needs one
     * player as a target for the action imposition and the type of the imposed
     * action.
     * 
     * @param player1 target of the imposition, not null, player has to be alive
     * @param player2 not needed for this card
     * @param player3 not needed for this card
     * @param action  the type of the imposed action, in the enum :
     *                {@link ActionType}
     */
    @Override
    public void useCard(Player player1, Player player2, Player player3, ActionType action, Card card) {

        if (player1 != null) {
            board.getMainBoardFront()
                    .displayMessage(String.format(stringsBundle.getString("OneTarget"), owner, this, player1));
            board.getMainBoardFront().displayMessage(
                    String.format(stringsBundle.getString("Pendulum_smallDescription"), owner, action, player1));
            player1.setImposedActionThisRound(action);
            player1.addOpinionOn(owner, getCardImpactOnOpinionOnTarget(), board.getDifficulty(),
                    board.getMainBoardFront());
            super.useCard(player1, player2, player3, action, card);
        }
    }

    /**
     * Indicates whether the card can be played. This card can be played only if
     * there is at least another player alive.
     */
    @Override
    public boolean canBeUsed() {
        return board.getHealthyPlayerList().size() > 1;
    }

    @Override
    public boolean[] getNeededParameters() {
        return new boolean[] { true, false, false, true, false };
    }

    @Override
    public ActionListener getActionListener() {
        return board.getMainBoardFront().new CardPlayerActionListenerOneTargetOneAction(this);
    }

    @Override
    public int getCardImpactOnOpinion() {
        return IMPACT_PENDULUM;
    }

    @Override
    public int getCardImpactOnOpinionOnTarget() {
        return IMPACT_PENDULUM_ON_TARGET;
    }

    @Override
    public List<PlayerState> getRequiredState() {
        List<PlayerState> allowedStates = new ArrayList<>();
        allowedStates.add(PlayerState.HEALTHY);
        return allowedStates;
    }
}
