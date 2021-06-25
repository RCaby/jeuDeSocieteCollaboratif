package back.cards.events;

import java.util.ResourceBundle;

public class Prisoner extends AEvent {
    public Prisoner(ResourceBundle stringBundle) {
        super(stringBundle);
        eventName = stringBundle.getString("prisonerName");
    }
}
