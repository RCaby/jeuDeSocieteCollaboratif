package back.cards.characters;

import java.util.ResourceBundle;

public class Magician extends ACharacter {
    public Magician(ResourceBundle stringBundle) {
        super(stringBundle);
        characterName = stringBundle.getString("magicianName");
        linkedCharacterEnum = CharacterEnum.MAGICIAN;
    }
}
