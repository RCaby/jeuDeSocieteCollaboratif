package back.personalities;

import java.util.List;

import back.ActionType;
import back.Player;
import back.cards.Card;

/**
 * The interface describing the personality of a player.
 */
public interface IPersonality {

    public static final int COOPERATIVE_STARTING_BONUS = 10;
    public static final int AGGRESSIVE_STARTING_BONUS = -10;
    public static final int MAD_STARTING_BONUS = -50;
    public static final int NEUTRAL_STARTING_BONUS = 0;
    public static final int PERSONALITY_CHANGE_VALUE = 10;

    /**
     * Asks the player if a card should be played.
     * 
     * @return the card played by the player, can be null if no card should be
     *         played
     */
    public Card wouldLikePlayACard();

    /**
     * Asks the player to vote for a player, based on the opinion.
     * 
     * @param pickablePlayers the electable players
     * @return the chosen player
     */
    public Player choosePlayerToVoteFor(List<Player> pickablePlayers);

    /**
     * Chooses a player to be designated, based on the player's opinion.
     * 
     * @param pickablePlayers the electable players
     * @return the chosen player
     */
    public Player chooseAsChief(List<Player> pickablePlayers);

    /**
     * Asks the player to choose which action should be played based on the
     * resources of the group.
     * 
     * @param food    the number of food rations available
     * @param water   the number of water rations available
     * @param wood    the number of planks on the raft
     * @param weather the weather on the island
     * @param nbAlive the number of players alive
     * @return the action chosen
     */
    public ActionType chooseAction(int food, int water, int wood, int weather, int nbAlive);

    /**
     * Determines which is the most lacking resource between water, food and wooden
     * planks.
     * 
     * @param food    the number of food rations available
     * @param water   the number of water rations available
     * @param wood    the number of wooden planks on the raft
     * @param weather the current weather on the island
     * @param nbAlive the number of players alive
     * @return the lacking resource
     */
    public ActionType getLackingResource(int food, int water, int wood, int weather, int nbAlive);

    /**
     * Asks the player to choose a target for a card in a given list.
     * 
     * @param card       the card that will be used on the target
     * @param playerList the players who can be designated
     * @return the designated target
     */
    public Player chooseTarget(Card card, List<Player> playerList);

    /**
     * Asks the player how many plank fragment they would like to gather.
     * 
     * @return the chosen number between 0 and 6
     */
    public int getNbWoodTries();

    /**
     * Modifies the personality of the player by using their self opinion.
     * 
     * @return a boolean indicating whether the personality has changed
     */
    public boolean updatePersonality();

    /**
     * Asks the player to choose three players in a given list.
     * 
     * @param playerList the players that can be designated
     * @return chosen players
     */
    public Player[] chooseThreeTargets(List<Player> playerList);

    /**
     * Asks the player to choose an action to be imposed to another player with the
     * {@code Pendulum} card.
     * 
     * @return the chosen action
     */
    public ActionType chooseActionForPendulum();

    /**
     * The getter for the attribute {@code linkedPersonality}.
     * 
     * @return the class of this personality
     */
    public PersonalitiesEnum getLinkedPersonality();

    /**
     * The getter for the attribute {@code linkedStartingBonus}.
     * 
     * @return the opinion impact of this personality on other's opinion
     */
    public int getLinkedStartingBonus();

    /**
     * The getter for the attribute {@code isPersonalityPublic}.
     * 
     * @return a boolean indicating whether this personality can be known by the
     *         other personalities
     */
    public boolean isPersonalityPublic();

    /**
     * Chooses in a given list of card belonging to a given player, the best card to
     * rob.
     * 
     * @param player the player about to be robbed
     * @return the card to rob
     */
    public Card chooseRevealedCardToRob(Player player);

    /**
     * Chooses which card is the best to keep.
     * 
     * @param cardArray pickable cards
     * @return the chosen card
     */
    public Card chooseBestCardIn(Card[] cardArray);

    /**
     * Chooses which card is the worst to keep.
     * 
     * @param cardArray pickable cards
     * @return the chosen card
     */
    public Card chooseWorstCardIn(Card[] cardArray);

    /**
     * Chooses which card should be given during a rum card action.
     * 
     * @return the chosen card
     */
    public Card chooseCardToGiveRum();

}
