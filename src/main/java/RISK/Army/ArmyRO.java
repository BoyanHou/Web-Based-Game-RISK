//code reviewed by Zian Li
package RISK.Army;
import RISK.Player.Player;
import RISK.Player.PlayerRO;
import RISK.Unit.Unit;
import RISK.Unit.UnitLevelException;

import java.util.ArrayList;
import java.util.HashMap;

/*
    Writer & Maintainer: hby
    ArmyRO(Read Only): the group to attack or defend
*/

public abstract class ArmyRO<T> {

    protected Player owner;
    protected int armyID;
    protected HashMap<Integer, ArrayList<Unit>> unitMap;

    public PlayerRO getOwnerRO() {
        return owner;
    }

    public int getArmyID() {
        return armyID;
    }

    public HashMap<Integer, ArrayList<Unit>> getUnitMapCopy() throws UnitLevelException {
        HashMap<Integer, ArrayList<Unit>> unitMapCopy = new HashMap<>();
        for (int ownerID : this.unitMap.keySet()) {
            ArrayList<Unit> unitListCopy = new ArrayList<>();
            unitMapCopy.put(ownerID, unitListCopy);
            ArrayList<Unit> unitList = this.unitMap.get(ownerID);
            for (Unit unit : unitList) {
                Unit unitCopy = unit.getCopy();
                unitListCopy.add(unitCopy);
            }
            unitMapCopy.put(ownerID, unitListCopy);
        }
        return unitMapCopy;
    }
}
