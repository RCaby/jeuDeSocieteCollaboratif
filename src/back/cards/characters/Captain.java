package back.cards.characters;

import java.util.ResourceBundle;

public class Captain extends ACharacter {
    public Captain(ResourceBundle stringBundle) {
        super(stringBundle);
        characterName = stringBundle.getString("captainName");
    }
}
