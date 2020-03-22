/*
    Writer & Maintainer:

 */

package RISK.Territory;
import RISK.Army.Army;
import RISK.Army.ArmyRO;
import RISK.Player.Player;
import RISK.Player.PlayerRO;
import RISK.Utils.TerritoryNames;

import java.util.ArrayList;

/*
    Writer & Maintainer: hby
 */
public class TerritoryRO {

    protected int terrID;
    protected ArrayList<Territory> neighborList;
    protected TerritoryNames name;
    protected Player owner;
    protected Army ownerArmy;
    protected ArrayList<Army> attackArmyList;

    public int getTerrID() {
        return this.terrID;
    }

    public TerritoryNames getName() {
        return this.name;
    }

    public PlayerRO getOwnerRO() {
        return owner;
    }

    public ArmyRO getOwnerArmyRO() {
        return ownerArmy;
    }

    public ArrayList<TerritoryRO> getNeighborListRO() {
        if (neighborList == null) {
            return null;
        }
        return new ArrayList<>(neighborList);
    }

    public ArrayList<ArmyRO> getAttackArmyListRO() {
        if (attackArmyList == null) {
            return null;
        }
        return new ArrayList<>(attackArmyList);
    }
}
