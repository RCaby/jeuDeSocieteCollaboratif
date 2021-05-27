package back.personalities;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import back.ActionType;
import back.Player;
import back.cards.Card;

public abstract class BasicPersonality implements IPersonality, Serializable {

    protected transient ResourceBundle stringsBundle;
    protected Random random;
    protected Player linkedPlayer;

    protected BasicPersonality(ResourceBundle stringBundle, Player linkedPlayer) {
        this.stringsBundle = stringBundle;
        this.linkedPlayer = linkedPlayer;
        this.random = new Random();
    }

    @Override
    public Card wouldLikePlayACard() {
        var pickedInt = random.nextInt(2);
        Card pickedCard = null;
        if (pickedInt % 2 == 0 && linkedPlayer.canUseCard()) {
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
        var pickedIndex = random.nextInt(pickablePlayers.size());
        if (pickablePlayers.size() > 1) {
            while (pickablePlayers.get(pickedIndex).equals(linkedPlayer)) {
                pickedIndex = random.nextInt(pickablePlayers.size());
            }
        }

        return pickablePlayers.get(pickedIndex);
    }

    @Override
    public Player chooseAsChief(List<Player> pickablePlayers) {
        var pickedIndex = random.nextInt(pickablePlayers.size());
        if (pickablePlayers.size() > 1) {
            while (pickablePlayers.get(pickedIndex).equals(linkedPlayer)) {
                pickedIndex = random.nextInt(pickablePlayers.size());
            }
        }

        return pickablePlayers.get(pickedIndex);
    }

    @Override
    public ActionType chooseAction() {
        return ActionType.getRandomActionType();
    }

    @Override
    public Player[] chooseThreeTargets(List<Player> playerList) {
        return chooseTargetForSleepingPills(playerList);
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
    public PersonalitiesEnum getLinkedPersonality() {
        return null;
    }

    @Override
    public ActionType chooseActionForPendulum() {
        return ActionType.getRandomActionType();
    }

    protected Player chooseTargetForGun(List<Player> playerList) {
        return playerList.get(random.nextInt(playerList.size()));
    }

    protected Player chooseTargetForVoodooDoll(List<Player> playerList) {
        return playerList.get(random.nextInt(playerList.size()));
    }

    protected Player chooseTargetForAntivenom(List<Player> playerList) {
        return playerList.get(random.nextInt(playerList.size()));
    }

    protected Player[] chooseTargetForSleepingPills(List<Player> playerList) {
        var pickedPlayers = new Player[3];

        for (var index = 0; index < 3; index++) {
            var pickedIndex = random.nextInt(playerList.size());
            pickedPlayers[index] = playerList.get(pickedIndex);
        }
        return pickedPlayers;
    }

    protected Player chooseTargetForAlarmClock(List<Player> playerList) {
        return playerList.get(random.nextInt(playerList.size()));
    }

    protected Player chooseTargetForPendulum(List<Player> playerList) {
        return playerList.get(random.nextInt(playerList.size()));
    }

    @Override
    public String sayHello() {
        return "Hello, I'm Basic";
    }
}
