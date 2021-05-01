package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Test;

import back.ActionType;
import back.Board;
import back.GamePhase;
import back.Player;
import back.PlayerState;
import back.cards.AlarmClock;
import back.cards.Antivenom;
import back.cards.Axe;
import back.cards.Barometer;
import back.cards.BoardGameQuoridor;
import back.cards.Card;
import back.cards.Cartridge;
import back.cards.Club;
import back.cards.Coconut;
import back.cards.Coffee;
import back.cards.Conch;
import back.cards.CrystalBall;
import back.cards.FishingRod;
import back.cards.Flashlight;
import back.cards.GiftBasket;
import back.cards.Gourd;
import back.cards.Gun;
import back.cards.KitBBQCannibal;
import back.cards.LuxuryCarKey;
import back.cards.Matches;
import back.cards.MetalSheet;
import back.cards.OldBrief;
import back.cards.Pendulum;
import back.cards.RottenFish;
import back.cards.Sandwich;
import back.cards.Sardines;
import back.cards.SleepingPills;
import back.cards.Spyglass;
import back.cards.StagnantWater;
import back.cards.ToiletBrush;
import back.cards.VegetableMill;
import back.cards.VoodooDoll;
import back.cards.WaterBottle;
import back.cards.WinningLotteryTicket;
import back.cards.WoodenPlank;

public class CardTest {
    private Locale locale = new Locale("en", "US");
    private ResourceBundle stringsBundle = ResourceBundle.getBundle("Strings", locale);

    private List<Card> cloneListCard(List<Card> listToClone) {
        List<Card> clonedList = new ArrayList<>();
        for (Card card : listToClone) {
            clonedList.add(card);
        }
        return clonedList;
    }

    @Test
    public void setCardRevealedTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card bottle = new WaterBottle(board, stringsBundle);
        board.giveCardToPlayer(player, bottle);

        bottle.setCardRevealed(true);
        assertTrue(bottle.isCardRevealed());

