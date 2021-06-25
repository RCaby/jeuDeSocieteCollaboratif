package back.cards.events;

import java.util.ResourceBundle;

public class StarvingCrocodile extends AEvent {
    public StarvingCrocodile(ResourceBundle stringBundle) {
        super(stringBundle);
        eventName = stringBundle.getString("starvingCrocodileName");
    }
}
