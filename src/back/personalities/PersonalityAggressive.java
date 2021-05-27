package back.personalities;

import java.util.ResourceBundle;

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
