package back.cards.characters;

import java.util.ResourceBundle;

public class Sumo extends ACharacter {
    public Sumo(ResourceBundle stringBundle) {
        super(stringBundle);
        characterName = stringBundle.getString("sumoName");
        linkedCharacterEnum = CharacterEnum.SUMO;
    }
}
