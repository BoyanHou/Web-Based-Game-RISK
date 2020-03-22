package RISK.ClassBuilder;

import RISK.Army.Army;
import RISK.Factory.NtopFactory;
import RISK.Game.Game;
import RISK.Player.Player;
import RISK.Territory.Territory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ClassBuilderJSON extends ClassBuilder<JSONObject> {

  public ClassBuilderJSON (NtopFactory<JSONObject> ntopFactory) {
    this.ntopFactory = ntopFactory;
  }
  
  protected class ArmyObjectFields {
    public int unitList;  // number of units
    public int owner;     // owner ID

    public ArmyObjectFields(JSONObject armyJO) {
      this.unitList = armyJO.getJSONObject("Army").getInt("unitList");
      this.owner = armyJO.getJSONObject("Army").getInt("owner");
    }
  }

  protected class TerrObjectFields {
    public JSONArray neighborList;    // neighbor terrIDs
    public int owner;                 // owner ID
    public int ownerArmy;             // owner armyID
    public JSONArray attackArmyList; // attacking armyIDs

    public TerrObjectFields(JSONObject terrJO) {
      this.neighborList = terrJO.getJSONObject("Territory").getJSONArray("neighborList");
      this.owner = terrJO.getJSONObject("Territory").getInt("owner");
      this.ownerArmy = terrJO.getJSONObject("Territory").getInt("ownerArmy");
      this.attackArmyList = terrJO.getJSONObject("Territory").getJSONArray("attackArmyList");
    }
  }

  protected class PlayerObjectFields {
    public JSONArray terrList; // owned terrIDs

    public PlayerObjectFields(JSONObject playerJO) {

      this.terrList = playerJO.getJSONObject("Player").getJSONArray("terrList");
    }
  }


  @Override
  protected void buildPrimitiveClasses (JSONObject classes, Game game) {
    JSONArray armyJOs = classes.getJSONArray("Army");
    JSONArray terrJOs = classes.getJSONArray("Terr");
    JSONArray playerJOs = classes.getJSONArray("Player");

    this.buildPrimitiveArmies(armyJOs, game);
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
  
  protected void buildPrimitiveArmies(JSONArray armyJOs, Game game) {

    HashMap<Integer, Army> armyMap = new HashMap<>();
    this.armyFieldMap = new HashMap<>();
    
    for (int i = 0; i < armyJOs.length(); i++) {
      JSONObject armyJO = armyJOs.getJSONObject(i);
      ArmyObjectFields aof = new ArmyObjectFields(armyJO);

      Army army = this.ntopFactory.armyNtop(armyJO);

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
      Territory terr = this.ntopFactory.terrNtop(terrJO);
      TerrObjectFields tof = new TerrObjectFields(terrJO);
      
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
      Player player = this.ntopFactory.playerNtop(playerJO);
      PlayerObjectFields pof = new PlayerObjectFields(playerJO);

      int playerID = player.getPlayerID();
      this.playerFieldMap.put(playerID, pof);
      playerMap.put(playerID, player);
    }
    game.setPlayerMap(playerMap);
  }

  protected void fillArmyClasses(Game game) {
    HashMap<Integer, Army> armyMap = game.getArmyMap();
    HashMap<Integer, Player> playerMap = game.getPlayerMap();
    for (int armyID : armyMap.keySet()) {
      Army army = armyMap.get(armyID);
      ArmyObjectFields aof = this.armyFieldMap.get(armyID);

      Player owner = playerMap.get(aof.owner);
      army.setOwner(owner);

      // !!! NOTICE: very likely to need more changes!!
      // currently just adding soldiers as units, REGARDLESS of specific unit type!!
      // temporarily not used: because ntopFactory has done this
//      int unitNum = aof.unitList;
//      for (int i = 0; i < unitNum; i++) {
//        army.addUnit(new Soldier());
//      }
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

      // neighborList
      ArrayList<Territory> neighborList = new ArrayList<>();
      for (int i = 0; i < tof.neighborList.length(); i++) {
        int neighborTerrID = tof.neighborList.getInt(i);
        Territory neighbor = terrMap.get(neighborTerrID);
        neighborList.add(neighbor);
      }
      terr.setNeighborList(neighborList);

      // owner
      Player owner = playerMap.get(tof.owner);
      terr.setOwner(owner);

      // ownerArmy
      Army ownerArmy = armyMap.get(tof.ownerArmy);
      terr.setOwnerArmy(ownerArmy);

      // attackArmyList
      ArrayList<Army> attackArmyList = new ArrayList<>();
      for (int i = 0; i < tof.attackArmyList.length(); i++) {
        int attackArmyID = tof.attackArmyList.getInt(i);
        Army attackArmy = armyMap.get(attackArmyID);
        attackArmyList.add(attackArmy);
      }
      terr.setAttackArmyList(attackArmyList);
    }

    return;
  }

  protected void fillPlayerClasses(Game game) {
    HashMap<Integer, Player> playerMap = game.getPlayerMap();
    HashMap<Integer, Territory> terrMap = game.getTerrMap();
    
    for (int playerID : playerMap.keySet()) {
      Player player = playerMap.get(playerID);
      PlayerObjectFields pof = this.playerFieldMap.get(playerID);
      
      ArrayList<Territory> terrList = new ArrayList<>();
      for (int i = 0; i < pof.terrList.length(); i++) {
        int terrID = pof.terrList.getInt(i);
        Territory terr = terrMap.get(terrID);
        terrList.add(terr);
      }
      player.setTerrList(terrList);
    }
    return;
  }
}
