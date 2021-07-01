package back.cards.characters;

import java.util.ResourceBundle;

public class Naturist extends ACharacter {
    public Naturist(ResourceBundle stringBundle) {
        super(stringBundle);
        characterName = stringBundle.getString("naturistName");
        linkedCharacterEnum = CharacterEnum.NATURIST;
        cardsPermanentlyRevealed = true;
    }
}
