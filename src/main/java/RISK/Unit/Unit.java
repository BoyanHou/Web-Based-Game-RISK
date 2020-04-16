package RISK.Unit;
/*
    Writer & Maintainer:
    Unit: the object could be put on a territory
 */

public abstract class Unit {
    protected int level;     // the level of unit
    protected int bonus;     // the bonus of unit level
    protected String name;   // the name of unit level

    public void setLevel(int level) throws UnitLevelException{
        this.level = level;
        this.bonus = UnitLevelMapper.mapBonus(this.level);
        this.name = UnitLevelMapper.mapName(this.level);
    }

    public int getLevel() {return this.level;}
    public int getBonus() {return this.bonus;}
    public String getName() {return this.name;}

    public abstract Unit getCopy() throws UnitLevelException;
}
