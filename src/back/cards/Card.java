package back.cards;

import java.io.Serializable;
import java.util.ResourceBundle;

import back.ActionType;
import back.Board;
import back.Player;

import java.awt.event.ActionListener;

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
    public static final int IMPACT_ALARM_CLOCK = 0;
    public static final int IMPACT_ALARM_CLOCK_ON_TARGET = 1;
    public static final int IMPACT_ANTIVENOM = 1;
    public static final int IMPACT_ANTIVENOM_ON_TARGET = 2;
    public static final int IMPACT_AXE = 5;
    public static final int IMPACT_BAROMETER = 0;
    public static final int IMPACT_BOARD_GAME_QUORIDOR = 0;
    public static final int IMPACT_CARTRIDGE = -1;
    public static final int IMPACT_CLUB = -5;
    public static final int IMPACT_COCONUT = 3;
    public static final int IMPACT_COFFEE = 0;
    public static final int IMPACT_CONCH = -1;
    public static final int IMPACT_CRYSTAL_BALL = -3;
    public static final int IMPACT_FISHINGROD = 5;
    public static final int IMPACT_FLASHLIGHT = 0;
    public static final int IMPACT_GIFTBASKET = 3;
    public static final int IMPACT_GOURD = 5;
    public static final int IMPACT_GUN = -5;
    public static final int IMPACT_GUN_ON_TARGET = -3;
    public static final int IMPACT_KIT_BBQ_CANNIBAL = 3;
    public static final int IMPACT_LUXURY_CAR_KEY = 0;
    public static final int IMPACT_MATCHES = 1;
    public static final int IMPACT_METAL_SHEET = 1;
    public static final int IMPACT_OLD_BRIEF = 0;
    public static final int IMPACT_PENDULUM = -1;
    public static final int IMPACT_PENDULUM_ON_TARGET = -3;
    public static final int IMPACT_ROTTEN_FISH = 3;
    public static final int IMPACT_SANDWICH = 1;
    public static final int IMPACT_SARDINES = 3;
    public static final int IMPACT_SLEEPING_PILLS = -3;
    public static final int IMPACT_SLEEPING_PILLS_ON_TARGET = -3;
    public static final int IMPACT_SPYGLASS = 0;
    public static final int IMPACT_STAGNANT_WATER = 3;
    public static final int IMPACT_TOILET_BRUSH = 0;
    public static final int IMPACT_VEGETABLE_MILL = 3;
    public static final int IMPACT_VOODOO_DOLL = 3;
    public static final int IMPACT_VOODOO_DOLL_ON_TARGET = 5;
    public static final int IMPACT_WATER_BOTTLE = 1;
    public static final int IMPACT_WINNING_LOTTERY_TICKET = 0;
    public static final int IMPACT_WOODEN_PLANK = 3;

    boolean isRevealed;
    Player owner;
    String cardName;
    String cardDescription;
    protected boolean isSingleUse;
    protected boolean discardOnDeath;
    protected Board board;
    protected transient ResourceBundle stringsBundle;
    protected CardType cardType;

    /**
     * Generates a new card and initialize some attributes.
     * <p>
     * <ul>
     * <li>isRevealed indicates whether the card is revealed
     * <li>owner is the {@link Player} that has the card, in most cases the owner
     * decides when the card is played and who is the target
     * <li>isSingleUse indicates whether the card is discarded after its utilization
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
    public void useCard(Player player1, Player player2, Player player3, ActionType action) {
        board.getCardsPlayedThisRound().add(this);
        for (Player player : board.getPlayerList()) {
            player.addOpinionOn(owner, getCardImpactOnOpinion(), board.getDifficulty(), board.getMainBoardFront());
        }
        if (!isRevealed) {
            setCardRevealed(true);
        }
        if (isSingleUse && owner != null) {
            owner.discardCard(this);

        }
    }

    /**
     * The getter for the attribute {@link Card#cardImpactOnOpinion}.
     * 
     * @return the impact of this card on the opinion of the other players.
     */
    public int getCardImpactOnOpinion() {
        return IMPACT_LUXURY_CAR_KEY;
    }

    /**
     * The getter for the attribute {@link Card#cardImpactOnOpinionOnTarget}.
     * 
     * @return the impact of this card on the opinion of the other players if they
     *         are targeted .
     */
    public int getCardImpactOnOpinionOnTarget() {
        return IMPACT_LUXURY_CAR_KEY;
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
        setCardRevealed(false);
        board.getDiscardDeck().add(this);
    }

    /**
     * Returns the appropriate action listener to allow the non computer player to
     * use this card.
     * 
     * @return the appropriate action listener
     */
    public ActionListener getActionListener() {
        return board.getMainBoardFront().new CardPlayerActionListener(this);
    }

    /**
     * Indicates if the owner can use the card.
     * 
     * @return a boolean value that indicates if the card can be used at the current
     *         state of the game. The default value is true because most of cards
     *         can be used at any time
     */
    public boolean canBeUsed() {
        return true;
    }

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
    public boolean[] getNeededParameters() {
        return new boolean[] { false, false, false, false };
    }

    /**
     * The setter for the attribute {@link Card#owner}.
     * 
     * @param player the players that owns this card
     */
    public void setOwner(Player player) {
        owner = player;
    }

    /**
     * The getter for the attribute {@link Card#owner}.
     * 
     * @return the player that owns this card
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * The getter for the attribute {@link Card#isRevealed}.
     * 
     * @return a boolean indicating whether the card is revealed to every player
     */
    public boolean isCardRevealed() {
        return isRevealed;
    }

    /**
     * The getter for the attribute {@link Card#cardDescription}.
     * 
     * @return the description of this card.
     */
    public String getCardDescription() {
        return cardDescription;
    }

    /**
     * The getter for the attribute {@link Card#cardType}.
     * 
     * @return the type of the card
     */
    public CardType getCardType() {
        return cardType;
    }

    /**
     * The getter for the attribute {@link Card#cardName}.
     * 
     * @return the name of the card
     */
    public String getCardName() {
        return cardName;
    }

    /**
     * The getter for the attribute {@link Card#discardOnDeath}.
     * 
     * @return a boolean indicating whether this card should be discarded on death
     */
    public boolean discardOnDeath() {
        return discardOnDeath;
    }

    public String toString() {
        return cardName;
    }
}
