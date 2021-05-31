package back.personalities;

import java.util.ResourceBundle;

import back.ActionType;
import back.Player;

public class PersonalityCooperative extends BasicPersonality {

    public PersonalityCooperative(ResourceBundle stringBundle, Player player, boolean publicPersonality) {
        super(stringBundle, player, publicPersonality);
    }

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
    public int getNbWoodTries() {
        var pickedInt = random.nextInt(8) + 1;
        if (pickedInt <= 3) {
            return pickedInt;
        } else {
            return random.nextInt(5) + 1;
        }
    }

    @Override
    public String sayHello() {
        return "Hello, I'm Cooperative";
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
}
