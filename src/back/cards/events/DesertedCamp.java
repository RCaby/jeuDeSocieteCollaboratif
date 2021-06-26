package back.cards.events;

import java.util.ResourceBundle;

public class DesertedCamp extends AEvent {
    public DesertedCamp(ResourceBundle stringBundle) {
        super(stringBundle);
        eventName = stringBundle.getString("desertedCampName");
        linkedEventEnum = EventEnum.DESERTED_CAMP;
    }
}
