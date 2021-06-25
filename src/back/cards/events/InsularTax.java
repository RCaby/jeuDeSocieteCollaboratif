package back.cards.events;

import java.util.ResourceBundle;

public class InsularTax extends AEvent {
    public InsularTax(ResourceBundle stringBundle) {
        super(stringBundle);
        eventName = stringBundle.getString("insularTaxName");
    }
}