        bottle.setCardRevealed(false);
        assertTrue(!bottle.isCardRevealed());
    }

    @Test
    public void discardTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card bottle = new WaterBottle(board, stringsBundle);
        board.giveCardToPlayer(player, bottle);

        bottle.discard();
        assertEquals(null, bottle.getOwner());
        assertTrue(board.getDiscardDeck().contains(bottle));
    }

    // TODO Tests pour chaque type de carte

    @Test
    public void getOwnerTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card bottle = new WaterBottle(board, stringsBundle);
        board.giveCardToPlayer(player, bottle);
        assertEquals(player, bottle.getOwner());
    }

    @Test
    public void canvaTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card card = new WinningLotteryTicket(board, stringsBundle);
        board.giveCardToPlayer(player, card);
        assertTrue(card.canBeUsed());
        assertTrue(!card.discardOnDeath());
        assertEquals(stringsBundle.getString("WinningLotteryTicket_description"), card.getCardDescription());
        assertEquals(stringsBundle.getString("WinningLotteryTicket_name"), card.getCardName());
        card.useCard(null, null, null, ActionType.NONE);
        assertTrue(!player.hasCard(card));
    }

    @Test
    public void alarmClockTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card card = new AlarmClock(board, stringsBundle);
        board.giveCardToPlayer(player, card);
        assertTrue(card.canBeUsed());
        assertTrue(!card.discardOnDeath());
        assertEquals(stringsBundle.getString("AlarmClock_description"), card.getCardDescription());
        assertEquals(stringsBundle.getString("AlarmClock_name"), card.getCardName());
        card.useCard(player, null, null, ActionType.NONE);
        assertTrue(!player.hasCard(card));
        assertEquals(player, board.getNextChief());
        board.switchToNextRound();
        assertNull(board.getNextChief());
        assertEquals(player, board.getChief());

    }

    @Test
    public void antivenomTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card card = new Antivenom(board, stringsBundle);
        board.giveCardToPlayer(player, card);
        assertTrue(!card.canBeUsed());
        assertTrue(!card.discardOnDeath());
        assertEquals(stringsBundle.getString("Antivenom_description"), card.getCardDescription());
        assertEquals(stringsBundle.getString("Antivenom_name"), card.getCardName());
        Player target = board.getPlayerList().get(1);
        target.setState(PlayerState.SICK);
        assertTrue(card.canBeUsed());
        card.useCard(target, null, null, ActionType.NONE);
        assertTrue(!player.hasCard(card));
        assertEquals(PlayerState.HEALTHY, target.getState());
    }

    @Test
    public void axeTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card card = new Axe(board, stringsBundle);
        board.giveCardToPlayer(player, card);
        assertTrue(card.canBeUsed());
        assertTrue(card.discardOnDeath());
        assertEquals(stringsBundle.getString("Axe_description"), card.getCardDescription());
        assertEquals(stringsBundle.getString("Axe_name"), card.getCardName());
        card.useCard(null, null, null, ActionType.NONE);
        assertTrue(!card.canBeUsed());
        assertEquals(player, card.getOwner());
        assertTrue(player.hasCard(card));
        board.killPlayer(player);
        assertTrue(!player.hasCard(card));
        assertNull(card.getOwner());
    }

    @Test
    public void barometerTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card card1 = new Barometer(board, stringsBundle);
        board.giveCardToPlayer(player, card1);
        Card card2 = new Barometer(board, stringsBundle);
        board.giveCardToPlayer(player, card2);
        assertTrue(card1.canBeUsed());
        assertTrue(!card1.discardOnDeath());
        assertEquals(stringsBundle.getString("Barometer_description"), card1.getCardDescription());
        assertEquals(stringsBundle.getString("Barometer_name"), card1.getCardName());
        int[] weather = new int[12];
        for (int index = 0; index < 12; index++) {
            weather[index] = board.getWeather(index);
        }
        int round1 = 9;
        int round2 = 10;
        List<Integer> wantedForRound1 = new ArrayList<>();
        List<Integer> wantedForRound2 = new ArrayList<>();
        wantedForRound1.add(weather[10]);
        wantedForRound1.add(weather[11]);
        wantedForRound2.add(weather[11]);
        board.setRound(round1);
        card1.useCard(null, null, null, ActionType.NONE);
        assertEquals(wantedForRound1, board.getBarometerList());
        board.setRound(round2);
        card2.useCard(null, null, null, ActionType.NONE);
        assertEquals(wantedForRound2, board.getBarometerList());
        assertTrue(!player.hasCard(card1));
        board.setRound(0);
        board.switchToNextRound();
        assertTrue(board.getBarometerList().isEmpty());

    }

    @Test
    public void boardGameQuoridorTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card card = new BoardGameQuoridor(board, stringsBundle);
        board.giveCardToPlayer(player, card);
        assertTrue(card.canBeUsed());
        assertTrue(!card.discardOnDeath());
        assertEquals(stringsBundle.getString("BoardGameQuoridor_description"), card.getCardDescription());
        assertEquals(stringsBundle.getString("BoardGameQuoridor_name"), card.getCardName());
        card.useCard(null, null, null, ActionType.NONE);
        assertTrue(!player.hasCard(card));
    }

    @Test
    public void cartridgeTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card gun = new Gun(board, stringsBundle);
        board.giveCardToPlayer(player, gun);
        assertTrue(!gun.canBeUsed());
        Card card = new Cartridge(board, stringsBundle);
        board.giveCardToPlayer(player, card);
        assertTrue(gun.canBeUsed());
        assertTrue(!card.discardOnDeath());
        assertEquals(stringsBundle.getString("Cartridge_description"), card.getCardDescription());
        assertEquals(stringsBundle.getString("Cartridge_name"), card.getCardName());
        Player killed = board.getPlayerList().get(1);
        gun.useCard(killed, null, null, ActionType.NONE);
        assertTrue(!player.hasCard(card));
        assertEquals(PlayerState.DEAD, killed.getState());

    }

    @Test
    public void clubTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card card = new Club(board, stringsBundle);
        board.giveCardToPlayer(player, card);
        assertTrue(card.canBeUsed());
        assertTrue(card.discardOnDeath());
        assertEquals(stringsBundle.getString("Club_description"), card.getCardDescription());
        assertEquals(stringsBundle.getString("Club_name"), card.getCardName());
        card.useCard(null, null, null, ActionType.NONE);
        assertTrue(!card.canBeUsed());
        assertEquals(player, card.getOwner());
        assertTrue(player.hasCard(card));
        board.killPlayer(player);
        assertTrue(!player.hasCard(card));
        assertNull(card.getOwner());
    }

    @Test
    public void coconutTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card card = new Coconut(board, stringsBundle);
        board.giveCardToPlayer(player, card);
        assertTrue(card.canBeUsed());
        assertTrue(!card.discardOnDeath());
        assertEquals(stringsBundle.getString("Coconut_description"), card.getCardDescription());
        assertEquals(stringsBundle.getString("Coconut_name"), card.getCardName());
        int prevWaterRations = board.getWaterRations();
        card.useCard(null, null, null, ActionType.NONE);
        assertTrue(!player.hasCard(card));
        assertEquals(prevWaterRations + 3, board.getWaterRations());
    }

    @Test
    public void coffeeTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card card = new Coffee(board, stringsBundle);
        board.giveCardToPlayer(player, card);
        assertTrue(card.canBeUsed());
        assertTrue(!card.discardOnDeath());
        assertEquals(stringsBundle.getString("Coffee_description"), card.getCardDescription());
        assertEquals(stringsBundle.getString("Coffee_name"), card.getCardName());
        card.useCard(null, null, null, ActionType.NONE);
        assertTrue(!player.hasCard(card));
        assertEquals(player, board.getTwicePlayingPlayer());
        board.switchToNextRound();
        assertNull(board.getTwicePlayingPlayer());
    }

    @Test
    public void conchTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card card = new Conch(board, stringsBundle);
        board.giveCardToPlayer(player, card);
        assertTrue(card.canBeUsed());
        assertTrue(!card.discardOnDeath());
        assertEquals(stringsBundle.getString("Conch_description"), card.getCardDescription());
        assertEquals(stringsBundle.getString("Conch_name"), card.getCardName());
        card.useCard(null, null, null, ActionType.NONE);
        assertTrue(!player.hasCard(card));
        assertEquals(player, board.getConchOwner());
        board.switchToNextRound();
        assertNull(board.getConchOwner());
    }

    @Test
    public void crystalBallTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card card = new CrystalBall(board, stringsBundle);
        board.giveCardToPlayer(player, card);
        assertTrue(card.canBeUsed());
        assertTrue(card.discardOnDeath());
        assertEquals(stringsBundle.getString("CrystalBall_description"), card.getCardDescription());
        assertEquals(stringsBundle.getString("CrystalBall_name"), card.getCardName());
        card.useCard(null, null, null, ActionType.NONE);
        assertTrue(!card.canBeUsed());
        assertEquals(player, card.getOwner());
        assertTrue(player.hasCard(card));
        board.killPlayer(player);
        assertTrue(!player.hasCard(card));
        assertNull(card.getOwner());
    }

    @Test
    public void fishingRodTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card card = new FishingRod(board, stringsBundle);
        board.giveCardToPlayer(player, card);
        assertTrue(card.canBeUsed());
        assertTrue(card.discardOnDeath());
        assertEquals(stringsBundle.getString("FishingRod_description"), card.getCardDescription());
        assertEquals(stringsBundle.getString("FishingRod_name"), card.getCardName());
        card.useCard(null, null, null, ActionType.NONE);
        assertTrue(!card.canBeUsed());
        assertEquals(player, card.getOwner());
        assertTrue(player.hasCard(card));
        board.killPlayer(player);
        assertTrue(!player.hasCard(card));
        assertNull(card.getOwner());
    }

    @Test
    public void flashlightTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card card1 = new Flashlight(board, stringsBundle);
        board.giveCardToPlayer(player, card1);
        Card card2 = new Flashlight(board, stringsBundle);
        board.giveCardToPlayer(player, card2);
        Card card3 = new Flashlight(board, stringsBundle);
        board.giveCardToPlayer(player, card3);
        assertTrue(card1.canBeUsed());
        assertTrue(!card1.discardOnDeath());
        assertEquals(stringsBundle.getString("Flashlight_description"), card1.getCardDescription());
        assertEquals(stringsBundle.getString("Flashlight_name"), card1.getCardName());
        List<Card> deck1 = new ArrayList<>();
        List<Card> deck2 = new ArrayList<>();
        List<Card> deck3 = new ArrayList<>();
        deck1.add(new WaterBottle(board, stringsBundle));
        deck1.add(new WaterBottle(board, stringsBundle));
        deck1.add(new WaterBottle(board, stringsBundle));
        deck2.add(new Sandwich(board, stringsBundle));
        deck2.add(new Sandwich(board, stringsBundle));
        deck3.add(new Gun(board, stringsBundle));
        board.setDeck(deck1);
        card1.useCard(null, null, null, ActionType.NONE);
        assertEquals(deck1, board.getFlashLightList());
        board.setDeck(deck2);
        card2.useCard(null, null, null, ActionType.NONE);
        assertEquals(deck2, board.getFlashLightList());
        board.setDeck(deck3);
        card3.useCard(null, null, null, ActionType.NONE);
        assertEquals(deck3, board.getFlashLightList());
        assertTrue(!player.hasCard(card1));
        board.switchToNextRound();
        assertTrue(board.getFlashLightList().isEmpty());

    }

    @Test
    public void giftBasketTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card card = new GiftBasket(board, stringsBundle);
        board.giveCardToPlayer(player, card);
        assertTrue(!card.canBeUsed());
        board.setCurrentPhase(GamePhase.GOODS_DISTRIBUTION);
        board.setFoodRations(5);
        board.setWaterRations(5);
        assertTrue(!card.canBeUsed());
        board.setFoodRations(4);
        assertTrue(card.canBeUsed());
        board.setFoodRations(5);
        board.setWaterRations(4);
        assertTrue(card.canBeUsed());
        board.setFoodRations(0);
        board.setWaterRations(2);
        assertTrue(!card.discardOnDeath());
        assertEquals(stringsBundle.getString("GiftBasket_description"), card.getCardDescription());
        assertEquals(stringsBundle.getString("GiftBasket_name"), card.getCardName());
        card.useCard(null, null, null, ActionType.NONE);
        assertEquals(5, board.getWaterRations());
        assertEquals(5, board.getFoodRations());
        board.switchToNextRound();
        assertEquals(0, board.getWaterRations());
        assertEquals(0, board.getFoodRations());
        assertTrue(!player.hasCard(card));
    }

    @Test
    public void gourdTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card card = new Gourd(board, stringsBundle);
        board.giveCardToPlayer(player, card);
        assertTrue(card.canBeUsed());
        assertTrue(card.discardOnDeath());
        assertEquals(stringsBundle.getString("Gourd_description"), card.getCardDescription());
        assertEquals(stringsBundle.getString("Gourd_name"), card.getCardName());
        card.useCard(null, null, null, ActionType.NONE);
        assertTrue(!card.canBeUsed());
        assertEquals(player, card.getOwner());
        assertTrue(player.hasCard(card));
        board.killPlayer(player);
        assertTrue(!player.hasCard(card));
        assertNull(card.getOwner());
    }

    @Test
    public void gunTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card card = new Gun(board, stringsBundle);
        Card cardCartridge = new Cartridge(board, stringsBundle);
        Card cardCartridge2 = new Cartridge(board, stringsBundle);
        Card cardMetalSheet = new MetalSheet(board, stringsBundle);
        Player target = board.getPlayerList().get(1);
        board.giveCardToPlayer(player, card);
        board.giveCardToPlayer(target, cardMetalSheet);
        assertTrue(!card.canBeUsed());
        board.giveCardToPlayer(player, cardCartridge);
        assertTrue(card.canBeUsed());
        board.giveCardToPlayer(player, cardCartridge2);
        assertTrue(!card.discardOnDeath());
        assertEquals(stringsBundle.getString("Gun_description"), card.getCardDescription());
        assertEquals(stringsBundle.getString("Gun_name"), card.getCardName());
        card.useCard(target, null, null, ActionType.NONE);
        assertTrue(!player.hasCard(cardCartridge) || !player.hasCard(cardCartridge2));
        assertTrue(!target.hasCard(cardMetalSheet) && target.getState() == PlayerState.HEALTHY);
        card.useCard(target, null, null, ActionType.NONE);
        assertTrue(player.hasCard(card));
        assertTrue(!player.hasCard(cardCartridge));
        assertTrue(!player.hasCard(cardCartridge2));
        assertEquals(PlayerState.DEAD, target.getState());
    }

    @Test
    public void kitBBQCannibalTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card card = new KitBBQCannibal(board, stringsBundle);
        board.giveCardToPlayer(player, card);
        assertTrue(!card.canBeUsed());
        board.killPlayer(board.getPlayerList().get(1));
        board.killPlayer(board.getPlayerList().get(2));
        assertTrue(card.canBeUsed());
        assertTrue(!card.discardOnDeath());
        assertEquals(stringsBundle.getString("KitBBQCannibal_description"), card.getCardDescription());
        assertEquals(stringsBundle.getString("KitBBQCannibal_name"), card.getCardName());
        int previousFoodRations = board.getFoodRations();
        card.useCard(null, null, null, ActionType.NONE);
        assertTrue(!player.hasCard(card));
        assertEquals(previousFoodRations + 4, board.getFoodRations());
    }

    @Test
    public void luxuryCarKeyTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card card = new LuxuryCarKey(board, stringsBundle);
        board.giveCardToPlayer(player, card);
        assertTrue(card.canBeUsed());
        assertTrue(!card.discardOnDeath());
        assertEquals(stringsBundle.getString("LuxuryCarKey_description"), card.getCardDescription());
        assertEquals(stringsBundle.getString("LuxuryCarKey_name"), card.getCardName());
        card.useCard(null, null, null, ActionType.NONE);
        assertTrue(!player.hasCard(card));
    }

    @Test
    public void matchesTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card card = new Matches(board, stringsBundle);
        board.giveCardToPlayer(player, card);
        assertTrue(card.canBeUsed());
        assertTrue(!card.discardOnDeath());
        assertEquals(stringsBundle.getString("Matches_description"), card.getCardDescription());
        assertEquals(stringsBundle.getString("Matches_name"), card.getCardName());
        card.useCard(null, null, null, ActionType.NONE);
        assertTrue(!player.hasCard(card));
        assertTrue(board.getMatchesUsedThisRound());
        board.switchToNextRound();
        assertTrue(!board.getMatchesUsedThisRound());
    }

    @Test
    public void metalSheetTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card card = new MetalSheet(board, stringsBundle);
        board.giveCardToPlayer(player, card);
        assertTrue(!card.canBeUsed());
        assertTrue(!card.discardOnDeath());
        assertEquals(stringsBundle.getString("MetalSheet_description"), card.getCardDescription());
        assertEquals(stringsBundle.getString("MetalSheet_name"), card.getCardName());
        Player killer = board.getPlayerList().get(1);
        Card cartridge = new Cartridge(board, stringsBundle);
        board.giveCardToPlayer(killer, cartridge);
        Card gun = new Gun(board, stringsBundle);
        board.giveCardToPlayer(killer, gun);
        player.setState(PlayerState.SICK);
        gun.useCard(player, null, null, ActionType.NONE);
        assertTrue(!player.hasCard(card));
        assertEquals(PlayerState.SICK, player.getState());

    }

    @Test
    public void oldBriefTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card card = new OldBrief(board, stringsBundle);
        board.giveCardToPlayer(player, card);
        assertTrue(card.canBeUsed());
        assertTrue(!card.discardOnDeath());
        assertEquals(stringsBundle.getString("OldBrief_description"), card.getCardDescription());
        assertEquals(stringsBundle.getString("OldBrief_name"), card.getCardName());
        card.useCard(null, null, null, ActionType.NONE);
        assertTrue(!player.hasCard(card));
    }

    @Test
    public void pendulumTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card card = new Pendulum(board, stringsBundle);
        board.giveCardToPlayer(player, card);
        assertTrue(card.canBeUsed());
        assertTrue(!card.discardOnDeath());
        assertEquals(stringsBundle.getString("Pendulum_description"), card.getCardDescription());
        assertEquals(stringsBundle.getString("Pendulum_name"), card.getCardName());
        for (int index = 1; index < board.getPlayerList().size(); index++) {
            board.killPlayer(board.getPlayerList().get(index));
        }
        assertTrue(!card.canBeUsed());
        Player target = board.getPlayerList().get(1);
        target.setState(PlayerState.HEALTHY);
        card.useCard(target, null, null, ActionType.FOOD);
        assertEquals(ActionType.FOOD, target.getImposedActionThisRound());
        assertTrue(!player.hasCard(card));
        board.switchToNextRound();
        assertEquals(ActionType.NONE, target.getImposedActionThisRound());
    }

    @Test
    public void rottenFishTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card card = new RottenFish(board, stringsBundle);
        Card matches = new Matches(board, stringsBundle);
        matches.useCard(null, null, null, ActionType.NONE);
        board.giveCardToPlayer(player, card);
        Card cardBis = new RottenFish(board, stringsBundle);
        board.giveCardToPlayer(player, cardBis);
        assertTrue(card.canBeUsed());
        assertTrue(!card.discardOnDeath());
        assertEquals(stringsBundle.getString("RottenFish_description"), card.getCardDescription());
        assertEquals(stringsBundle.getString("RottenFish_name"), card.getCardName());
        Player target = board.getPlayerList().get(2);
        Player targetSick = board.getPlayerList().get(3);
        int prevFoodRation = board.getFoodRations();
        card.useCard(target, null, null, ActionType.NONE);
        assertEquals(prevFoodRation + 1, board.getFoodRations());
        assertEquals(PlayerState.HEALTHY, targetSick.getState());
        cardBis.useCard(targetSick, null, null, ActionType.NONE);
        assertEquals(prevFoodRation + 2, board.getFoodRations());
        assertEquals(PlayerState.SICK, targetSick.getState());
        assertTrue(!player.hasCard(card));
    }

    @Test
    public void sandwichTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card card = new Sandwich(board, stringsBundle);
        board.giveCardToPlayer(player, card);
        assertTrue(card.canBeUsed());
        assertTrue(!card.discardOnDeath());
        assertEquals(stringsBundle.getString("Sandwich_description"), card.getCardDescription());
        assertEquals(stringsBundle.getString("Sandwich_name"), card.getCardName());
        int prevFoodRations = board.getFoodRations();
        card.useCard(null, null, null, ActionType.NONE);
        assertTrue(!player.hasCard(card));
        assertEquals(prevFoodRations + 1, board.getFoodRations());
    }

    @Test
    public void sardinesTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card card = new Sardines(board, stringsBundle);
        board.giveCardToPlayer(player, card);
        assertTrue(card.canBeUsed());
        assertTrue(!card.discardOnDeath());
        assertEquals(stringsBundle.getString("Sardines_description"), card.getCardDescription());
        assertEquals(stringsBundle.getString("Sardines_name"), card.getCardName());
        int prevFoodRations = board.getFoodRations();
        card.useCard(null, null, null, ActionType.NONE);
        assertTrue(!player.hasCard(card));
        assertEquals(prevFoodRations + 3, board.getFoodRations());
    }

    @Test
    public void sleepingPillsTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card card = new SleepingPills(board, stringsBundle);
        board.cardsDistribution();

        board.giveCardToPlayer(player, card);
        Player target1 = board.getPlayerList().get(1);
        Player target2 = board.getPlayerList().get(2);
        Player target3 = board.getPlayerList().get(3);
        assertTrue(target1.getCardNumber() > 0);
        assertTrue(target2.getCardNumber() > 0);
        assertTrue(target3.getCardNumber() > 0);
        assertTrue(card.canBeUsed());
        assertTrue(!card.discardOnDeath());
        assertEquals(stringsBundle.getString("SleepingPills_description"), card.getCardDescription());
        assertEquals(stringsBundle.getString("SleepingPills_name"), card.getCardName());
        List<Card> prevInventoryTarget1 = cloneListCard(target1.getInventory());
        List<Card> prevInventoryTarget2 = cloneListCard(target2.getInventory());
        List<Card> prevInventoryTarget3 = cloneListCard(target3.getInventory());
        List<Card> prevInventoryUser = cloneListCard(player.getInventory());

        card.useCard(target1, target2, target3, ActionType.NONE);
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        for (Card cardUser : player.getInventory()) {
            if (!prevInventoryUser.contains(cardUser)) {
                if (prevInventoryTarget1.contains(cardUser) && !target1.getInventory().contains(cardUser)) {
                    flag1 = true;
                } else if (prevInventoryTarget2.contains(cardUser) && !target2.getInventory().contains(cardUser)) {
                    flag2 = true;
                } else if (prevInventoryTarget3.contains(cardUser) && !target3.getInventory().contains(cardUser)) {
                    flag3 = true;
                }
            }
        }
        assertTrue(flag1 && flag2 && flag3);
        assertTrue(!player.hasCard(card));
        assertEquals(prevInventoryUser.size() + 2, player.getCardNumber());
    }

    @Test
    public void spyglassTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card card = new Spyglass(board, stringsBundle);
        board.giveCardToPlayer(player, card);
        assertTrue(card.canBeUsed());
        assertTrue(!card.discardOnDeath());
        assertEquals(stringsBundle.getString("Spyglass_description"), card.getCardDescription());
        assertEquals(stringsBundle.getString("Spyglass_name"), card.getCardName());
        int nbCardPlayers = 0;
        List<Card> otherPlayersCard = new ArrayList<>();
        for (Player playerSpied : board.getPlayerList()) {
            if (!playerSpied.equals(player)) {
                nbCardPlayers += playerSpied.getCardNumber();
                for (int cardIndex = 0; cardIndex < playerSpied.getCardNumber(); cardIndex++) {
                    otherPlayersCard.add(playerSpied.getCard(cardIndex));
                }
            }
        }
        card.useCard(null, null, null, ActionType.NONE);
        assertTrue(!player.hasCard(card));
        assertEquals(nbCardPlayers, board.getSpyglassList().size());
        assertTrue(otherPlayersCard.equals(board.getSpyglassList()));

    }

    @Test
    public void stagnantWaterTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card card = new StagnantWater(board, stringsBundle);
        Card matches = new Matches(board, stringsBundle);
        matches.useCard(null, null, null, ActionType.NONE);
        board.giveCardToPlayer(player, card);
        Card cardBis = new StagnantWater(board, stringsBundle);
        board.giveCardToPlayer(player, cardBis);
        assertTrue(card.canBeUsed());
        assertTrue(!card.discardOnDeath());
        assertEquals(stringsBundle.getString("StagnantWater_description"), card.getCardDescription());
        assertEquals(stringsBundle.getString("StagnantWater_name"), card.getCardName());
        Player target = board.getPlayerList().get(2);
        Player targetSick = board.getPlayerList().get(3);
        int prevWaterRation = board.getWaterRations();
        card.useCard(target, null, null, ActionType.NONE);
        assertEquals(prevWaterRation + 1, board.getWaterRations());
        assertEquals(PlayerState.HEALTHY, targetSick.getState());
        cardBis.useCard(targetSick, null, null, ActionType.NONE);
        assertEquals(prevWaterRation + 2, board.getWaterRations());
        assertEquals(PlayerState.SICK, targetSick.getState());
        assertTrue(!player.hasCard(card));
    }

    @Test
    public void toiletBrushTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card card = new ToiletBrush(board, stringsBundle);
        board.giveCardToPlayer(player, card);
        assertTrue(card.canBeUsed());
        assertTrue(!card.discardOnDeath());
        assertEquals(stringsBundle.getString("ToiletBrush_description"), card.getCardDescription());
        assertEquals(stringsBundle.getString("ToiletBrush_name"), card.getCardName());
        card.useCard(null, null, null, ActionType.NONE);
        assertTrue(!player.hasCard(card));
    }

    @Test
    public void vegetableMillTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card card = new VegetableMill(board, stringsBundle);
        board.giveCardToPlayer(player, card);
        board.setFoodRations(0);
        assertTrue(!card.canBeUsed());
        board.setFoodRations(2);
        assertTrue(card.canBeUsed());
        assertTrue(!card.discardOnDeath());
        assertEquals(stringsBundle.getString("VegetableMill_description"), card.getCardDescription());
        assertEquals(stringsBundle.getString("VegetableMill_name"), card.getCardName());
        int prevFoodRation = board.getFoodRations();
        int prevWaterRation = board.getWaterRations();
        card.useCard(null, null, null, ActionType.NONE);
        assertEquals(prevFoodRation - 2, board.getFoodRations());
        assertEquals(prevWaterRation + 2, board.getWaterRations());
        assertTrue(!player.hasCard(card));
    }

    @Test
    public void voodooDollTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card card = new VoodooDoll(board, stringsBundle);
        board.giveCardToPlayer(player, card);
        assertTrue(!card.canBeUsed());
        assertTrue(!card.discardOnDeath());
        assertEquals(stringsBundle.getString("VoodooDoll_description"), card.getCardDescription());
        assertEquals(stringsBundle.getString("VoodooDoll_name"), card.getCardName());
        Player target = board.getPlayerList().get(2);
        board.killPlayer(target);
        board.setCurrentPhase(GamePhase.ROUND_BEGINNING);
        assertTrue(card.canBeUsed());
        card.useCard(target, null, null, ActionType.NONE);
        assertTrue(!board.getDeadThisRound().contains(target));
        assertEquals(PlayerState.HEALTHY, target.getState());
        assertEquals(0, target.getCardNumber());
        assertTrue(!player.hasCard(card));
    }

    @Test
    public void waterBottleTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        Card card = new WaterBottle(board, stringsBundle);
        board.giveCardToPlayer(player, card);
        assertTrue(card.canBeUsed());
        assertTrue(!card.discardOnDeath());
        assertEquals(stringsBundle.getString("WaterBottle_description"), card.getCardDescription());
        assertEquals(stringsBundle.getString("WaterBottle_name"), card.getCardName());
        int previousWaterRations = board.getWaterRations();
        card.useCard(null, null, null, ActionType.NONE);
        assertEquals(previousWaterRations + 1, board.getWaterRations());
        assertTrue(!player.hasCard(card));
    }

    @Test
    public void winningLotteryTicketTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        WinningLotteryTicket card = new WinningLotteryTicket(board, stringsBundle);
        board.giveCardToPlayer(player, card);
        assertTrue(card.canBeUsed());
        assertTrue(!card.discardOnDeath());
        assertEquals(stringsBundle.getString("WinningLotteryTicket_description"), card.getCardDescription());
        assertEquals(stringsBundle.getString("WinningLotteryTicket_name"), card.getCardName());
        card.useCard(null, null, null, ActionType.NONE);
        assertTrue(!player.hasCard(card));
    }

    @Test
    public void woodenPlankTest() {
        Board board = new Board(5);
        Player player = board.getPlayerList().get(0);
        WoodenPlank card = new WoodenPlank(board, stringsBundle);
        board.giveCardToPlayer(player, card);

        assertTrue(card.canBeUsed());
        assertTrue(!card.discardOnDeath());
        assertEquals(stringsBundle.getString("WoodenPlank_description"), card.getCardDescription());
        assertEquals(stringsBundle.getString("WoodenPlank_name"), card.getCardName());
        card.useCard(null, null, null, ActionType.NONE);
        assertEquals(1, board.getWoodPlank());
        assertTrue(!player.hasCard(card));
    }

}
