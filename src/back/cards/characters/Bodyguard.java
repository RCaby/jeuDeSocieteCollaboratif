package back.cards.characters;

import java.util.ResourceBundle;

public class Bodyguard extends ACharacter {

    public Bodyguard(ResourceBundle stringBundle) {
        super(stringBundle);
        characterName = stringBundle.getString("bodyguardName");
    }

}
