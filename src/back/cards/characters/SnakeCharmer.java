package back.cards.characters;

import java.util.ResourceBundle;

public class SnakeCharmer extends ACharacter {
    public SnakeCharmer(ResourceBundle stringBundle) {
        super(stringBundle);
        characterName = stringBundle.getString("snakeCharmerName");
    }
}
