package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import back.Board;
import back.Player;
import back.cards.Axe;
import back.cards.Card;
import back.cards.WaterBottle;

public class CardTest {
    @Test
    public void setCardRevealedTest() {
        Board board = new Board(6, "name", true);
        Card card = new Axe(board);
        card.setCardRevealed(true);
        assertEquals(true, card.isCardRevealed());
        card.setCardRevealed(false);
        assertEquals(false, card.isCardRevealed());
    }

    @Test
    public void useCardTest() {
        Board board = new Board(6, "name", true);
        Card card = new WaterBottle(board);
        Player player = board.getPlayerList().get(0);
        player.addCardToInventory(card);
        int nbCard = player.getCardNumber();
        player.getCard(nbCard - 1).useCard(null, null, null, "");
        assertEquals(player.getCardNumber(), nbCard - 1);
    }
}
