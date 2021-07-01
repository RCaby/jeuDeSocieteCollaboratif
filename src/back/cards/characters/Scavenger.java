package back.cards.characters;

import java.util.ResourceBundle;

public class Scavenger extends ACharacter {
    public Scavenger(ResourceBundle stringBundle) {
        super(stringBundle);
        characterName = stringBundle.getString("scavengerName");
        linkedCharacterEnum = CharacterEnum.SCAVENGER;
        firstOnRaft = true;
    }
}
