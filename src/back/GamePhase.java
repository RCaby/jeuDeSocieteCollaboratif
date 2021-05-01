package back;

/**
 * The different phases of a game.
 * 
 * <p>
 * A game phase can be :
 * <ul>
 * <li>Initialisation
 * <li>Round Beginning
 * <li>Gathering Ressources
 * <li>Goods Distribution
 * <li>Vote
 * <li>End
 * </ul>
 */
public enum GamePhase {
    INITIALISATION("Initialisation"), ROUND_BEGINNING("Round Beginning"), GATHERING_RESSOURCES("Gathering Ressources"),
    GOODS_DISTRIBUTION("Goods Distribution"), VOTE("Vote"), END("End");

    final String phaseName;

    GamePhase(String name) {
        phaseName = name;
    }

    @Override
    public String toString() {
        return phaseName;
    }

}
