package RISK.Factory;

import RISK.Army.Army;
import RISK.Army.ArmyRO;
import RISK.Order.AttackOrder;
import RISK.Order.MoveOrder;
import RISK.Player.Player;
import RISK.Player.PlayerRO;
import RISK.Territory.Territory;
import RISK.Territory.TerritoryRO;
import RISK.Unit.Soldier;
import RISK.Unit.Unit;
import RISK.Utils.Status;
import RISK.Utils.TerritoryNames;
import RISK.Game.Game;

import org.json.JSONObject;

import java.util.*;

public class NtopFactoryJSON implements NtopFactory<JSONObject> {

    /*
      { "MoveOrder": { "fromTerr": the id of from territory,
      "toTerr": the id of to territory,
      "num": the number of units to move,
      "player": the id of the player
      }}
    */
    @Override
    public MoveOrder moveNtop(JSONObject moveOrder,Game game) {

      HashMap<Integer, Territory> terrMap = game.getTerrMap();
      HashMap<Integer, Player> playerMap = game.getPlayerMap();

      MoveOrder mv = moveNtop(moveOrder, terrMap, playerMap);
      return mv;
    }

    public MoveOrder moveNtop(JSONObject moveOrder, HashMap<Integer, Territory> terrMap, HashMap<Integer, Player> playerMap) {

        JSONObject moveOrderN = moveOrder.getJSONObject("MoveOrder");
        int fromTerrID = moveOrderN.getInt("fromTerr");
        int toTerrID = moveOrderN.getInt("toTerr");
        int num = moveOrderN.getInt("num");
        int playerID = moveOrderN.getInt("player");

        Territory fromTerr = terrMap.get(fromTerrID);
        Territory toTerr = terrMap.get(toTerrID);

        Player ownerPlayer = playerMap.get(playerID);

        MoveOrder mv = new MoveOrder(fromTerr, toTerr, num, ownerPlayer);
        return mv;
    }

    /*
      { "AttackOrder": { "myTerr": the id of my territory,
      "targetTerr": the id of target territory,
      "num": the number to attack,
      "player": the id of the player
      }}
    */
    @Override
    public AttackOrder attackNtop(JSONObject atkOrder, Game game) {

      HashMap<Integer, Territory> terrMap = game.getTerrMap(); 
      HashMap<Integer, Player> playerMap = game.getPlayerMap();
      
      AttackOrder atk = attackNtop(atkOrder, terrMap, playerMap);
      return atk;
    }

    public AttackOrder attackNtop(JSONObject atkOrder, HashMap<Integer, Territory> terrMap, HashMap<Integer, Player> playerMap) {

        JSONObject atkOrderN = atkOrder.getJSONObject("AttackOrder");
        int myTerrID = atkOrderN.getInt("myTerr");
        int targetTerrID = atkOrderN.getInt("targetTerr");
        int num = atkOrderN.getInt("num");
        int playerID = atkOrderN.getInt("player");

        Territory myTerr = terrMap.get(myTerrID);
        Territory targetTerr = terrMap.get(targetTerrID);
        Player ownerPlayer = playerMap.get(playerID);

        AttackOrder atk = new AttackOrder(myTerr, targetTerr, num, ownerPlayer);
        return atk;
    }

    /*
      { "Territory": { "terrID": id of the territory,
                       "neighborList": JSONArray of id of all neighbor territories,
                       "name": the Territory Name,
                       "owner": the id of the owner player,
                       "ownerArmy": the id of owner army，
                       "attackArmyList": JSONArray of id of the attackArmy list
                      }
                      }
       */
    public Territory terrNtop(JSONObject terr) {
      JSONObject terrN = terr.getJSONObject("Territory");
      String nameStr = terrN.getString("name");
      TerritoryNames territoryName = null;
      for (TerritoryNames name : TerritoryNames.values()) {
        if (name.toString().equals(nameStr)) {
          territoryName = name;
          break;
        }
      }
      return new Territory(terrN.getInt("terrID"), territoryName);
    }


    /*
      { "Army": { "owner": the id of the owner player,
      "armyID": ,
      "unitList": the number of the units
      }
      }
      */
    public Army armyNtop(JSONObject army) {
        JSONObject armyN = army.getJSONObject("Army");
        int num = armyN.getInt("unitList");
        ArrayList<Unit> unitList = new ArrayList<>();
        while (num > 0) {
            unitList.add(new Soldier());
            num--;
        }
        return new Army(unitList, null, armyN.getInt("armyID"));

    }

    /*
      { "Player": { "playerID": ,
      "name": ,
      "status": ,
      "terrList": JSONArray of terrID of territories that belongs to the player
      }
      }
       */
    public Player playerNtop(JSONObject playerJson) {
        JSONObject playerN = playerJson.getJSONObject("Player");
        Player player = new Player(playerN.getInt("playerID"), playerN.getString("name"));
        String statusStr = playerN.getString("status");
        Status status = null;
        for (Status s : Status.values()) {
            if (s.toString().equals(statusStr)) {
                status = s;
                break;
            }
        }
        player.setStatus(status);
        return player;
    }
}
