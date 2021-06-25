package back.cards.characters;

import java.util.ResourceBundle;

public class TimeTraveller extends ACharacter {
    public TimeTraveller(ResourceBundle stringBundle) {
        super(stringBundle);
        characterName = stringBundle.getString("timeTravellerName");
    }
}
