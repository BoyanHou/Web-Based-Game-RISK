//code reviewed by Zian Li

package RISK.ClassBuilder;

import RISK.Army.Army;
import RISK.Army.ArmyJSON;
import RISK.Game.Game;
import RISK.Player.Player;
import RISK.Player.PlayerJSON;
import RISK.Territory.TerrJSON;
import RISK.Territory.Territory;
import RISK.Unit.UnitLevelException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

// This class is used for evaluation 2 with an updated army object

public class ClassBuilderEvo2 extends ClassBuilder<JSONObject> {
  
  protected class ArmyObjectFieldsEvo2 implements ArmyObjectFields{
    public int owner;  // owner ID

    public ArmyObjectFieldsEvo2(JSONObject armyJO) {
      this.owner = armyJO.getInt("owner");
    }
    @Override
    public void getConnected(Game game, Army army) {
      army.setOwner(game.getPlayerMap().get(owner));
    }
  }

  protected class TerrObjectFieldsEvo2 implements TerrObjectFields {
    public JSONArray neighborMap;     // neighbor terrIDs
    public int owner;                 // owner ID
    public int ownerArmy;             // owner armyID
    public JSONArray attackArmyMap;   // attacking armyIDs

    public TerrObjectFieldsEvo2(JSONObject terrJO) {
      this.neighborMap = terrJO.getJSONArray("neighborMap");
      this.owner = terrJO.getInt("owner");
      this.ownerArmy = terrJO.getInt("ownerArmy");
      this.attackArmyMap = terrJO.getJSONArray("attackArmyMap");
    }

    @Override
    public void getConnected(Game game, Territory terr) {
      // neighborMap
      HashMap<Integer, Territory> neighborMapTemp = new HashMap<>();
      for (int i = 0; i < this.neighborMap.length(); i++) {
        int neighborTerrID = this.neighborMap.getInt(i);
        Territory neighbor = game.getTerrMap().get(neighborTerrID);
        neighborMapTemp.put(neighborTerrID, neighbor);
      }
      terr.setNeighborMap(neighborMapTemp);

      // owner
      Player owner = game.getPlayerMap().get(this.owner);
      terr.setOwner(owner);

      // ownerArmy
      Army ownerArmy = game.getArmyMap().get(this.ownerArmy);
      terr.setOwnerArmy(ownerArmy);

      // attackArmyMap
      HashMap<Integer, Army> attackArmyMapTemp = new HashMap<>();
      for (int i = 0; i < this.attackArmyMap.length(); i++) {
        int attackArmyID = this.attackArmyMap.getInt(i);
        Army attackArmy = game.getArmyMap().get(attackArmyID);
        attackArmyMapTemp.put(attackArmyID, attackArmy);
      }
      terr.setAttackArmyMap(attackArmyMapTemp);
    }
  }

  protected class PlayerObjectFieldsEvo2 implements PlayerObjectFields {
    public JSONArray terrMap; // owned terrIDs

    public PlayerObjectFieldsEvo2(JSONObject playerJO) {
      this.terrMap = playerJO.getJSONArray("terrMap");
    }
    @Override
    public void getConnected(Game game, Player player) {
      HashMap<Integer, Territory> terrMapTemp = new HashMap<>();
      for (int i = 0; i < this.terrMap.length(); i++) {
        int terrID = this.terrMap.getInt(i);
        Territory terr = game.getTerrMap().get(terrID);
        terrMapTemp.put(terrID, terr);
      }
      player.setTerrMap(terrMapTemp);
    }
  }

  @Override
  protected void buildPrimitiveClasses (JSONObject classesJSON, Game game)
  throws BuildClassesException {
    JSONArray armyJOs = classesJSON.getJSONArray("Army");
    JSONArray terrJOs = classesJSON.getJSONArray("Terr");
    JSONArray playerJOs = classesJSON.getJSONArray("Player");

    try {
      this.buildPrimitiveArmies(armyJOs, game);
    } catch (UnitLevelException e) {
      throw new BuildClassesException("Unit level exception: " + e.getMessage());
    }
    this.buildPrimitiveTerrs(terrJOs, game);
    this.buildPrimitivePlayers(playerJOs, game);

    return;
  }

  @Override
  protected void fillClasses(Game game) {
    this.fillArmyClasses(game);
    this.fillTerrClasses(game);
    this.fillPlayerClasses(game);
  }
  
  protected void buildPrimitiveArmies(JSONArray armyJOs, Game game)
          throws UnitLevelException {

    HashMap<Integer, Army> armyMap = new HashMap<>();
    this.armyFieldMap = new HashMap<>();
    
    for (int i = 0; i < armyJOs.length(); i++) {
      JSONObject armyJO = armyJOs.getJSONObject(i);
      ArmyObjectFields aof = new ArmyObjectFieldsEvo2(armyJO);
      Army army = new ArmyJSON(armyJO);
      int armyID = army.getArmyID();
      this.armyFieldMap.put(armyID, aof);
      armyMap.put(armyID, army);
    }
    game.setArmyMap(armyMap);
  }

  protected void buildPrimitiveTerrs(JSONArray terrJOs, Game game) {
    HashMap<Integer, Territory> terrMap = new HashMap<>();
    this.terrFieldMap = new HashMap<>();
    
    for (int i = 0; i < terrJOs.length(); i++) {
      JSONObject terrJO = terrJOs.getJSONObject(i);
      Territory terr = new TerrJSON(terrJO);
      TerrObjectFields tof = new TerrObjectFieldsEvo2(terrJO);
      
      int terrID = terr.getTerrID();
      this.terrFieldMap.put(terrID, tof);
      terrMap.put(terrID, terr);
    }
    game.setTerrMap(terrMap);
  }

  protected void buildPrimitivePlayers(JSONArray playerJOs, Game game) {
    HashMap<Integer, Player> playerMap = new HashMap<>();
    this.playerFieldMap = new HashMap<>();
    for (int i = 0; i < playerJOs.length(); i++) {
      JSONObject playerJO = playerJOs.getJSONObject(i);
      Player player = new PlayerJSON(playerJO);
      PlayerObjectFields pof = new PlayerObjectFieldsEvo2(playerJO);

      int playerID = player.getPlayerID();
      this.playerFieldMap.put(playerID, pof);
      playerMap.put(playerID, player);
    }
    game.setPlayerMap(playerMap);
  }

  protected void fillArmyClasses(Game game) {
    for (int armyID : game.getArmyMap().keySet()) {
      ArmyObjectFields aof = this.armyFieldMap.get(armyID);
      aof.getConnected(game, game.getArmyMap().get(armyID));
    }
    return;
  }

  protected void fillTerrClasses(Game game) {
    HashMap<Integer, Army> armyMap = game.getArmyMap();
    HashMap<Integer, Player> playerMap = game.getPlayerMap();
    HashMap<Integer, Territory> terrMap = game.getTerrMap();

    for (int terrID : terrMap.keySet()) {
      Territory terr = terrMap.get(terrID);
      TerrObjectFields tof = this.terrFieldMap.get(terrID);
      tof.getConnected(game, terr);
    }

    return;
  }

  protected void fillPlayerClasses(Game game) {
    HashMap<Integer, Player> playerMap = game.getPlayerMap();
    
    for (int playerID : playerMap.keySet()) {
      Player player = playerMap.get(playerID);
      PlayerObjectFields pof = this.playerFieldMap.get(playerID);
      pof.getConnected(game, player);
    }
    return;
  }
}
