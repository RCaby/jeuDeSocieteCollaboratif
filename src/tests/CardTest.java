package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import back.cards.Axe;
import back.cards.Card;

public class CardTest {
    @Test
    public void setCardRevealedTest() {
        Card card = new Axe();
        card.setCardRevealed(true);
        assertEquals(true, card.isCardRevealed());
        card.setCardRevealed(false);
        assertEquals(false, card.isCardRevealed());
    }
}
