package back.cards.characters;

import java.util.ResourceBundle;

public class FarSighted extends ACharacter {
    public FarSighted(ResourceBundle stringBundle) {
        super(stringBundle);
        characterName = stringBundle.getString("farSightedName");
        linkedCharacterEnum = CharacterEnum.FARSIGHTED;
    }
}
