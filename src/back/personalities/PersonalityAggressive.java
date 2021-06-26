package back.personalities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import back.ActionType;
import back.Player;
import back.cards.items.Card;

/**
 * The aggressive personality is supposed to be more independent than the other.
 * Aggressive players tend to pick more cards, to use their cards in a more
 * individual way.
 */
public class PersonalityAggressive extends BasicPersonality {

    /**
     * Builds an aggressive personality.
     * 
     * @param stringBundle      the resource bundle used to store strings used by
     *                          the class
     * @param player            the player linked to this personality
     * @param publicPersonality a boolean indicating whether this personality should
     *                          be known by other players
     */
    public PersonalityAggressive(ResourceBundle stringBundle, Player player, boolean publicPersonality) {
        super(stringBundle, player, publicPersonality);
    }

    /**
     * {@inheritDoc}
     * <p>
     * An aggressive player will more likely get a card than other type of
     * personality.
     */
    @Override
    public ActionType chooseAction(int food, int water, int wood, int weather, int nbAlive) {
        if (random.nextInt(2) % 2 == 0) {
            return ActionType.CARD;
        }
        return getLackingResource(food, water, wood, weather, nbAlive);

    }

    @Override
    public Card wouldLikePlayACard() {
        List<Card> playerInventory = new ArrayList<>();
        for (Card card : linkedPlayer.getInventory()) {
            playerInventory.add(card);
        }

        Collections.sort(playerInventory, new CompareCardType());
        for (Card card : playerInventory) {
            if (card.canBeUsed()) {
                var probability = card.getCardType().getProbability(this, linkedPlayer.getThreatLevel());
                var pickedInt = random.nextInt(100);
                if (pickedInt < probability) {
                    return card;
                }
            }
        }

        return null;
    }

    /**
     * {@inheritDoc}
     * <p>
     * The aggressive player will more likely to try to get between 0 and 4
     * (included) wooden plank fragments.
     */
    @Override
    public int getNbWoodTries() {
        var pickedInt = random.nextInt(8) + 1;
        if (pickedInt <= 4) {
            return pickedInt;
        } else {
            return random.nextInt(5) + 1;
        }
    }

    @Override
    public PersonalitiesEnum getLinkedPersonality() {
        return PersonalitiesEnum.AGGRESSIVE_PERSONALITIES;
    }

    @Override
    public String toString() {
        return "Aggressive";
    }

    @Override
    public int getLinkedStartingBonus() {
        return IPersonality.AGGRESSIVE_STARTING_BONUS;
    }

    /**
     * {@inheritDoc}
     * <p>
     * The least appreciated players are targeted.
     */
    @Override
    protected Player[] chooseTargetForSleepingPills(List<Player> playerList) {
        Player player1 = null;
        Player player2 = null;
        Player player3 = null;
        List<Player> alivePlayers = getAlivePlayersIn(playerList);
        alivePlayers.remove(linkedPlayer);
        var nbAlive = alivePlayers.size();
        if (nbAlive >= 1) {
            player1 = linkedPlayer.getLeastLikedPlayerIn(alivePlayers);
            alivePlayers.remove(player1);
        }
        if (nbAlive >= 2) {
            player2 = linkedPlayer.getLeastLikedPlayerIn(alivePlayers);
            alivePlayers.remove(player2);
        }
        if (nbAlive >= 3) {
            player3 = linkedPlayer.getLeastLikedPlayerIn(alivePlayers);
            alivePlayers.remove(player3);
        }
        return new Player[] { player1, player2, player3 };

    }

    /**
     * {@inheritDoc}
     * <p>
     * The least liked player is chosen.
     */
    @Override
    protected Player chooseTargetForGun(List<Player> playerList) {
        List<Player> alivePlayers = getAlivePlayersIn(playerList);
        if (alivePlayers.size() == 1) {
            return linkedPlayer;
        } else {
            alivePlayers.remove(linkedPlayer);
            return linkedPlayer.getLeastLikedPlayerIn(alivePlayers);
        }

    }

    /**
     * {@inheritDoc}
     * <p>
     * The least liked player is chosen.
     */
    @Override
    protected Player chooseTargetForPendulum(List<Player> playerList) {
        return linkedPlayer.getLeastLikedPlayerIn(getAlivePlayersIn(playerList));
    }

    /**
     * {@inheritDoc}
     * <p>
     * The most liked player is chosen.
     */
    @Override
    protected Player chooseTargetForVoodooDoll(List<Player> playerList) {
        return linkedPlayer.getMostLikedPlayerIn(getDeadPlayersIn(playerList));
    }

