package back.cards;

import back.ActionType;
import back.Player;

import java.awt.event.ActionListener;

import javax.swing.Icon;

public interface ICard {

    /**
     * Simulates the utilization of the card.
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
    public void useCard(Player player1, Player player2, Player player3, ActionType action);

    /**
     * The getter for the attribute {@link Card#cardImpactOnOpinion}.
     * 
     * @return the impact of this card on the opinion of the other players.
     */
    public int getCardImpactOnOpinion();

    /**
     * The getter for the attribute {@link Card#cardImpactOnOpinionOnTarget}.
     * 
     * @return the impact of this card on the opinion of the other players if they
     *         are targeted .
     */
    public int getCardImpactOnOpinionOnTarget();

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
    public void setCardRevealed(boolean cardRevealed);

    /**
     * Discards this card.
     * 
     * <p>
     * Discarding a card means that the card has no longer a owner and cannot be
     * picked in the deck, as the card goes to the discard deck.
     */
    public void discard();

    /**
     * Returns the appropriate action listener to allow the non computer player to
     * use this card.
     * 
     * @return the appropriate action listener
     */
    public ActionListener getActionListener();

    /**
     * Indicates if the owner can use the card.
     * 
     * @return a boolean value that indicates if the card can be used at the current
     *         state of the game. The default value is true because most of cards
     *         can be used at any time
     */
    public boolean canBeUsed();

    /**
     * The getter for the attribute {@link Card#revealedCardIcon}.
     * 
     * @return the icon of the revealed card
     */
    public Icon getRevealedCardIcon();

    /**
     * The getter for the attribute {@link Card#hiddenCardIcon}.
     * 
     * @return the icon of the hidden card
     */
    public Icon getHiddenCardIcon();

    /**
     * The getter for the attribute {@link Card#isFromExpansion}.
     * 
     * @return a boolean indicating whether the card is from the expansion
     */
    public boolean isFromExpansion();

    /**
     * Indicates which parameters are needed for the {@link Card#useCard} method.
     * 
     * <p>
     * If the boolean of index {@code i} is {@code true} then it is needed. For
     * instance the {@link Antivenom#getNeededParameters} method would return
     * {@code (true, false, false, false)} while the
     * {@link Pendulum#getNeededParameters} method would return {@code (true, false,
     * false, true)}.
     * 
     * @return an array of booleans which indicates the needed parameters of the
     *         useCard method
     */
    public boolean[] getNeededParameters();

    /**
     * The setter for the attribute {@link Card#owner}.
     * 
     * @param player the players that owns this card
     */
    public void setOwner(Player player);

    /**
     * The getter for the attribute {@link Card#owner}.
     * 
     * @return the player that owns this card
     */
    public Player getOwner();

    /**
     * The getter for the attribute {@link Card#isRevealed}.
     * 
     * @return a boolean indicating whether the card is revealed to every player
     */
    public boolean isCardRevealed();

    /**
     * The getter for the attribute {@link Card#cardDescription}.
     * 
     * @return the description of this card.
     */
    public String getCardDescription();

    /**
     * The getter for the attribute {@link Card#cardType}.
     * 
     * @return the type of the card
     */
    public CardType getCardType();

    /**
     * The getter for the attribute {@link Card#cardName}.
     * 
     * @return the name of the card
     */
    public String getCardName();

    /**
     * The getter for the attribute {@link Card#discardOnDeath}.
     * 
     * @return a boolean indicating whether this card should be discarded on death
     */
    public boolean discardOnDeath();

    public String toString();
}
