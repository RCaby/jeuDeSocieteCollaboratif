package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.Test;

import back.Board;
import back.Player;
import back.PlayerState;
import back.cards.Axe;
import back.cards.Card;

public class PlayerTest {
    private Locale locale = new Locale("en", "US");
    private ResourceBundle stringsBundle = ResourceBundle.getBundle("Strings", locale);

    @Test
    public void buildDisplayTest() {
        Player player = new Player("Nom", stringsBundle);
        assertEquals(player.getPanelDisplay().getComponentCount(), 5);
        assertEquals(((JLabel) player.getPanelDisplay().getComponent(0)).getText(), "Nom");
        assertEquals(PlayerState.HEALTHY.toString(), ((JLabel) player.getPanelDisplay().getComponent(1)).getText());
        assertEquals(((JLabel) player.getPanelDisplay().getComponent(2)).getText(), "     ");

    }

    @Test
    public void addCardToInventoryTest() {
        Board board = new Board(7, "Name", true);
        Card cardRevealed = new Axe(board, stringsBundle);
        cardRevealed.setCardRevealed(true);
        Card cardHidden = new Axe(board, stringsBundle);
        cardHidden.setCardRevealed(false);
        Player player = new Player("Nom", stringsBundle);
        player.addCardToInventory(cardRevealed);
        assertEquals(player.getCard(0), cardRevealed);
        assertEquals(((JLabel) ((JPanel) player.getPanelDisplay().getComponent(3)).getComponent(0)).getText(), "Axe");
        player.addCardToInventory(cardHidden);
        assertEquals(player.getCard(1), cardHidden);
        assertEquals(((JLabel) ((JPanel) player.getPanelDisplay().getComponent(4)).getComponent(0)).getText(),
                "Card !");
    }

    @Test
    public void removeCardTest() {
        Board board = new Board(7, "Name", true);
        Card cardRevealed = new Axe(board, stringsBundle);
        cardRevealed.setCardRevealed(true);
        Card cardHidden = new Axe(board, stringsBundle);
        cardHidden.setCardRevealed(false);
        Player player = new Player("Nom", stringsBundle);
        player.addCardToInventory(cardRevealed);
        player.addCardToInventory(cardHidden);
        player.removeCard(0);
        assertEquals(player.getCardNumber(), 1);
        assertEquals(((JPanel) player.getPanelDisplay().getComponent(3)).getComponentCount(), 0);
        assertEquals(((JPanel) player.getPanelDisplay().getComponent(4)).getComponentCount(), 1);

    }

    @Test
    public void setPlayerChiefTest() {
        Player player = new Player("Nom", stringsBundle);
        assertEquals(((JLabel) player.getPanelDisplay().getComponent(2)).getText(), "     ");
        player.setPlayerChief(true);
        assertEquals(((JLabel) player.getPanelDisplay().getComponent(2)).getText(), "Chief");
    }

    @Test
    public void setStateTest() {
        Player player = new Player("Nom", stringsBundle);
        assertEquals(PlayerState.HEALTHY.toString(), ((JLabel) player.getPanelDisplay().getComponent(1)).getText());
        player.setState(PlayerState.SICK);
        assertEquals(PlayerState.SICK.toString(), ((JLabel) player.getPanelDisplay().getComponent(1)).getText());
    }

    @Test
    public void playAsCPUFoodTest() {
        Board board = new Board(6, "name", true);
        Player player = board.getPlayerList().get(3);
        int foodBefore = board.getFoodRations();
        player.playAsCPUFood(board);
        int foodAfter = board.getFoodRations();
        int deltaFood = foodAfter - foodBefore;
        assertTrue(1 <= deltaFood && deltaFood <= 3);
    }

    @Test
    public void playAsCPUWaterTest() {
        Board board = new Board(6, "name", true);
        Player player = board.getPlayerList().get(3);
        int waterBefore = board.getWaterRations();
        player.playAsCPUWater(board);
        int waterAfter = board.getWaterRations();
        int deltaWater = waterAfter - waterBefore;
        assertTrue(deltaWater == Math.abs(board.getWeather()));
    }

    @Test
    public void playAsCPUWoodTest() {
        Board board = new Board(6, "name", true);
        Player player = board.getPlayerList().get(3);
        int planksFragmentBefore = board.getWoodPlankFragments();
        int planksBefore = board.getWoodPlank();
        player.playAsCPUWood(board);
        int planksFragmentAfter = board.getWoodPlankFragments();
        int planksAfter = board.getWoodPlank();
        int deltaPlanks = planksAfter - planksBefore;
        int deltaPlankFragments = planksFragmentAfter - planksFragmentBefore;

        boolean sickFlag = player.getState() == PlayerState.SICK && deltaPlankFragments == deltaPlanks;
        boolean healthyFlag = player.getState() == PlayerState.HEALTHY && (deltaPlankFragments > 0 || deltaPlanks > 0);
        assertTrue(sickFlag || healthyFlag);
    }

    @Test
    public void playAsCPUCard() {
        Board board = new Board(6, "name", true);
        Player player = board.getPlayerList().get(3);
        int nbCardBefore = player.getCardNumber();
        Card card = player.playAsCPUCard(board);
        int nbCardAfter = player.getCardNumber();
        assertTrue(nbCardAfter - nbCardBefore == 1);
        assertTrue(player.hasCard(card));
    }
}