    /**
     * {@inheritDoc}
     * <p>
     * The most liked player is chosen.
     */
    @Override
    protected Player chooseTargetForAntivenom(List<Player> playerList) {
        return linkedPlayer.getMostLikedPlayerIn(getSickPlayersIn(playerList));
    }

    /**
     * {@inheritDoc}
     * <p>
     * The most liked player is chosen.
     */
    @Override
    protected Player chooseTargetForAlarmClock(List<Player> playerList) {
        return linkedPlayer.getMostLikedPlayerIn(getAlivePlayersIn(playerList));
    }

    /**
     * {@inheritDoc}
     * <p>
     * Cards are ordered following the priority order of their type.
     */
    @Override
    public Card chooseRevealedCardToRob(Player player) {
        var minPriorityOrder = 10;
        Card selectedCard = null;
        for (Card potentialCard : player.getInventoryRevealed()) {
            if (potentialCard.getCardType().getAggressiveValuePriorityOrder() < minPriorityOrder
                    && potentialCard.isSingleUse()) {
                selectedCard = potentialCard;
                minPriorityOrder = potentialCard.getCardType().getAggressiveValuePriorityOrder();
            }

        }
        return selectedCard;
    }

    /**
     * {@inheritDoc}
     * <p>
     * If the player has to choose between two player, the least liked is chosen.
     */
    @Override
    protected Player chooseTargetForTaser(List<Player> playerList) {
        List<Player> potentialTargets = getPotentialListTargetTaser(playerList);
        var minPriorityValue = 10;
        Player target = null;
        for (Player player : potentialTargets) {
            for (Card card : player.getInventoryRevealed()) {
                var priorityValue = card.getCardType().getAggressiveValuePriorityOrder();
                if (priorityValue < minPriorityValue && card.isSingleUse()) {
                    target = player;
                    minPriorityValue = priorityValue;
                } else if (priorityValue == minPriorityValue && card.isSingleUse()) {
                    List<Player> targets = new ArrayList<>();
                    target = linkedPlayer.getLeastLikedPlayerIn(targets);
                }
            }
        }
        if (target == null) {
            System.out.println("Alerte ! Joueur nul sélectionné");
        }
        return target;
    }

    @Override
    public Card chooseBestCardIn(Card[] cardArray) {
        var minPriorityValue = 10;
        Card selectedCard = null;
        for (Card card : cardArray) {
            var value = card.getCardType().getAggressiveValuePriorityOrder();
            if (value <= minPriorityValue) {
                minPriorityValue = value;
                selectedCard = card;
            }
        }
        return selectedCard;
    }

    @Override
    public Card chooseWorstCardIn(Card[] cardArray) {
        var maxPriorityValue = 0;
        Card selectedCard = null;
        for (Card card : cardArray) {
            var value = card.getCardType().getAggressiveValuePriorityOrder();
            if (value >= maxPriorityValue) {
                maxPriorityValue = value;
                selectedCard = card;
            }
        }
        return selectedCard;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Takes the least liked card.
     */
    @Override
    public Card chooseCardToGiveRum() {
        List<Card> nonRevealedCard = new ArrayList<>();
        for (Card card : linkedPlayer.getInventory()) {
            if (!card.isCardRevealed()) {
                nonRevealedCard.add(card);
            }
        }
        Card[] usedCards;
        if (nonRevealedCard.isEmpty()) {
            usedCards = linkedPlayer.getInventory().toArray(new Card[0]);
        } else {
            usedCards = nonRevealedCard.toArray(new Card[0]);
        }
        return chooseWorstCardIn(usedCards);
    }

    /**
     * {@inheritDoc}
     * <p>
     * This personality can only become a cooperative personality.
     */
    @Override
    public boolean updatePersonality() {
        if (linkedPlayer.getOpinionOn(linkedPlayer) > PERSONALITY_CHANGE_VALUE) {
            linkedPlayer.setPersonality(
                    PersonalitiesEnum.getCooperativePersonality(stringsBundle, linkedPlayer, isPersonalityPublic()));
            return true;
        }
        return false;
    }

    protected class CompareCardType implements Comparator<Card> {

        @Override
        public int compare(Card o1, Card o2) {
            return o1.getCardType().getAggressiveValuePriorityOrder()
                    - o2.getCardType().getAggressiveValuePriorityOrder();

        }

    }

}
