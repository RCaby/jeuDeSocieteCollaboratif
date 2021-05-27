package back.personalities;

import java.util.ResourceBundle;

import back.Player;

public class PersonalityCooperative extends BasicPersonality {

    public PersonalityCooperative(ResourceBundle stringBundle, Player player, boolean publicPersonality) {
        super(stringBundle, player, publicPersonality);
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
