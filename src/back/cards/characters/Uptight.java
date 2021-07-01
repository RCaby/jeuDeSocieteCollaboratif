package back.cards.characters;

import java.util.ResourceBundle;

public class Uptight extends ACharacter {
    public Uptight(ResourceBundle stringBundle) {
        super(stringBundle);
        characterName = stringBundle.getString("uptightName");
        linkedCharacterEnum = CharacterEnum.UPTIGHT;
        cannotUseSameAction = true;
    }
}
