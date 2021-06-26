package back.cards.characters;

import java.util.ResourceBundle;

public class SharpEye extends ACharacter {
    public SharpEye(ResourceBundle stringBundle) {
        super(stringBundle);
        characterName = stringBundle.getString("sharpEyeName");
        linkedCharacterEnum = CharacterEnum.SHARP_EYE;
    }
}
