package back.cards.events;

import java.util.ResourceBundle;

public class RainMaking extends AEvent {
    public RainMaking(ResourceBundle stringBundle) {
        super(stringBundle);
        eventName = stringBundle.getString("rainMakingName");
    }
}
