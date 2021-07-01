package back.cards.items;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.Icon;

import back.ActionType;
import back.Board;
import back.Player;
import back.PlayerState;

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
public abstract class Card implements ICard, Serializable {

    private static final long serialVersionUID = -3585116799691315922L;
    public static final int NUMBER_THIS_IN_DECK = 0;
    public static final int IMPACT_ALARM_CLOCK = 0;
    public static final int IMPACT_ALARM_CLOCK_ON_TARGET = 1;
    public static final int IMPACT_ALARM_CLOCK_SEE = 0;
    public static final int IMPACT_ANTIVENOM = 1;
    public static final int IMPACT_ANTIVENOM_ON_TARGET = 2;
    public static final int IMPACT_ANTIVENOM_SEE = 0;
    public static final int IMPACT_AXE = 5;
    public static final int IMPACT_AXE_SEE = 1;
    public static final int IMPACT_BAROMETER = 0;
    public static final int IMPACT_BAROMETER_SEE = 0;
    public static final int IMPACT_BOARD_GAME_QUORIDOR = 0;
    public static final int IMPACT_BOARD_GAME_QUORIDOR_SEE = 0;
    public static final int IMPACT_CARTRIDGE = -1;
    public static final int IMPACT_CARTRIDGE_SEE = -1;
    public static final int IMPACT_CLUB = -5;
    public static final int IMPACT_CLUB_SEE = -1;
    public static final int IMPACT_COCONUT = 3;
    public static final int IMPACT_COCONUT_SEE = 1;
    public static final int IMPACT_COFFEE = 0;
    public static final int IMPACT_COFFEE_SEE = 0;
    public static final int IMPACT_CONCH = -1;
    public static final int IMPACT_CONCH_SEE = 0;
    public static final int IMPACT_CRYSTAL_BALL = -3;
    public static final int IMPACT_CRYSTAL_BALL_SEE = -1;
    public static final int IMPACT_FISHINGROD = 5;
    public static final int IMPACT_FISHINGROD_SEE = 1;
    public static final int IMPACT_FLASHLIGHT = 0;
    public static final int IMPACT_FLASHLIGHT_SEE = 0;
    public static final int IMPACT_GIFTBASKET = 3;
    public static final int IMPACT_GIFTBASKET_SEE = 1;
    public static final int IMPACT_GOURD = 5;
    public static final int IMPACT_GOURD_SEE = 1;
    public static final int IMPACT_GUN = -5;
    public static final int IMPACT_GUN_SEE = -1;
    public static final int IMPACT_GUN_ON_TARGET = -3;
    public static final int IMPACT_KIT_BBQ_CANNIBAL = 3;
    public static final int IMPACT_KIT_BBQ_CANNIBAL_SEE = 1;
    public static final int IMPACT_LUXURY_CAR_KEY = 0;
    public static final int IMPACT_LUXURY_CAR_KEY_SEE = 0;
    public static final int IMPACT_MATCHES = 1;
    public static final int IMPACT_MATCHES_SEE = 0;
    public static final int IMPACT_METAL_SHEET = 1;
    public static final int IMPACT_METAL_SHEET_SEE = 0;
    public static final int IMPACT_OLD_BRIEF = 0;
    public static final int IMPACT_OLD_BRIEF_SEE = 0;
    public static final int IMPACT_PENDULUM = -1;
    public static final int IMPACT_PENDULUM_ON_TARGET = -3;
    public static final int IMPACT_PENDULUM_SEE = -1;
    public static final int IMPACT_ROTTEN_FISH = 3;
    public static final int IMPACT_ROTTEN_FISH_SEE = 1;
    public static final int IMPACT_SANDWICH = 1;
    public static final int IMPACT_SANDWICH_SEE = 0;
    public static final int IMPACT_SARDINES = 3;
    public static final int IMPACT_SARDINES_SEE = 1;
    public static final int IMPACT_SLEEPING_PILLS = -3;
    public static final int IMPACT_SLEEPING_PILLS_ON_TARGET = -3;
    public static final int IMPACT_SLEEPING_PILLS_SEE = -1;
    public static final int IMPACT_SPYGLASS = 0;
    public static final int IMPACT_SPYGLASS_SEE = 0;
    public static final int IMPACT_STAGNANT_WATER = 3;
    public static final int IMPACT_STAGNANT_WATER_SEE = 1;
    public static final int IMPACT_TOILET_BRUSH = 0;
    public static final int IMPACT_TOILET_BRUSH_SEE = 0;
    public static final int IMPACT_VEGETABLE_MILL = 3;
    public static final int IMPACT_VEGETABLE_MILL_SEE = 1;
    public static final int IMPACT_VOODOO_DOLL = 3;
    public static final int IMPACT_VOODOO_DOLL_ON_TARGET = 5;
    public static final int IMPACT_VOODOO_DOLL_SEE = 1;
    public static final int IMPACT_WATER_BOTTLE = 1;
    public static final int IMPACT_WATER_BOTTLE_SEE = 0;
    public static final int IMPACT_WINNING_LOTTERY_TICKET = 0;
    public static final int IMPACT_WINNING_LOTTERY_SEE = 0;
    public static final int IMPACT_WOODEN_PLANK = 3;
    public static final int IMPACT_WOODEN_PLANK_SEE = 1;

