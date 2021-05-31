package back.personalities;

import java.util.List;
import java.util.ResourceBundle;

import back.ActionType;
import back.Player;

public class PersonalityAggressive extends BasicPersonality {

    public PersonalityAggressive(ResourceBundle stringBundle, Player player, boolean publicPersonality) {
        super(stringBundle, player, publicPersonality);
    }

    @Override
    public String sayHello() {
        return "Hello, I'm Aggresive";
    }

    @Override
    public ActionType chooseAction(int food, int water, int wood, int weather, int nbAlive) {
        if (random.nextInt(2) % 2 == 0) {
            return ActionType.CARD;
        }

        return getLackingResource(food, water, wood, weather, nbAlive);

    }

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
        return IPersonality.AGGRESIVE_STARTING_BONUS;
    }

    @Override
    protected Player[] chooseTargetForSleepingPills(List<Player> playerList) {
        Player player1 = null;
        Player player2 = null;
        Player player3 = null;
        List<Player> alivePlayers = getAlivePlayersIn(playerList);
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

    @Override
    protected Player chooseTargetForGun(List<Player> playerList) {
        return linkedPlayer.getLeastLikedPlayerIn(getAlivePlayersIn(playerList));

    }

    @Override
    protected Player chooseTargetForPendulum(List<Player> playerList) {
        return super.chooseTargetForPendulum(getAlivePlayersIn(playerList));
    }

    @Override
    protected Player chooseTargetForVoodooDoll(List<Player> playerList) {
        return super.chooseTargetForVoodooDoll(getDeadPlayersIn(playerList));
    }

    @Override
    protected Player chooseTargetForAntivenom(List<Player> playerList) {
        return super.chooseTargetForAntivenom(getSickPlayersIn(playerList));
    }

    @Override
    protected Player chooseTargetForAlarmClock(List<Player> playerList) {
        return super.chooseTargetForAlarmClock(getAlivePlayersIn(playerList));
    }

    @Override
    public boolean updatePersonality() {
        System.out.println("Personality change ? " + linkedPlayer.getOpinionOn(linkedPlayer) + "/"
                + PERSONALITY_CHANGE_VALUE + " " + linkedPlayer);
        if (linkedPlayer.getOpinionOn(linkedPlayer) > PERSONALITY_CHANGE_VALUE) {
            System.out.println("Personnality update !!");
            linkedPlayer.setPersonality(
                    PersonalitiesEnum.getCooperativePersonality(stringsBundle, linkedPlayer, isPersonalityPublic()));
            return true;
        }
        return false;
    }

}
