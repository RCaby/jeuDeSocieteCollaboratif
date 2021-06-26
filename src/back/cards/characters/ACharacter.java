package back.cards.characters;

import java.util.ResourceBundle;

public abstract class ACharacter implements ICharacter {

    protected ResourceBundle stringBundle;
    protected String characterName = "";
    protected CharacterEnum linkedCharacterEnum = null;
    protected int initialCardBonus = 0;
    protected int nbActionPerRound = 1;
    protected int nbPlacesTakenOnRaft = 1;
    protected int waterConsumptionPerRound = 1;
    protected int foodConsumptionPerRound = 1;
    protected int waterConsumptionOnRaft = 1;
    protected int foodConsumptionOnRaft = 1;
    protected int rationsProducedOnDeath = 2;
    protected boolean spyOnPickCardAction = false;
    protected boolean bulletProtected = false;
    protected boolean cardsLostOnDeath = false;
    protected boolean cardsPermanentlyRevealed = false;
    protected boolean priorityToLootNeighbor = false;
    protected boolean cannotUseSameAction = false;
    protected boolean canFleeAlone = true;
    protected boolean firstOnRaft = false;

    // TODO modify taser : only permanent use card

    protected ACharacter(ResourceBundle stringBundle) {
        this.stringBundle = stringBundle;
    }

    public int updateWaterCollect(int waterRation) {
        return waterRation;
    }

    public int updateFoodCollect(int foodRation) {
        return foodRation;
    }

    public int updateWoodCollect(int woodRation) {
        return woodRation;
    }

    @Override
    public String toString() {
        return characterName;
    }
}
