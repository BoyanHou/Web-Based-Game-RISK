package RISK.Game;

import java.util.HashMap;

import RISK.Army.Army;
import RISK.Army.ArmyRO;
import RISK.Player.Player;
import RISK.Player.PlayerRO;
import RISK.Territory.Territory;
import RISK.Territory.TerritoryRO;

public abstract class GameRO {
  protected HashMap<Integer, Territory> terrMap;
  protected HashMap<Integer, Player> playerMap;
  protected HashMap<Integer, Army> armyMap;

  public HashMap<Integer, TerritoryRO> getTerrMapRO() {
    HashMap<Integer, TerritoryRO> res = new HashMap<>();
    for (int key : this.terrMap.keySet()) {
      res.put(key, this.terrMap.get(key));
    }
    return res;
  }

  public HashMap<Integer, PlayerRO> getPlayerMapRO() {
    HashMap<Integer, PlayerRO> res = new HashMap<>();
    for (int key : this.playerMap.keySet()) {
      res.put(key, this.playerMap.get(key));
    }
    return res;
  }

  public HashMap<Integer, ArmyRO> getArmyMapRO() {
    HashMap<Integer, ArmyRO> res = new HashMap<>();
    for (int key : this.armyMap.keySet()) {
      res.put(key, this.armyMap.get(key));
    }
    return res;
  }  
}
