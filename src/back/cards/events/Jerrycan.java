package back.cards.events;

import java.util.ResourceBundle;

public class Jerrycan extends AEvent {
    public Jerrycan(ResourceBundle stringBundle) {
        super(stringBundle);
        eventName = stringBundle.getString("jerrycanName");
    }
}
