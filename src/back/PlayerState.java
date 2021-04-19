package back;

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
}
