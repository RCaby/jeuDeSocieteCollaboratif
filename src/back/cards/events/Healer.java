package back.cards.events;

import java.util.ResourceBundle;

public class Healer extends AEvent {
    public Healer(ResourceBundle stringBundle) {
        super(stringBundle);
        eventName = stringBundle.getString("healerName");
    }
}
