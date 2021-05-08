package tests;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Test;

import back.Board;
import back.Player;
import back.PlayerState;
import back.cards.Card;
import back.cards.Cartridge;
import back.cards.FishingRod;
import back.cards.Gun;
import back.cards.MetalSheet;
import back.cards.Sandwich;
import back.cards.WaterBottle;

public class PlayerTest {
    private Locale locale = new Locale("en", "US");
    private ResourceBundle stringsBundle = ResourceBundle.getBundle("Strings", locale);

    @Test
    public void tradeTest() {
        Board board = new Board(5);
        Player player0 = board.getPlayerList().get(0);
        Player player1 = board.getPlayerList().get(1);
        Card waterBottle = new WaterBottle(board, stringsBundle);
        Card sandwich = new Sandwich(board, stringsBundle);
        board.giveCardToPlayer(player0, waterBottle);
        board.giveCardToPlayer(player1, sandwich);
        player1.trade(player0, waterBottle, sandwich);
        assertTrue(player0.hasCard(waterBottle) && player1.hasCard(sandwich));
        player0.trade(player1, waterBottle, sandwich);
        assertTrue(player1.hasCard(waterBottle) && player0.hasCard(sandwich));
    }

    @Test
    public void discardCardTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card waterBottle = new WaterBottle(board, stringsBundle);
        board.giveCardToPlayer(player, waterBottle);
        player.discardCard(waterBottle);
        assertNull(waterBottle.getOwner());
        assertTrue(!player.hasCard(waterBottle));

    }

    @Test
    public void canUseCardTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card waterBottle = new WaterBottle(board, stringsBundle);
        Card metalSheet = new MetalSheet(board, stringsBundle);
        Card gun = new Gun(board, stringsBundle);
        Card cartridge = new Cartridge(board, stringsBundle);
        assertTrue(!player.canUseCard());
        board.giveCardToPlayer(player, waterBottle);
        assertTrue(player.canUseCard());
        player.discardCard(waterBottle);
        board.giveCardToPlayer(player, metalSheet);
        assertTrue(!player.canUseCard());
        board.giveCardToPlayer(player, gun);
        assertTrue(!player.canUseCard());
        board.giveCardToPlayer(player, cartridge);
        assertTrue(player.canUseCard());
    }

    @Test
    public void wouldLikePlayCardTest() {// Waiting for a better wouldLikePlayCard function...
    }

    @Test
    public void deathPurgeCardsTest() {
        Board board = new Board(6);
        Player player0 = board.getPlayerList().get(0);
        Card gun = new Gun(board, stringsBundle);
        Card gun2 = new Gun(board, stringsBundle);
        Card fishingRod = new FishingRod(board, stringsBundle);
        Card bottle = new WaterBottle(board, stringsBundle);
        board.giveCardToPlayer(player0, gun);
        board.giveCardToPlayer(player0, gun2);
        board.giveCardToPlayer(player0, fishingRod);
        board.giveCardToPlayer(player0, bottle);
        player0.revealCard(gun);
        player0.revealCard(fishingRod);
        player0.revealCard(bottle);
        player0.setState(PlayerState.DEAD);
        player0.deathPurgeCards();
        assertTrue(player0.hasCard(gun));
        assertTrue(player0.hasCard(gun2));
        assertTrue(player0.hasCard(bottle));
        assertTrue(!player0.hasCard(fishingRod));
        board.distributeCardsFromDeadPlayer(player0);
        assertTrue(!player0.hasCard(gun) && !player0.hasCard(gun2) && !player0.hasCard(bottle));

    }

}
