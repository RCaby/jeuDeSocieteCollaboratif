package back.cards.events;

import java.util.ResourceBundle;

public class RumblingVolcano extends AEvent {
    public RumblingVolcano(ResourceBundle stringBundle) {
        super(stringBundle);
        eventName = stringBundle.getString("rumblingVolcanoName");
    }
}
