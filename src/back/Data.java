package back;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

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

public class Data implements Serializable {
    private static final long serialVersionUID = 2040640105155032989L;
    private Map<Integer, Integer[]> initialRations;
    private List<Integer> weatherList;
    private transient ResourceBundle stringsBundle;
    Random random;

    public Data(ResourceBundle strings) {
        stringsBundle = strings;
        initialRations = new HashMap<>();
        weatherList = new ArrayList<>();
        Integer[] tempRation3 = { 5, 6 };
        Integer[] tempRation4 = { 7, 8 };
        Integer[] tempRation5 = { 8, 10 };
        Integer[] tempRation6 = { 10, 12 };
        Integer[] tempRation7 = { 12, 14 };
        Integer[] tempRation8 = { 13, 16 };
        Integer[] tempRation9 = { 15, 18 };
        Integer[] tempRation10 = { 16, 20 };
        Integer[] tempRation11 = { 18, 22 };
        Integer[] tempRation12 = { 20, 24 };
        initialRations.put((Integer) 3, tempRation3);
        initialRations.put((Integer) 4, tempRation4);
        initialRations.put((Integer) 5, tempRation5);
        initialRations.put((Integer) 6, tempRation6);
        initialRations.put((Integer) 7, tempRation7);
        initialRations.put((Integer) 8, tempRation8);
        initialRations.put((Integer) 9, tempRation9);
        initialRations.put((Integer) 10, tempRation10);
        initialRations.put((Integer) 11, tempRation11);
        initialRations.put((Integer) 12, tempRation12);

        weatherList.add(0);
        weatherList.add(0);
        weatherList.add(0);
        weatherList.add(1);
        weatherList.add(1);
        weatherList.add(1);
        weatherList.add(2);
        weatherList.add(2);
        weatherList.add(2);
        weatherList.add(3);
        weatherList.add(3);
        weatherList.add(-2);
    }

    public Integer[] getInitialRations(int nbPlayers) {
        return initialRations.get((Integer) nbPlayers);
    }

    public int[] getWeatherList() {
        int[] weather = new int[12];
        for (int i = 0; i < 12; i++) {
            weather[i] = -1;
        }

        List<Integer> alreadyPicked = new ArrayList<>();
        boolean flag;
        int maxBound = 11;
        random = new Random();
        for (int index = 0; index < 12; index++) {
            if (index >= 6) {
                maxBound = 12;
            }
            flag = false;
            while (!flag) {
                int pickedIndex = random.nextInt(maxBound);
                if (!alreadyPicked.contains(pickedIndex)) {
                    weather[index] = weatherList.get(pickedIndex);
                    alreadyPicked.add(pickedIndex);
                    flag = true;
                }
            }
        }
        return weather;
    }

    public List<Card> getDeck(Board board) {
        List<Card> cardList = new ArrayList<>();

        cardList.add(new AlarmClock(board, stringsBundle));
        cardList.add(new Antivenom(board, stringsBundle));
        cardList.add(new Axe(board, stringsBundle));
        cardList.add(new Barometer(board, stringsBundle));
        cardList.add(new BoardGameQuoridor(board, stringsBundle));
        for (int index = 0; index < 6; index++) {
            cardList.add(new Cartridge(board, stringsBundle));
        }
        cardList.add(new Club(board, stringsBundle));
        cardList.add(new Coconut(board, stringsBundle));
        cardList.add(new Coffee(board, stringsBundle));
        cardList.add(new Conch(board, stringsBundle));
        cardList.add(new CrystalBall(board, stringsBundle));
        cardList.add(new FishingRod(board, stringsBundle));
        cardList.add(new Flashlight(board, stringsBundle));
        cardList.add(new GiftBasket(board, stringsBundle));
        cardList.add(new Gourd(board, stringsBundle));
        for (int index = 0; index < 3; index++) {
            cardList.add(new Gun(board, stringsBundle));
        }

        cardList.add(new KitBBQCannibal(board, stringsBundle));
        cardList.add(new LuxuryCarKey(board, stringsBundle));
        cardList.add(new Matches(board, stringsBundle));
        cardList.add(new MetalSheet(board, stringsBundle));
        cardList.add(new MetalSheet(board, stringsBundle));
        cardList.add(new OldBrief(board, stringsBundle));
        cardList.add(new Pendulum(board, stringsBundle));
        cardList.add(new RottenFish(board, stringsBundle));
        for (int index = 0; index < 7; index++) {
            cardList.add(new Sandwich(board, stringsBundle));
        }
        cardList.add(new Sardines(board, stringsBundle));
        cardList.add(new SleepingPills(board, stringsBundle));
        cardList.add(new Spyglass(board, stringsBundle));
        cardList.add(new StagnantWater(board, stringsBundle));
        cardList.add(new ToiletBrush(board, stringsBundle));
        cardList.add(new VegetableMill(board, stringsBundle));
        cardList.add(new VoodooDoll(board, stringsBundle));
        for (int index = 0; index < 7; index++) {
            cardList.add(new WaterBottle(board, stringsBundle));
        }
        cardList.add(new WinningLotteryTicket(board, stringsBundle));
        cardList.add(new WoodenPlank(board, stringsBundle));

        java.util.Collections.shuffle(cardList);

        return cardList;
    }
}
