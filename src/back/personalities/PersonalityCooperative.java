package back.personalities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import back.ActionType;
import back.Player;
import back.cards.Card;

/**
 * The cooperative personality is supposed to be less independent than the
 * other. Cooperative players tend to pick less cards, to use their cards in a
 * more cooperative way.
 */
public class PersonalityCooperative extends BasicPersonality {

    /**
     * Builds a cooperative personality.
     * 
     * @param stringBundle      the resource bundle used to store strings used by
     *                          the class
     * @param player            the player linked to this personality
     * @param publicPersonality a boolean indicating whether this personality should
     *                          be known by other players
     */
    public PersonalityCooperative(ResourceBundle stringBundle, Player player, boolean publicPersonality) {
        super(stringBundle, player, publicPersonality);
    }

    /**
     * {@inheritDoc}
     * <p>
     * An cooperative player will more likely get the lacking resource than any
     * other personality.
     */
    @Override
    public ActionType chooseAction(int food, int water, int wood, int weather, int nbAlive) {
        var pickedInt = random.nextInt(4);
        if (pickedInt == 0) {
            return ActionType.CARD;
        } else {
            return getLackingResource(food, water, wood, weather, nbAlive);
        }
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
     * The cooperative player will more likely to try to get between 0 and 3
     * (included) wooden plank fragments.
     */
    @Override
    public int getNbWoodTries() {
        var pickedInt = random.nextInt(8) + 1;
        if (pickedInt <= 3) {
            return pickedInt;
        } else {
            return random.nextInt(5) + 1;
        }
    }

    @Override
    public PersonalitiesEnum getLinkedPersonality() {
        return PersonalitiesEnum.COOPERATIVE_PERSONALITIES;
    }

    @Override
    public String toString() {
        return "Cooperative";
    }

    @Override
    public int getLinkedStartingBonus() {
        return IPersonality.COOPERATIVE_STARTING_BONUS;
    }

    /**
     * {@inheritDoc}
     * <p>
     * The least liked players are chosen.
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
     * This personality can become an aggressive or a mad personality.
     */
    @Override
    public boolean updatePersonality() {
        if (linkedPlayer.getOpinionOn(linkedPlayer) < -PERSONALITY_CHANGE_VALUE) {

            if (random.nextInt(4) != 0) {
                linkedPlayer.setPersonality(
                        PersonalitiesEnum.getAggressivePersonality(stringsBundle, linkedPlayer, isPersonalityPublic()));
                return true;
            } else {
                linkedPlayer.setPersonality(
                        PersonalitiesEnum.getMadPersonality(stringsBundle, linkedPlayer, isPersonalityPublic()));
                return true;
            }
        }
        return false;
    }

    protected class CompareCardType implements Comparator<Card> {

        @Override
        public int compare(Card o1, Card o2) {
            return o1.getCardType().getCooperativeValuePriorityOrder()
                    - o2.getCardType().getCooperativeValuePriorityOrder();

        }

    }
}
