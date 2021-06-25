package back.cards.characters;

import java.util.ResourceBundle;

public class Salesman extends ACharacter {
    public Salesman(ResourceBundle stringBundle) {
        super(stringBundle);
        characterName = stringBundle.getString("salesmanName");
    }
}
