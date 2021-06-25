package back.cards.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public enum EventEnum {

    JERRYCAN(1, Jerrycan.class), BANNED_PLACE(1, BannedPlace.class), PLANE_OF_THE_OTHERS(1, PlaneOfTheOthers.class),
    HAPPY_BIRTHDAY(1, HappyBirthday.class), CRAZY_PSYCHIC(1, CrazyPsychic.class), MARKET_DAY(1, MarketDay.class),
    ARMED_THREAT(1, ArmedThreat.class), TRIBAL_CEMETERY(1, TribalCemetery.class), BARTER(1, Barter.class),
    INSULAR_TAX(1, InsularTax.class),

    PRISONER(2, Prisoner.class), ATTACK_BY_THE_OTHERS(2, AttackByTheOthers.class), GIFT(2, Gift.class),
    CHARITY(2, Charity.class), STARVING_CROCODILE(2, StarvingCrocodile.class), RAINMAKING(2, RainMaking.class),
    POTATO_CROP(2, PotatoCrop.class), HEALER(2, Healer.class), MOULD(2, Mould.class), HEAVY_SWELL(2, HeavySwell.class),

    DESERTED_CAMP(3, DesertedCamp.class), ATTACK(3, Attack.class), CAREER_CHANGE(3, CareerChange.class),
    LOGGER(3, Logger.class), PRESTIGE_HOSTAGE(3, PrestigeHostage.class), LOOTING(3, Looting.class),
    SECRET_STASH(3, SecretStash.class), BODY_ARMOUR(3, BodyArmour.class), NEW_FRIEND(3, NewFriend.class),
    RUMBLING_VOLCANO(3, RumblingVolcano.class);

    int eventType;
    Class<?> linkedClass;

    EventEnum(int eventType, Class<?> linkClass) {
        this.eventType = eventType;
        this.linkedClass = linkClass;
    }

    static Random random = new Random();
    static final EventEnum[] TYPE_ONE = new EventEnum[] { JERRYCAN, BANNED_PLACE, PLANE_OF_THE_OTHERS, HAPPY_BIRTHDAY,
            CRAZY_PSYCHIC, MARKET_DAY, ARMED_THREAT, TRIBAL_CEMETERY, BARTER, INSULAR_TAX };
    static final EventEnum[] TYPE_TWO = new EventEnum[] { PRISONER, ATTACK_BY_THE_OTHERS, GIFT, CHARITY,
            STARVING_CROCODILE, RAINMAKING, POTATO_CROP, MOULD, HEAVY_SWELL, HEALER };
    static final EventEnum[] TYPE_THREE = new EventEnum[] { DESERTED_CAMP, ATTACK, LOGGER, CAREER_CHANGE,
            PRESTIGE_HOSTAGE, LOOTING, SECRET_STASH, BODY_ARMOUR, RUMBLING_VOLCANO, NEW_FRIEND };

    private static EventEnum[] typeXArray(EventEnum[] typeX) {
        var pickedEvent = new EventEnum[4];
        List<Integer> pickedIndex = new ArrayList<>();
        while (pickedIndex.size() != 4) {
            var index = random.nextInt(typeX.length);
            if (!pickedIndex.contains(index)) {
                pickedIndex.add(index);
            }
        }
        for (var index = 0; index < 4; index++) {
            pickedEvent[index] = typeX[pickedIndex.get(index)];
        }
        return pickedEvent;
    }

    /**
     * Generates the event array for this game which means an event of type I, an
     * event of type II, an array of type III etc.
     * 
     * @return the event array
     */
    public static IEvent[] getEventEnumArray(ResourceBundle stringBundle) {
        var eventEnumArray = new EventEnum[12];
        var eventEnumType1 = typeXArray(TYPE_ONE);
        var eventEnumType2 = typeXArray(TYPE_TWO);
        var eventEnumType3 = typeXArray(TYPE_THREE);
        for (var index = 0; index < 4; index++) {
            eventEnumArray[index * 3] = eventEnumType1[index];
            eventEnumArray[index * 3 + 1] = eventEnumType2[index];
            eventEnumArray[index * 3 + 2] = eventEnumType3[index];
        }
        var eventArray = new IEvent[12];
        for (var index = 0; index < eventArray.length; index++) {
            eventArray[index] = eventEnumArray[index].getInstance(stringBundle);
        }
        return eventArray;
    }

    public AEvent getInstance(ResourceBundle stringBundle) {
        AEvent newInstanceACharacter = null;
        try {
            Constructor<?> constructor = linkedClass.getConstructor(ResourceBundle.class);
            Object instance = constructor.newInstance(stringBundle);
            newInstanceACharacter = (AEvent) instance;
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return newInstanceACharacter;
    }

}
