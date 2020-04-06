package RISK.Player;


import java.util.ArrayList;
import java.util.HashMap;

import RISK.Territory.Territory;
import RISK.Territory.TerritoryRO;
import RISK.Utils.Status;

/*
    Writer & Maintainer: hby
 */
public class PlayerRO<T> {

    protected int playerID;
    protected String name;
    protected int status; // 0 for in-game, 1 for audit, 2 for exit
    protected HashMap<Integer, Territory> terrMap;
    protected int food;  // food resources
    protected int tech;  // tech resources

    public int getPlayerID() {
        return playerID;
    }
    public String getName() {
        return name;
    }
    public int getStatus() {
        return status;
    }
    public HashMap<Integer, TerritoryRO> getTerrMapRO() {
        HashMap<Integer, TerritoryRO> terrMapRO = new HashMap<>();
        for (int terrID : this.terrMap.keySet()) {
            TerritoryRO terrRO = this.terrMap.get(terrID);
            terrMapRO.put(terrID, terrRO);
        }
        return terrMapRO;
    }

    public int getFood() {return this.food;}
    public int getTech() {return this.tech;}
}
