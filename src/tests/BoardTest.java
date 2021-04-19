package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.Test;

import back.Board;
import back.Player;
import back.cards.Axe;
import back.cards.Card;

public class BoardTest {

    private Random random = new Random();

    @Test
    public void giveCardToPlayerTest() {
        Board board = new Board(7, "nom", true);
        Player player = board.getPlayerList().get(3);
        Axe axe = new Axe();
        board.giveCardToPlayer(player, axe);
        assertEquals((Axe) player.getCard(4), axe);
    }

    @Test
    public void switchToNextRoundTest() {

        Board board = new Board(7, "nom", true);
        int size = board.getPlayerList().size();
        int previousIndexOfThisPlayer = board.getIndexThisPlayer();
        Player player = board.getPlayerList().get(0);
        board.switchToNextRound(true);
        assertEquals(size, board.getPlayerList().size());
        assertEquals(player, board.getPlayerList().get(board.getPlayerList().size() - 1));
        assertEquals(0, board.getIndexCurrentPlayer());
        boolean indexNot0Case = board.getIndexThisPlayer() == previousIndexOfThisPlayer - 1;
        boolean index0Case = board.getIndexThisPlayer() == (board.getPlayerList().size() - 1)
                && previousIndexOfThisPlayer == 0;
        assertTrue(indexNot0Case || index0Case);

    }

    @Test
    public void seekFoodTest() {
        Board board = new Board(6, "Nom", true);
        int food = board.seekFood();
        assertTrue(1 <= food && food <= 3);

    }

    @Test
    public void seekWaterTest() {
        Board board = new Board(6, "Nom", true);
        int water = board.seekWater();
        assertTrue(0 <= water && water <= 3);
        assertTrue(board.getWeather() == water);
    }

    @Test
    public void seekWoodTest() {
        Board board = new Board(6, "Nom", true);
        int wood = board.seekWood(6);
        assertEquals(0, wood);
        wood = board.seekWood(0);
        assertEquals(1, wood);
        for (int index = 0; index < 5000; index++) {
            int nbTries = random.nextInt(6) + 1;
            wood = board.seekWood(nbTries);
            assertTrue((wood == 0) || (wood == nbTries + 1));
        }
    }

    @Test
    public void seekCardTest() {
        Board board = new Board(6, "Nom", true);
        Card cardInDeck = board.getDeck().get(0);
        int size = board.getDeck().size();
        Card card = board.seekCard();
        assertTrue(size > board.getDeck().size());
        assertEquals(card, cardInDeck);
        size = board.getDeck().size();
        for (int index = 0; index < size; index++) {
            board.getDeck().remove(0);
        }
        assertTrue(board.getDeck().isEmpty());
        assertEquals(null, board.seekCard());
    }

    @Test
    public void discardCardTest() {
        Board board = new Board(6, "Name", true);
        Player player = board.getPlayerList().get(3);
        Card card = player.getCard(2);
        board.discardCard(player, card);
        assertTrue(card.getOwner() == null);
        assertTrue(board.getDiscardDeck().contains(card));
        assertTrue(!player.hasCard(card));
    }

    @Test
    public void nextPlayerTest() {
        Board board = new Board(5, "Name", true);
        for (int index = 0; index < 5; index++) {
            Player player = board.nextPlayer();
            if (index < 4) {
                assertEquals(board.getPlayerList().get(index + 1), player);
            } else {
                assertEquals(null, player);
            }
        }
    }

}
