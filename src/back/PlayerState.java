package back;

/**
 * The different states of a {@link Player}. A Player can be {@code HEALTHY},
 * {@code SICK} or {@code DEAD}.
 */
public enum PlayerState {
    HEALTHY("Healthy"), SICK("Sick"), DEAD("Dead");

    String name;

    PlayerState(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
    // TODO link this class with the Strings bundle
}
