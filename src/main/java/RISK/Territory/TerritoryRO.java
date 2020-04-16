/*
    Writer & Maintainer:

 */

package RISK.Territory;
import RISK.Army.Army;
import RISK.Army.ArmyRO;
import RISK.Player.Player;
import RISK.Player.PlayerRO;
import RISK.Unit.Spy;
import RISK.Unit.UnitLevelException;
import RISK.Utils.TerritoryNames;

import java.util.ArrayList;
import java.util.HashMap;

/*
    Writer & Maintainer: hby
 */
public class TerritoryRO<T> {

    protected int terrID;
    protected String name;
    protected int size;
    protected int food;
    protected int tech;

    protected Player owner;
    protected Army ownerArmy;
    protected HashMap<Integer, Territory> neighborMap; // terrID:Territory
    protected HashMap<Integer, Army> attackArmyMap; // ownerID:Army
    protected HashMap<Integer, Integer> unitGenMap; // level:number -- units to generate each round (for owner)
    protected HashMap<Integer, ArrayList<Spy>> spyMap;         // ownerID:Spy

    public int getTerrID() {return this.terrID;}
    public String getName() {return this.name;}
    public int getSize() {return this.size;}
    public int getFood() {return this.food;}
    public int getTech() {return this.tech;}



    public PlayerRO getOwnerRO() {return this.owner;}

    public ArmyRO getOwnerArmyRO() {return this.ownerArmy;}

    public HashMap<Integer, TerritoryRO> getNeighborMapRO() {
        if (this.neighborMap == null) {
            return null;
        }
        HashMap <Integer, TerritoryRO> neighborMapRO = new HashMap<>();
        for (int terrID : this.neighborMap.keySet()) {
            neighborMapRO.put(terrID, this.neighborMap.get(terrID));
        }
        return neighborMapRO;
    }

    public HashMap<Integer, ArmyRO> getAttackArmyMapRO() {
        if (attackArmyMap == null) {
            return null;
        }
        HashMap<Integer, ArmyRO> attackArmyMapRO = new HashMap<>();
        for (int armyID : this.attackArmyMap.keySet()) {
            attackArmyMapRO.put(armyID, this.attackArmyMap.get(armyID));
        }
        return attackArmyMapRO;
    }

    public HashMap<Integer, ArrayList<Spy>> getSpyMapCopy() throws UnitLevelException {
        HashMap<Integer, ArrayList<Spy>> spyMapCopy = new HashMap<>();
        for (int ownerID : this.spyMap.keySet()) {
            ArrayList<Spy> spyList = this.spyMap.get(ownerID);
            ArrayList<Spy> spyListCopy = new ArrayList<>();
            for (Spy spy : spyList) {
                Spy spyCopy = (Spy)spy.getCopy();
                spyListCopy.add(spyCopy);
            }
            spyMapCopy.put(ownerID, spyListCopy);
        }
        return spyMapCopy;
    }
}
