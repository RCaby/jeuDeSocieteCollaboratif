package back.personalities;

public abstract class BasicPersonality implements IPersonality {

    protected BasicPersonality() {
        //
    }

    @Override
    public String sayHello() {
        return "Hello, I'm Basic";
    }
}
