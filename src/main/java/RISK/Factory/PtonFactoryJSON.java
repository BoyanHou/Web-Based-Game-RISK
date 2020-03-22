package RISK.Factory;

import RISK.Army.ArmyRO;
import RISK.Order.AttackOrder;
import RISK.Order.MoveOrder;
import RISK.Player.PlayerRO;
import RISK.Territory.TerritoryRO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/*
   Writer & Maintainer: Lyt
*/

public class PtonFactoryJSON implements PtonFactory<JSONObject> {

    /*
    { "MoveOrder": { "fromTerr": the id of from territory,
                     "toTerr": the id of to territory,
                     "num": the number of units to move,
                     "player": the id of the player
    }}
     */
  
    public JSONObject movePton(MoveOrder moveOrder) {
        JSONObject json = new JSONObject();
        json.put("fromTerr", moveOrder.getFromTerr().getTerrID());
        json.put("toTerr", moveOrder.getToTerrID().getTerrID());
        json.put("num", moveOrder.getNum());
        json.put("player", moveOrder.getPlayer().getPlayerID());

        JSONObject result = new JSONObject();
        result.put("MoveOrder", json);
        return result;
    }

    /*
    { "AttackOrder": { "myTerr": the id of my territory,
                       "targetTerr": the id of target territory,
                       "num": the number to attack,
                       "player": the id of the player
    }}
     */
    public JSONObject attackPton(AttackOrder attackOrder) {
        JSONObject json = new JSONObject();
        json.put("myTerr", attackOrder.getMyTerrTerr().getTerrID());
        json.put("targetTerr", attackOrder.getTargetTerr().getTerrID());
        json.put("num", attackOrder.getNum());
        json.put("player", attackOrder.getPlayer().getPlayerID());

        JSONObject result = new JSONObject();
        result.put("AttackOrder", json);
        return result;
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
    public JSONObject terrPton(TerritoryRO terr) {
        JSONObject json = new JSONObject();
        json.put("name", terr.getName());
        json.put("terrID", terr.getTerrID());
        json.put("owner", terr.getOwnerRO().getPlayerID());
        json.put("ownerArmy", terr.getOwnerArmyRO().getArmyID());

        JSONArray neighbotList = new JSONArray();
        ArrayList<TerritoryRO> territories = terr.getNeighborListRO();
        for (TerritoryRO territoryRO: territories) {
            neighbotList.put(territoryRO.getTerrID());
        }
        json.put("neighborList", neighbotList);

        JSONArray attackArmyList = new JSONArray();
        ArrayList<ArmyRO> armyROS = terr.getAttackArmyListRO();
        if (armyROS != null) {
            for (ArmyRO armyRO : armyROS) {
                attackArmyList.put(armyRO.getArmyID());
            }
        }
        json.put("attackArmyList", attackArmyList);

        JSONObject result = new JSONObject();
        result.put("Territory", json);
        return result;
    }

    /*
    { "Army": { "owner": the id of the owner player,
                "armyID": ,
                "unitList": the number of the units
               }
    }
     */
    public JSONObject armyPton(ArmyRO army) {
        JSONObject json = new JSONObject();
        json.put("owner", army.getOwnerRO().getPlayerID());
        json.put("armyID", army.getArmyID());
        json.put("unitList", army.getUnitListSize());

        JSONObject result = new JSONObject();
        result.put("Army", json);
        return result;
    }

    /*
    { "Player": { "playerID": ,
                  "name": ,
                  "status": ,
                  "terrList": JSONArray of terrID of territories that belongs to the player
                 }
    }
     */
    public JSONObject playerPton(PlayerRO player) {
        JSONObject json = new JSONObject();
        json.put("playerID", player.getPlayerID());
        json.put("name", player.getName());
        json.put("status", player.getStatus());

        JSONArray terrList = new JSONArray();
        ArrayList<TerritoryRO> territories = player.getTerrListRO();
        for (TerritoryRO territoryRO: territories) {
            terrList.put(territoryRO.getTerrID());
        }
        json.put("terrList", terrList);

        JSONObject result = new JSONObject();
        result.put("Player", json);
        return result;
    }
}
