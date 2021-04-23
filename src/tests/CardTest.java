package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Test;

import back.Board;
import back.Player;
import back.cards.Axe;
import back.cards.Card;
import back.cards.WaterBottle;

public class CardTest {
    private Locale locale = new Locale("en", "US");
    private ResourceBundle stringsBundle = ResourceBundle.getBundle("Strings", locale);

    @Test
    public void setCardRevealedTest() {
        Board board = new Board(6, "name", true);
        Card card = new Axe(board, stringsBundle);
        card.setCardRevealed(true);
        assertEquals(true, card.isCardRevealed());
        card.setCardRevealed(false);
        assertEquals(false, card.isCardRevealed());
    }

    @Test
    public void useCardTest() {
        Board board = new Board(6, "name", true);
        Card card = new WaterBottle(board, stringsBundle);
        Player player = board.getPlayerList().get(0);
        player.addCardToInventory(card);
        int nbCard = player.getCardNumber();
        player.getCard(nbCard - 1).useCard(null, null, null, "");
        assertEquals(player.getCardNumber(), nbCard - 1);
    }
}
