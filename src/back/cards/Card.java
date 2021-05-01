package back.cards;

import java.io.Serializable;
import java.util.ResourceBundle;

import back.ActionType;
import back.Board;
import back.Player;

/**
 * The {@code Card} class represents a Card.
 * 
 * <p>
 * A card, in most cases, can be used to trigger an action and is discarded
 * after use. Some cards are permanent and can be used as long as the owner stay
 * alive. When a player dies, they card are distributed to his alive neighbors.
 * Permanent cards, except the {@link Gun} are discarded.
 * 
 * <p>
 * Some cards have to be played at determined game moment while others can be
 * played at any time. Some need parameters (for instance a target), while
 * others do not (like a card which increase the number of food rations). To be
 * used, a card has to be revealed first .
 * 
 * <p>
 * Players can trade, exchange or discard cards freely.
 * 
 * <p>
 * At the beginning of a game, a deck is made of each card type. Each card type
 * has a determined number of instance in the deck.
 */
public abstract class Card implements Serializable {

    private static final long serialVersionUID = -3585116799691315922L;
    public static final int NUMBER_THIS_IN_DECK = 0;
    boolean isRevealed;
    Player owner;
    String cardName;
    String cardDescription;
    protected boolean isSingleUse;
    protected boolean discardOnDeath;
    protected Board board;
    protected transient ResourceBundle stringsBundle;

    /**
     * Generates a new card and initialize some attributes.
     * <p>
     * <ul>
     * <li>isRevealed indicates whether the card is revealed
     * <li>owner is the {@link Player} that has the card, in most cases the owner
     * decides when the card is played and who is the target
     * <li>isSingleUse indicates whether the card is discarded after its utilisation
     * <li>discardOnDeath indicates if the card is discarded after the death of its
     * owner
     * </ul>
     * <p>
     * 
     * @param board         the main board, not null
     * @param stringsBundle the strings used by the card such as its name or its
     *                      description, not null
     */
    protected Card(Board board, ResourceBundle stringsBundle) {
        this.stringsBundle = stringsBundle;
        isRevealed = false;
        owner = null;
        isSingleUse = true;
        discardOnDeath = false;
        this.board = board;
    }

    /**
     * Simulates the utilisation of the card.
     * 
     * <p>
     * In most cases, cards are revealed, then discarded. As only some cards need
     * three targets or the description of an action, those parameters may not be
     * used in each {@code useCard} method.
     * 
     * @param player1 the first target, a {@code Player}
     * @param player2 the second target, a {@code Player}
     * @param player3 the third target, a {@code Player}
     * @param action  a {@code ActionType}
     */
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {
        board.getCardsPlayedThisRound().add(this);
        if (!isRevealed) {
            setCardRevealed(true);
        }
        if (isSingleUse) {
            setCardRevealed(false);
            if (owner != null) {
                owner.discardCard(this);
            }
        }
    }

    /**
     * Reveals this card.
     * 
     * <p>
     * Revealing a card means that any player can see that the owner has this card
     * and knows the effect of this card. The steps to reveal a card of a player are
     * :
     * <ul>
     * <li>reveal the card (some attributes are modified)
     * <li>reveal the card to the other players
     * </ul>
     * 
     * @param cardRevealed indicates whether the card has to be revealed or hidden.
     */
    public void setCardRevealed(boolean cardRevealed) {
        isRevealed = cardRevealed;
        if (cardRevealed && owner != null) {
            owner.revealCard(this);
        }
    }

    /**
     * Discards this card.
     * 
     * <p>
     * Discarding a card means that the card has no longer a owner and cannot be
     * picked in the deck, as the card goes to the discard deck.
     */
    public void discard() {
        owner = null;
        board.getDiscardDeck().add(this);
    }

    /**
     * Indicates if the owner can use the card.
     * 
     * @return a boolean value that indicates if the card can be used at the current
     *         state of the game. The default value is true because most of cards
     *         can be used at any time.
     */
    public boolean canBeUsed() {
        return true;
    }

    public void setOwner(Player player) {
        owner = player;
    }

    public Player getOwner() {
        return owner;
    }

    public boolean isCardRevealed() {
        return isRevealed;
    }

    public String getCardDescription() {
        return cardDescription;
    }

    public String getCardName() {
        return cardName;
    }

    public boolean discardOnDeath() {
        return discardOnDeath;
    }
}
