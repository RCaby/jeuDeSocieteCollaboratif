package back.cards.items;

import back.cards.items.expansion.*;

public enum CardEnum {
    ALARMCLOCK(AlarmClock.class.getName(), AlarmClock.NUMBER_THIS_IN_DECK, false),
    ANTIVENOM(Antivenom.class.getName(), Antivenom.NUMBER_THIS_IN_DECK, false),
    AXE(Axe.class.getName(), Axe.NUMBER_THIS_IN_DECK, false),
    BAROMETER(Barometer.class.getName(), Barometer.NUMBER_THIS_IN_DECK, false),
    BOARD_GAME_QUORIDOR(BoardGameQuoridor.class.getName(), BoardGameQuoridor.NUMBER_THIS_IN_DECK, false),
    CARTRIDGE(Cartridge.class.getName(), Cartridge.NUMBER_THIS_IN_DECK, false),
    CLUB(Club.class.getName(), Club.NUMBER_THIS_IN_DECK, false),
    COCONUT(Coconut.class.getName(), Coconut.NUMBER_THIS_IN_DECK, false),
    COFFEE(Coffee.class.getName(), Coffee.NUMBER_THIS_IN_DECK, false),
    CONCH(Conch.class.getName(), Conch.NUMBER_THIS_IN_DECK, false),
    CRYSTAL_BALL(CrystalBall.class.getName(), CrystalBall.NUMBER_THIS_IN_DECK, false),
    FISHINGROD(FishingRod.class.getName(), FishingRod.NUMBER_THIS_IN_DECK, false),
    FLASHLIGHT(Flashlight.class.getName(), Flashlight.NUMBER_THIS_IN_DECK, false),
    GIFTBASKET(GiftBasket.class.getName(), GiftBasket.NUMBER_THIS_IN_DECK, false),
    GOURD(Gourd.class.getName(), Gourd.NUMBER_THIS_IN_DECK, false),
    GUN(Gun.class.getName(), Gun.NUMBER_THIS_IN_DECK, false),
    KIT_BBQ_CANNIBAL(KitBBQCannibal.class.getName(), KitBBQCannibal.NUMBER_THIS_IN_DECK, false),
    LUXURY_CAR_KEY(LuxuryCarKey.class.getName(), LuxuryCarKey.NUMBER_THIS_IN_DECK, false),
    MATCHES(Matches.class.getName(), Matches.NUMBER_THIS_IN_DECK, false),
    METAL_SHEET(MetalSheet.class.getName(), MetalSheet.NUMBER_THIS_IN_DECK, false),
    OLD_BRIEF(OldBrief.class.getName(), OldBrief.NUMBER_THIS_IN_DECK, false),
    PENDULUM(Pendulum.class.getName(), Pendulum.NUMBER_THIS_IN_DECK, false),
    ROTTEN_FISH(RottenFish.class.getName(), RottenFish.NUMBER_THIS_IN_DECK, false),
    SANDWICH(Sandwich.class.getName(), Sandwich.NUMBER_THIS_IN_DECK, false),
    SARDINES(Sardines.class.getName(), Sardines.NUMBER_THIS_IN_DECK, false),
    SLEEPING_PILLS(SleepingPills.class.getName(), SleepingPills.NUMBER_THIS_IN_DECK, false),
    SPYGLASS(Spyglass.class.getName(), Spyglass.NUMBER_THIS_IN_DECK, false),
    STAGNANT_WATER(StagnantWater.class.getName(), StagnantWater.NUMBER_THIS_IN_DECK, false),
    TOILET_BRUSH(ToiletBrush.class.getName(), ToiletBrush.NUMBER_THIS_IN_DECK, false),
    VEGETABLE_MILL(VegetableMill.class.getName(), VegetableMill.NUMBER_THIS_IN_DECK, false),
    VOODOO_DOLL(VoodooDoll.class.getName(), VoodooDoll.NUMBER_THIS_IN_DECK, false),
    WATER_BOTTLE(WaterBottle.class.getName(), WaterBottle.NUMBER_THIS_IN_DECK, false),
    WINNING_LOTTERY_TICKET(WinningLotteryTicket.class.getName(), WinningLotteryTicket.NUMBER_THIS_IN_DECK, false),
    WOODEN_PLANK(WoodenPlank.class.getName(), WoodenPlank.NUMBER_THIS_IN_DECK, false),
    BUOY_EXPANSION(BuoyExpansion.class.getName(), BuoyExpansion.NUMBER_THIS_IN_DECK, true),
    CARTRIDGE_EXPANSION(CartridgeExpansion.class.getName(), CartridgeExpansion.NUMBER_THIS_IN_DECK, true),
    CAT_EXPANSION(CatExpansion.class.getName(), CatExpansion.NUMBER_THIS_IN_DECK, true),
    CHINESE_NOODLES_EXPANSION(ChineseNoodlesExpansion.class.getName(), ChineseNoodlesExpansion.NUMBER_THIS_IN_DECK,
            true),
    CONCAVE_METAL_SHEET_EXPANSION(ConcaveMetalSheetExpansion.class.getName(),
            ConcaveMetalSheetExpansion.NUMBER_THIS_IN_DECK, true),
    CRATE_EXPANSION(CrateExpansion.class.getName(), CrateExpansion.NUMBER_THIS_IN_DECK, true),
    EXPANDING_BULLET_EXPANSION(ExpandingBulletExpansion.class.getName(), ExpandingBulletExpansion.NUMBER_THIS_IN_DECK,
            true),
    FISH_BOWL_EXPANSION(FishBowlExpansion.class.getName(), FishBowlExpansion.NUMBER_THIS_IN_DECK, true),
    GUN_EXPANSION(GunExpansion.class.getName(), GunExpansion.NUMBER_THIS_IN_DECK, true),
    HONE_EXPANSION(HoneExpansion.class.getName(), HoneExpansion.NUMBER_THIS_IN_DECK, true),
    KIT_SHAMAN_EXPANSION(KitShamanExpansion.class.getName(), KitShamanExpansion.NUMBER_THIS_IN_DECK, true),
    MAGAZINE_EXPANSION(MagazineExpansion.class.getName(), MagazineExpansion.NUMBER_THIS_IN_DECK, true),
    METAL_DETECTOR_EXPANSION(MetalDetectorExpansion.class.getName(), MetalDetectorExpansion.NUMBER_THIS_IN_DECK, true),
    RUM_EXPANSION(RumExpansion.class.getName(), RumExpansion.NUMBER_THIS_IN_DECK, true),
    SANDWICH_EXPANSION(SandwichExpansion.class.getName(), SandwichExpansion.NUMBER_THIS_IN_DECK, true),
    TASER_EXPANSION(TaserExpansion.class.getName(), TaserExpansion.NUMBER_THIS_IN_DECK, true),
    WATER_BOTTLE_EXPANSION(WaterBottleExpansion.class.getName(), WaterBottleExpansion.NUMBER_THIS_IN_DECK, true),
    WHIP_EXPANSION(WhipExpansion.class.getName(), WhipExpansion.NUMBER_THIS_IN_DECK, true);

    private String className;
    private int numberOfCard;
    private boolean isFromExpansion;

    private CardEnum(String className, int numberOfCard, boolean isFromExpansion) {
        this.className = className;
        this.numberOfCard = numberOfCard;
        this.isFromExpansion = isFromExpansion;
    }

    public String getClassName() {
        return className;
    }

    public int getNumberOfCard() {
        return numberOfCard;
    }

    public boolean isFromExpansion() {
        return isFromExpansion;
    }
}
