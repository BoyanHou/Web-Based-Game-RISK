package RISK.Unit;

public class Spy extends Unit{
    public Spy() throws UnitLevelException {
        this.setLevel(0);
    }

    @Override
    public Unit getCopy() throws UnitLevelException {
        Spy spyCopy = new Spy();
        return spyCopy;
    }
}
