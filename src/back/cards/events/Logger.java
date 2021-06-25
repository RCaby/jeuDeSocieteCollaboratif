package back.cards.events;

import java.util.ResourceBundle;

public class Logger extends AEvent {
    public Logger(ResourceBundle stringBundle) {
        super(stringBundle);
        eventName = stringBundle.getString("loggerName");
    }
}
