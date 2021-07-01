package back.cards.characters;

import java.util.ResourceBundle;

public class Survivalist extends ACharacter {
    public Survivalist(ResourceBundle stringBundle) {
        super(stringBundle);
        characterName = stringBundle.getString("survivalistName");
        linkedCharacterEnum = CharacterEnum.SURVIVALIST;
        waterConsumptionOnRaft = 0;
        waterConsumptionPerRound = 0;
        nbPlacesTakenOnRaft = 2;
    }
}
