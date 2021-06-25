package back.cards.events;

import java.util.ResourceBundle;

public class Barter extends AEvent {
    public Barter(ResourceBundle stringBundle) {
        super(stringBundle);
        eventName = stringBundle.getString("barterName");
    }
}
