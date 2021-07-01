package back.cards.characters;

import java.io.Serializable;
import java.util.ResourceBundle;

import back.Player;

public abstract class ACharacter implements ICharacter, Serializable {

    protected transient ResourceBundle stringBundle;
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
    protected boolean bulletProtected = false; // TODO useless at the moment
    protected boolean cardsLostOnDeath = false;
    protected boolean cardsPermanentlyRevealed = false;
    protected boolean priorityToLootNeighbor = false;
    protected boolean cannotUseSameAction = false;
    protected boolean canFleeAlone = true;
    protected boolean firstOnRaft = false;
    protected Player linkedPlayer;

    // TODO method to get food etc in each character involved

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

    /**
     * @param linkedPlayer the linkedPlayer to set
     */
    public void setLinkedPlayer(Player linkedPlayer) {
        this.linkedPlayer = linkedPlayer;
    }

    /**
     * @return the linkedPlayer
     */
    public Player getLinkedPlayer() {
        return linkedPlayer;
    }

    /**
     * @return the nbActionPerRound
     */
    public int getNbActionPerRound() {
        return nbActionPerRound;
    }

    /**
     * @return the cannotUseSameAction
     */
    public boolean isCannotUseSameAction() {
        return cannotUseSameAction;
    }

    /**
     * @return the cardsPermanentlyRevealed
     */
    public boolean isCardsPermanentlyRevealed() {
        return cardsPermanentlyRevealed;
    }

    /**
     * @return the priorityToLootNeighbor
     */
    public boolean isPriorityToLootNeighbor() {
        return priorityToLootNeighbor;
    }

    /**
     * @param bulletProtected the bulletProtected to set
     */
    public void setBulletProtected(boolean bulletProtected) {
        this.bulletProtected = bulletProtected;
    }

    /**
     * @return the bulletProtected
     */
    public boolean isBulletProtected() {
        return bulletProtected;
    }

    /**
     * @return the firstOnRaft
     */
    public boolean isFirstOnRaft() {
        return firstOnRaft;
    }

    /**
     * @return the canFleeAlone
     */
    public boolean isCanFleeAlone() {
        return canFleeAlone;
    }

    /**
     * @return the rationsProducedOnDeath
     */
    public int getRationsProducedOnDeath() {
        return rationsProducedOnDeath;
    }

    /**
     * @return the waterConsumptionOnRaft
     */
    public int getWaterConsumptionOnRaft() {
        return waterConsumptionOnRaft;
    }

    /**
     * @return the foodConsumptionOnRaft
     */
    public int getFoodConsumptionOnRaft() {
        return foodConsumptionOnRaft;
    }

    /**
     * @return the nbPlacesTakenOnRaft
     */
    public int getNbPlacesTakenOnRaft() {
        return nbPlacesTakenOnRaft;
    }

    /**
     * @return the foodConsumptionPerRound
     */
    public int getFoodConsumptionPerRound() {
        return foodConsumptionPerRound;
    }

    /**
     * @return the waterConsumptionPerRound
     */
    public int getWaterConsumptionPerRound() {
        return waterConsumptionPerRound;
    }

    /**
     * @return the cardsLostOnDeath
     */
    public boolean isCardsLostOnDeath() {
        return cardsLostOnDeath;
    }

    /**
     * @return the initialCardBonus
     */
    public int getInitialCardBonus() {
        return initialCardBonus;
    }

    @Override
    public String toString() {
        return characterName;
    }
}
