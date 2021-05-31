package back.personalities;

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

}
