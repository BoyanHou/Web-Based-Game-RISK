package RISK.Unit;

public class Soldier extends Unit {
    public Soldier() throws UnitLevelException{
        this.setLevel(0);
    }

    public Soldier(int level) throws UnitLevelException{
       this.setLevel(level);
    }

    @Override
    public Unit getCopy() throws UnitLevelException {
        Soldier soldierCopy = new Soldier();
        return soldierCopy;
    }
}
