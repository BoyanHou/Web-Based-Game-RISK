package RISK.Game;

import RISK.Army.Army;
import RISK.ClassBuilder.BuildClassesException;
import RISK.ClassBuilder.ClassBuilder;
import RISK.Player.Player;
import RISK.Territory.Territory;
import RISK.Utils.TxtReader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class Game extends GameRO {

  public Game() {}

  public Game (String terrPath,
               String playerPath,
               String armyPath,
               ClassBuilder classBuilder) throws IOException, BuildClassesException {

    String terrStr = TxtReader.readStrFromFile(terrPath);
    JSONArray terrJOs = new JSONArray(terrStr);

    String playerStr = TxtReader.readStrFromFile(playerPath);
    JSONArray playerJOs = new JSONArray(playerStr);

    String armyStr = TxtReader.readStrFromFile(armyPath);
    JSONArray armyJOs = new JSONArray(armyStr);

    JSONObject classes = new JSONObject();
    classes.put("Terr", terrJOs);
    classes.put("Player", playerJOs);
    classes.put("Army", armyJOs);

    classBuilder.buildAllClasses(classes, this);
  }

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
