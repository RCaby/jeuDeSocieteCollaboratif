package back.cards.events;

import java.util.ResourceBundle;

public class MarketDay extends AEvent {
    public MarketDay(ResourceBundle stringBundle) {
        super(stringBundle);
        eventName = stringBundle.getString("marketDayName");
    }
}
