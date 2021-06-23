package back.cards.characters;

public enum CharacterEnum {
    NATURIST(0), SNAKE_CHARMER(0), SHARP_EYE(0), STRAPPING(0), HYPNOTIST(0), SCAVENGER(0), FOOD_ALL(0), SURVIVALIST(0),
    WHOLESALE_FISHERMAN(0), DOWSER(0), TIME_TRAVELLER(0), MAGICIAN(0), UPTIGHT(0), FARSIGHTED(0), SUMO(0), BODYGUARD(6),
    SALESMAN(5), MODEL(5), CAPTAIN(5), KID(5),;

    int numberMinPlayers;

    CharacterEnum(int nbMinPlayers) {
        numberMinPlayers = nbMinPlayers;
    }
}
