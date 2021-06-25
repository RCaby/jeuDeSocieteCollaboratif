package back.cards.events;

import java.util.ResourceBundle;

public class Looting extends AEvent {
    public Looting(ResourceBundle stringBundle) {
        super(stringBundle);
        eventName = stringBundle.getString("lootingName");
    }
}
