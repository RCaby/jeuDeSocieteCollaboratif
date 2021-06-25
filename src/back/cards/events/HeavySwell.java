package back.cards.events;

import java.util.ResourceBundle;

public class HeavySwell extends AEvent {
    public HeavySwell(ResourceBundle stringBundle) {
        super(stringBundle);
        eventName = stringBundle.getString("heavySwellName");
    }
}
