package back.cards;

import java.util.ResourceBundle;

import back.Board;

/**
 * The {@code Gourd} class represents the Groud Card.
 * 
 * <p>
 * The card Gourd allows its owner to get twice as many water rations. It is not
 * a single-use card, discarded after the death of its owner.
 * 
 * <p>
 * The class {@code Gourd} extends the abstract class {@link Card}.
 * 
 */
public class Gourd extends Card {
    private static final long serialVersionUID = -6073595887835577054L;
    public static final int NUMBER_THIS_IN_DECK = 1;

    /**
     * Generates a new {@code Gourd} card.
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    public Gourd(Board board, ResourceBundle stringsBundle) {
        super(board, stringsBundle);
        isSingleUse = false;
        discardOnDeath = true;
        cardName = stringsBundle.getString("Gourd_name");
        cardDescription = stringsBundle.getString("Gourd_description");
    }

    public String toString() {
        return cardName;
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
