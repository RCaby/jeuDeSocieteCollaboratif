package back.cards.characters;

import java.util.ResourceBundle;

public class Kid extends ACharacter {
    public Kid(ResourceBundle stringBundle) {
        super(stringBundle);
        characterName = stringBundle.getString("kidName");
        linkedCharacterEnum = CharacterEnum.KID;
        nbPlacesTakenOnRaft = 0;
        canFleeAlone = false;
    }
}
