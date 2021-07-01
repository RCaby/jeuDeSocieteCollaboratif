package back.cards.characters;

import java.util.ResourceBundle;

public class Strapping extends ACharacter {
    public Strapping(ResourceBundle stringBundle) {
        super(stringBundle);
        characterName = stringBundle.getString("strappingName");
        linkedCharacterEnum = CharacterEnum.STRAPPING;
        nbActionPerRound = 2;
        waterConsumptionPerRound = 2;
        foodConsumptionPerRound = 2;
    }
}
