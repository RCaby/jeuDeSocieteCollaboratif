package back;

/**
 * The different states of a {@link Player}. A Player can be {@code HEALTHY},
 * {@code SICK_FROM_SNAKE}, {@code SICK_FROM_FOOD} or {@code DEAD}.
 */
public enum PlayerState {
    HEALTHY("Healthy"), SICK_FROM_SNAKE("Sick"), SICK_FROM_FOOD("Sick"), DEAD("Dead");

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
