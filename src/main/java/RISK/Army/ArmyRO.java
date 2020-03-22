package RISK.Army;
import RISK.Player.Player;
import RISK.Player.PlayerRO;
import RISK.Unit.Unit;
import java.util.ArrayList;

/*
    Writer & Maintainer: hby
    ArmyRO(Read Only): the group to attack or defend
*/

public abstract class ArmyRO {

    protected Player owner;
    protected int armyID;
    protected ArrayList<Unit> unitList;

    public PlayerRO getOwnerRO() {
        return owner;
    }

    public int getArmyID() {
        return armyID;
    }

    public ArrayList<Unit> getUnitList() {
        return unitList;
    }

    public int getUnitListSize(){
    return unitList.size();
  }
}
