package back.cards.events;

import java.util.ResourceBundle;

public class PlaneOfTheOthers extends AEvent {
    public PlaneOfTheOthers(ResourceBundle stringBundle) {
        super(stringBundle);
        eventName = stringBundle.getString("planeOfTheOthersName");
    }
}
