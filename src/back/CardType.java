package back;

import back.cards.*;

public enum CardType {
    ALARMCLOCK(AlarmClock.class.getName(), AlarmClock.NUMBER_THIS_IN_DECK),
    ANTIVENOM(Antivenom.class.getName(), Antivenom.NUMBER_THIS_IN_DECK),
    AXE(Axe.class.getName(), Axe.NUMBER_THIS_IN_DECK),
    BAROMETER(Barometer.class.getName(), Barometer.NUMBER_THIS_IN_DECK),
    BOARDGAMEQUORIDOR(BoardGameQuoridor.class.getName(), BoardGameQuoridor.NUMBER_THIS_IN_DECK),
    CARTRIDGE(Cartridge.class.getName(), Cartridge.NUMBER_THIS_IN_DECK),
    CLUB(Club.class.getName(), Club.NUMBER_THIS_IN_DECK), COCONUT(Coconut.class.getName(), Coconut.NUMBER_THIS_IN_DECK),
    COFFEE(Coffee.class.getName(), Coffee.NUMBER_THIS_IN_DECK), CONCH(Conch.class.getName(), Conch.NUMBER_THIS_IN_DECK),
    CRYSTALBALL(CrystalBall.class.getName(), CrystalBall.NUMBER_THIS_IN_DECK),
    FISHINGROD(FishingRod.class.getName(), FishingRod.NUMBER_THIS_IN_DECK),
    FLASHLIGHT(Flashlight.class.getName(), Flashlight.NUMBER_THIS_IN_DECK),
    GIFTBASKET(GiftBasket.class.getName(), GiftBasket.NUMBER_THIS_IN_DECK),
    GOURD(Gourd.class.getName(), Gourd.NUMBER_THIS_IN_DECK), GUN(Gun.class.getName(), Gun.NUMBER_THIS_IN_DECK),
    KITBBQCANNIBAL(KitBBQCannibal.class.getName(), KitBBQCannibal.NUMBER_THIS_IN_DECK),
    LUXURYCARKEY(LuxuryCarKey.class.getName(), LuxuryCarKey.NUMBER_THIS_IN_DECK),
    MATCHES(Matches.class.getName(), Matches.NUMBER_THIS_IN_DECK),
    METALSHEET(MetalSheet.class.getName(), MetalSheet.NUMBER_THIS_IN_DECK),
    OLDBRIEF(OldBrief.class.getName(), OldBrief.NUMBER_THIS_IN_DECK),
    PENDULUM(Pendulum.class.getName(), Pendulum.NUMBER_THIS_IN_DECK),
    ROTTENFISH(RottenFish.class.getName(), RottenFish.NUMBER_THIS_IN_DECK),
    SANDWICH(Sandwich.class.getName(), Sandwich.NUMBER_THIS_IN_DECK),
    SARDINES(Sardines.class.getName(), Sardines.NUMBER_THIS_IN_DECK),
    SLEEPINGPILLS(SleepingPills.class.getName(), SleepingPills.NUMBER_THIS_IN_DECK),
    SPYGLASS(Spyglass.class.getName(), Spyglass.NUMBER_THIS_IN_DECK),
    STAGNANTWATER(StagnantWater.class.getName(), StagnantWater.NUMBER_THIS_IN_DECK),
    TOILETBRUSH(ToiletBrush.class.getName(), ToiletBrush.NUMBER_THIS_IN_DECK),
    VEGETABLEMILL(VegetableMill.class.getName(), VegetableMill.NUMBER_THIS_IN_DECK),
    VOODOODOLL(VoodooDoll.class.getName(), VoodooDoll.NUMBER_THIS_IN_DECK),
    WATERBOTTLE(WaterBottle.class.getName(), WaterBottle.NUMBER_THIS_IN_DECK),
    WINNINGLOTTERYTICKET(WinningLotteryTicket.class.getName(), WinningLotteryTicket.NUMBER_THIS_IN_DECK),
    WOODENPLANK(WoodenPlank.class.getName(), WoodenPlank.NUMBER_THIS_IN_DECK)

    ;

    private String className;
    private int numberOfCard;

    private CardType(String className, int numberOfCard) {
        this.className = className;
        this.numberOfCard = numberOfCard;
    }

    public String getClassName() {
        return className;
    }

    public int getNumberOfCard() {
        return numberOfCard;
    }
}
