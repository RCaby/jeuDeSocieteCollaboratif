package back.cards;

import java.util.ResourceBundle;

import back.ActionType;
import back.Board;
import back.GamePhase;
import back.Player;

/**
 * The {@code GiftBasket} class represents the Gift Basket Card.
 * 
 * <p>
 * The card Gift Basket allows all player to survive even if there are not
 * enough rations for everyone. It is a single-use card, discarded after
 * utilisation.
 * 
 * <p>
 * The class {@code GiftBasket} extends the abstract class {@link Card}.
 * 
 */
public class GiftBasket extends Card {
    private static final long serialVersionUID = -925523317295840498L;
    public static final int NUMBER_THIS_IN_DECK = 1;

    /**
     * Generates a new {@code GiftBasket} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public GiftBasket(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        cardName = stringsBundle.getString("GiftBasket_name");
        cardDescription = stringsBundle.getString("GiftBasket_description");
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
        super.useCard(player1, player2, player3, action);
        System.out.println("Panier garni !");
        if (board.getWaterRations() < board.getNbPlayersAlive()) {
            int waterToAdd = board.getNbPlayersAlive() - board.getWaterRations();
            board.addWater(waterToAdd);
        }
        if (board.getFoodRations() < board.getNbPlayersAlive()) {
            int foodToAdd = board.getNbPlayersAlive() - board.getFoodRations();
            board.addFood(foodToAdd);
        }
    }

    /**
     * Indicates whether the card can be played. This card can be played only at the
     * distribution of the food and water rations and if there is a lack of
     * resources. Cannot be used to leave on the raft.
     */
    @Override
    public boolean canBeUsed() {
        return board.getCurrentPhase() == GamePhase.GOODS_DISTRIBUTION && (!board.isThereEnoughGoodsForAll(false));// To
                                                                                                                   // be
        // ended
    }
}
