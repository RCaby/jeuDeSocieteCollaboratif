package back.cards.characters;

import java.util.ResourceBundle;

public class Dowser extends ACharacter {

    public Dowser(ResourceBundle stringBundle) {
        super(stringBundle);
        characterName = stringBundle.getString("dowserName");
    }
}
