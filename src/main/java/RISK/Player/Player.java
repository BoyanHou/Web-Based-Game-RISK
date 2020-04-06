/*
    Writer & Maintainer:

 */
package RISK.Player;


import RISK.Territory.Territory;
import RISK.Unit.UnitLevelException;
import RISK.Utils.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Player<T> extends PlayerRO<T> {

    public Player(int playerID, String name, int status) {
        this.playerID = playerID;
        this.name = name;
        this.status = status;
        this.terrMap = new HashMap<>();
    }

    public HashMap<Integer, Territory> getTerrMap() {
        return this.terrMap;
    }

    public void setTerrMap(Map<Integer, Territory> terrMap) {
        this.terrMap = (HashMap)terrMap;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public void setFood(int food) {this.food = food;}
    public void setTech(int tech) {this.tech = tech;}
    public void setName(String name) {this.name = name;}
    public void setPlayerID(int playerID) {this.playerID = playerID;}

    public void harvestTech() {
        for (Territory terr : this.terrMap.values()) {
            this.tech += terr.getTech();
        }
    }

    public void harvestFood() {
        for (Territory terr: this.terrMap.values()) {
            this.food += terr.getFood();
        }
    }

    public void harvestUnit() throws UnitLevelException {
        for (Territory terr: this.terrMap.values()) {
            terr.generateUnits();
        }
    }

    public void addTerr(Territory terr) {
        this.terrMap.put(terr.getTerrID(), terr);
    }

    public void looseTerr(int terrID) {
        this.terrMap.remove(terrID);
    }

    public abstract T pton();

}
