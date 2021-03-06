package back.personalities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import back.ActionType;
import back.Player;
import back.PlayerState;
import back.cards.Card;

/**
 * The basic personality which makes every choice randomly.
 */
public abstract class BasicPersonality implements IPersonality, Serializable {

    protected transient ResourceBundle stringsBundle;
    protected Random random;
    protected Player linkedPlayer;
    protected boolean personalityIsPublic;

    /**
     * Builds a personality linked to a determined player.
     * 
     * @param stringBundle      the resource bundle which contains the strings used
     *                          by the personality
     * @param linkedPlayer      the player linked to this personality
     * @param publicPersonality a boolean indicating if the personality is public
     */
    protected BasicPersonality(ResourceBundle stringBundle, Player linkedPlayer, boolean publicPersonality) {
        this.stringsBundle = stringBundle;
        this.linkedPlayer = linkedPlayer;
        this.random = new Random();
        this.personalityIsPublic = publicPersonality;
    }

    @Override
    public Card wouldLikePlayACard() {
        var pickedInt = random.nextInt(5);
        Card pickedCard = null;
        if (pickedInt == 0 && linkedPlayer.canUseCard()) {
            var pickedIndexCard = random.nextInt(linkedPlayer.getInventory().size());

            while (!linkedPlayer.getInventory().get(pickedIndexCard).canBeUsed()) {
                pickedIndexCard = random.nextInt(linkedPlayer.getInventory().size());
            }
            pickedCard = linkedPlayer.getInventory().get(pickedIndexCard);
        }
        return pickedCard;
    }

    @Override
    public Player choosePlayerToVoteFor(List<Player> pickablePlayers) {
        return chooseAsChief(pickablePlayers);
    }

    /**
     * {@inheritDoc}
     * <p>
     * The most important resource is water, then food, then wooden planks. Water
     * cannot be found when the weather is 0.
     */
    @Override
    public ActionType getLackingResource(int food, int water, int wood, int weather, int nbAlive) {
        if (weather != 0 && water <= food && (water < nbAlive || wood >= nbAlive)) {
            return ActionType.WATER;
        } else if (food < nbAlive || wood >= nbAlive) {
            return ActionType.FOOD;
        } else {
            return ActionType.WOOD;
        }
    }

    @Override
    public Player chooseAsChief(List<Player> pickablePlayers) {
        if (pickablePlayers.size() == 1) {
            return pickablePlayers.get(0);
        }
        return linkedPlayer.getLeastLikedPlayerIn(pickablePlayers);

    }

    @Override
    public int getNbWoodTries() {
        return random.nextInt(6) + 1;
    }

    @Override
    public ActionType chooseAction(int food, int water, int wood, int weather, int nbAlive) {
        return ActionType.getRandomActionType();
    }

    @Override
    public Player[] chooseThreeTargets(List<Player> playerList) {
        return chooseTargetForSleepingPills(playerList);
    }

    @Override
    public boolean isPersonalityPublic() {
        return personalityIsPublic;
    }

    public void setPersonalityIsPublic(boolean personalityIsPublic) {
        this.personalityIsPublic = personalityIsPublic;
    }

    @Override
    public Player chooseTarget(Card card, List<Player> playerList) {
        var cardName = card.getCardName();
        switch (cardName) {
            case "Gun":
                return chooseTargetForGun(playerList);
            case "Pendulum":
                return chooseTargetForPendulum(playerList);
            case "Voodoo Doll":
                return chooseTargetForVoodooDoll(playerList);
            case "Anti-venom":
                return chooseTargetForAntivenom(playerList);
            case "Alarm Clock":
                return chooseTargetForAlarmClock(playerList);
            default:
                return null;
        }
    }

    @Override
    public int getLinkedStartingBonus() {
        return 0;
    }

    @Override
    public PersonalitiesEnum getLinkedPersonality() {
        return null;
    }

    @Override
    public ActionType chooseActionForPendulum() {
        return ActionType.getRandomActionType();
    }

    /**
     * Asks the player to choose a target for the gun.
     * 
     * @param playerList the list of players who can be designated.
     * @return the designated player
     */
    protected Player chooseTargetForGun(List<Player> playerList) {
        return playerList.get(random.nextInt(playerList.size()));
    }

    /**
     * Asks the player to choose a target for the voodoo doll.
     * 
     * @param playerList the list of players who can be designated.
     * @return the designated player
     */
    protected Player chooseTargetForVoodooDoll(List<Player> playerList) {
        return playerList.get(random.nextInt(playerList.size()));
    }

    /**
     * Asks the player to choose a target for the anti-venom.
     * 
     * @param playerList the list of players who can be designated.
     * @return the designated player
     */
    protected Player chooseTargetForAntivenom(List<Player> playerList) {
        return playerList.get(random.nextInt(playerList.size()));
    }

    /**
     * Asks the player to choose targets for the sleeping pills.
     * 
     * @param playerList the list of players who can be designated.
     * @return the designated players
     */
    protected Player[] chooseTargetForSleepingPills(List<Player> playerList) {
        var pickedPlayers = new Player[3];

        for (var index = 0; index < 3; index++) {
            var pickedIndex = random.nextInt(playerList.size());
            pickedPlayers[index] = playerList.get(pickedIndex);
        }
        return pickedPlayers;
    }

    /**
     * Asks the player to choose a target for the alarm clock.
     * 
     * @param playerList the list of players who can be designated.
     * @return the designated player
     */
    protected Player chooseTargetForAlarmClock(List<Player> playerList) {
        return playerList.get(random.nextInt(playerList.size()));
    }

    /**
     * Asks the player to choose a target for the pendulum.
     * 
     * @param playerList the list of players who can be designated.
     * @return the designated player
     */
    protected Player chooseTargetForPendulum(List<Player> playerList) {
        return playerList.get(random.nextInt(playerList.size()));
    }

    /**
     * Extracts the dead players in a given list.
     * 
     * @param playerList the list from which dead player are extracted
     * @return a new list of dead players
     */
    protected List<Player> getDeadPlayersIn(List<Player> playerList) {
        List<Player> deadPlayers = new ArrayList<>();
        for (Player player : playerList) {
            if (player.getState() == PlayerState.DEAD) {
                deadPlayers.add(player);
            }
        }
        return deadPlayers;
    }

    /**
     * Extracts the alive players in a given list.
     * 
     * @param playerList the list from which alive players are extracted
     * @return a new list of alive players
     */
    protected List<Player> getAlivePlayersIn(List<Player> playerList) {
        List<Player> alivePlayers = new ArrayList<>();
        for (Player player : playerList) {
            if (player.getState() != PlayerState.DEAD) {
                alivePlayers.add(player);
            }
        }
        return alivePlayers;
    }

    /**
     * Extracts the sick players in a given list.
     * 
     * @param playerList the list from which sick players are extracted
     * @return a new list of sick players
     */
    protected List<Player> getSickPlayersIn(List<Player> playerList) {
        List<Player> sickPlayers = new ArrayList<>();
        for (Player player : playerList) {
            if (player.getState() == PlayerState.SICK_FROM_SNAKE) {
                sickPlayers.add(player);
            }
        }
        return sickPlayers;
    }

}
