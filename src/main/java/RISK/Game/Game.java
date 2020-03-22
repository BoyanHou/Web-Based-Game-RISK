package RISK.Game;

import RISK.Army.Army;
import RISK.Player.Player;
import RISK.Territory.Territory;
import java.util.HashMap;

public class Game extends GameRO {
  public HashMap<Integer, Army> getArmyMap() {
    return this.armyMap;
  }
  public void setArmyMap(HashMap<Integer, Army> armyMap) {
    this.armyMap = armyMap;
  }

  public HashMap<Integer, Territory> getTerrMap() {
    return this.terrMap;
  }
  public void setTerrMap(HashMap<Integer, Territory> terrMap) {
    this.terrMap = terrMap;
  }

  public HashMap<Integer, Player> getPlayerMap() {
    return this.playerMap;
  }
  public void setPlayerMap(HashMap<Integer, Player> playerMap) {
    this.playerMap = playerMap;
  }
}
