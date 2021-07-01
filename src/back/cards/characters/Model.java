package back.cards.characters;

import java.util.ResourceBundle;

public class Model extends ACharacter {
    public Model(ResourceBundle stringBundle) {
        super(stringBundle);
        characterName = stringBundle.getString("modelName");
        linkedCharacterEnum = CharacterEnum.MODEL;
        foodConsumptionPerRound = 0;
        foodConsumptionOnRaft = 0;
    }
}
