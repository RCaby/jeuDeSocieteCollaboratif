package back;

/**
 * The different threat levels felt by a computer player.
 */
public enum ThreatLevel {
    NONE(0), THREATENED(1), IN_DANGER(2), IMMINENT_DEATH(3);

    int associatedIndex;

    ThreatLevel(int associatedIndex) {
        this.associatedIndex = associatedIndex;
    }

    /**
     * The getter for the attribute {@link ThreatLevel#associatedIndex}.
     * 
     * @return the index of this threat level
     */
    public int getAssociatedIndex() {
        return associatedIndex;
    }
}
