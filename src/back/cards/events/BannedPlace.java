package back.cards.events;

import java.util.ResourceBundle;

public class BannedPlace extends AEvent {
    public BannedPlace(ResourceBundle stringBundle) {
        super(stringBundle);
        eventName = stringBundle.getString("bannedPlaceName");
    }
}
