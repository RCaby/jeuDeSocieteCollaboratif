package back.cards.characters;

import java.util.ResourceBundle;

public class Kid extends ACharacter {
    public Kid(ResourceBundle stringBundle) {
        super(stringBundle);
        characterName = stringBundle.getString("kidName");
    }
}