    public static final int IMPACT_CAT = 2;
    public static final int IMPACT_CAT_SEE = 1;
    public static final int IMPACT_FISH_BOWL = 2;
    public static final int IMPACT_FISH_BOWL_SEE = 1;
    public static final int IMPACT_WHIP = -1;
    public static final int IMPACT_WHIP_SEE = -1;
    public static final int IMPACT_CHINESE_NOODLES = 3;
    public static final int IMPACT_CHINESE_NOODLES_SEE = 1;
    public static final int IMPACT_CRATE = 1;
    public static final int IMPACT_CRATE_SEE = 0;
    public static final int IMPACT_CONCAVE_METAL_SHEET = 0;
    public static final int IMPACT_CONCAVE_METAL_SHEET_SEE = 0;
    public static final int IMPACT_EXPANDING_BULLET = -3;
    public static final int IMPACT_EXPANDING_BULLET_SEE = -1;
    public static final int IMPACT_HONE = -3;
    public static final int IMPACT_HONE_ON_TARGET = -1;
    public static final int IMPACT_HONE_SEE = -1;
    public static final int IMPACT_TASER = -1;
    public static final int IMPACT_TASER_ON_TARGET = -3;
    public static final int IMPACT_TASER_SEE = -1;
    public static final int IMPACT_BUOY = 0;
    public static final int IMPACT_BUOY_SEE = 0;
    public static final int IMPACT_RUM = 0;
    public static final int IMPACT_RUM_SEE = 0;
    public static final int IMPACT_MAGAZINE = 0;
    public static final int IMPACT_MAGAZINE_SEE = 0;
    public static final int IMPACT_METAL_DETECTOR = -1;
    public static final int IMPACT_METAL_DETECTOR_SEE = 0;

    protected boolean isRevealed;
    protected Player owner;
    protected String cardName;
    protected String cardDescription;
    protected boolean isSingleUse;
    protected boolean discardOnDeath;
    protected Board board;
    protected transient ResourceBundle stringsBundle;
    protected CardType cardType;
    protected boolean isFromExpansion;
    protected transient Icon revealedCardIcon;

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
        isFromExpansion = false;
        this.board = board;
        revealedCardIcon = null;

    }

    public void useCard(Player player1, Player player2, Player player3, ActionType action, Card card) {
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

    @Override
    public List<PlayerState> getRequiredState() {
        List<PlayerState> allowedStates = new ArrayList<>();
        allowedStates.add(PlayerState.HEALTHY);
        allowedStates.add(PlayerState.DEAD);
        allowedStates.add(PlayerState.SICK_FROM_SNAKE);
        allowedStates.add(PlayerState.SICK_FROM_FOOD);
        return allowedStates;
    }

    public int getCardImpactOnOpinion() {
        return IMPACT_LUXURY_CAR_KEY;
    }

    public int getCardImpactOnOpinionOnTarget() {
        return IMPACT_LUXURY_CAR_KEY;
    }

    public int getCardImpactOnOpinionOnSee() {
        return IMPACT_ALARM_CLOCK_SEE;
    }

    public void setCardRevealed(boolean cardRevealed) {
        isRevealed = cardRevealed;
        if (cardRevealed && owner != null) {
            owner.revealCard(this);
        }
    }

    public Icon getRevealedCardIcon() {
        return revealedCardIcon;
    }

    public void discard() {
        owner = null;
        setCardRevealed(false);
        board.getDiscardDeck().add(this);
    }

    public ActionListener getActionListener() {
        return board.getMainBoardFront().new CardPlayerActionListener(this);
    }

    public boolean canBeUsed() {
        return true;
    }

    public boolean[] getNeededParameters() {
        return new boolean[] { false, false, false, false, false };
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

    public boolean isFromExpansion() {
        return isFromExpansion;
    }

    public String getCardDescription() {
        return cardDescription;
    }

    public CardType getCardType() {
        return cardType;
    }

    public String getCardName() {
        return cardName;
    }

    public boolean discardOnDeath() {
        return discardOnDeath;
    }

    public boolean isSingleUse() {
        return isSingleUse;
    }

    public String toString() {
        return cardName;
    }
}