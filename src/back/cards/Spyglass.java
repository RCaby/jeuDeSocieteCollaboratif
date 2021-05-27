package back.cards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import back.ActionType;
import back.Board;
import back.Player;

/**
 * The {@code Spyglass} class represents the Spyglass Card.
 * 
 * <p>
 * The card Spyglass allows one player to see the cards of the other players. It
 * is a single-use card, discarded after utilisation.
 * 
 * <p>
 * The class {@code Spyglass} extends the abstract class {@link Card}.
 * 
 */
public class Spyglass extends Card {
    private static final long serialVersionUID = -4985929183848934161L;
    public static final int NUMBER_THIS_IN_DECK = 1;

    /**
     * Generates a new {@code Spyglass} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public Spyglass(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        cardName = stringsBundle.getString("Spyglass_name");
        cardDescription = stringsBundle.getString("Spyglass_description");
        cardImpactOnOpinion = NEGATIVE_IMPACT;
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

        Map<Player, List<Card>> cardsMap = new HashMap<>();
        for (Player player : board.getPlayerList()) {
            if (!player.equals(owner)) {
                List<Card> cardsOfPlayer = new ArrayList<>();
                for (var index = 0; index < player.getCardNumber(); index++) {

                    cardsOfPlayer.add(player.getCard(index));
                }
                cardsMap.put(player, cardsOfPlayer);
            }

        }
        board.setSpyglassMap(cardsMap);
        board.showSpyglassMap(owner);
        board.getMainBoardFront().displayMessage(String.format(stringsBundle.getString("NoTarget"), owner, this));
        board.getMainBoardFront()
                .displayMessage(String.format(stringsBundle.getString("Spyglass_smallDescription"), owner));
        super.useCard(player1, player2, player3, action);
    }

}
