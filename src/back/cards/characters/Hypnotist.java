package back.cards.characters;

import java.util.ResourceBundle;

public class Hypnotist extends ACharacter {
    public Hypnotist(ResourceBundle stringBundle) {
        super(stringBundle);
        characterName = stringBundle.getString("hypnotistName");
        linkedCharacterEnum = CharacterEnum.HYPNOTIST;
    }
}
