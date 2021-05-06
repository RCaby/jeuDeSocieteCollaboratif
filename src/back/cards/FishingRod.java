package back.cards;

import java.util.ResourceBundle;

import back.Board;

/**
 * The {@code FishingRod} class represents the Fishing Rod Card.
 * 
 * <p>
 * The card Fishing Rod allows its owner to get twice as many food rations. It
 * is not a single-use card, discarded after the death of its owner.
 * 
 * <p>
 * The class {@code FishingRod} extends the abstract class {@link Card}.
 * 
 */
public class FishingRod extends Card {
    private static final long serialVersionUID = -2072913005557800074L;
    public static final int NUMBER_THIS_IN_DECK = 1;

    /**
     * Generates a new {@code FishingRod} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public FishingRod(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        isSingleUse = false;
        discardOnDeath = true;
        cardName = stringsBundle.getString("FishingRod_name");
        cardDescription = stringsBundle.getString("FishingRod_description");
    }

    /**
     * Indicates whether the card can be played. This card can be played only to
     * reveal it to the other player.
     */
    @Override
    public boolean canBeUsed() {
        return !isRevealed;
    }

}
