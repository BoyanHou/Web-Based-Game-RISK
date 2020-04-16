package RISK.Order;

import RISK.Army.Army;
import RISK.Army.ArmyJSON;
import RISK.Game.Game;
import RISK.Player.Player;
import RISK.Territory.Territory;
import RISK.Unit.Unit;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class AttackOrderEvo2 extends AttackOrder<JSONObject> {
    protected Territory myTerr;
    protected Territory targetTerr;
    protected HashMap<Integer, Integer> unitMap;
    // player field is in Order class

    // construct by strings
    public AttackOrderEvo2(Game game,
                           int playerID,
                           HashMap<String, String> parameterMap)
    throws WrongOrderVersionException,
            InvalidOptionException{
        super(game, playerID, parameterMap);
    }
    // construct by ntop
    public AttackOrderEvo2(Game game, JSONObject orderN)
            throws WrongOrderVersionException,
                InvalidOptionException{
        super(game, orderN);
    }

    @Override
    protected void parseOrderStr(Game game,
                                 int playerID,
                                 HashMap<String, String> parameterMap)
            throws WrongOrderVersionException,
                    InvalidOptionException {
        Player player = game.getPlayerMap().get(playerID);
        this.player = player;
        HashMap<Integer, Territory> myTerrMap = player.getTerrMap();
        String errorStr = "";

        // myTerr
        if (!parameterMap.containsKey("myTerrName")) {
            throw new WrongOrderVersionException("Cannot find attribute: \"myTerrName\" for attackOrder, you might be using the wrong order version!");
        }
        String myTerrName = parameterMap.get("myTerrName");
        try {
            this.myTerr = this.lookupTerrByName(myTerrName, myTerrMap);
        } catch (InvalidOptionException e) {
            errorStr += "Invalid Option: " + e.getMessage() + "\n";
        }

        // targetTerr
        if (!parameterMap.containsKey("targetTerrName")) {
            throw new WrongOrderVersionException("Cannot find attribute: \"targetTerrName\" for attackOrder, you might be using the wrong order version!");
        }
        String targetTerrName = parameterMap.get("targetTerrName");
        try {
            this.targetTerr = this.lookupTerrByName(targetTerrName, game.getTerrMap());
        } catch (InvalidOptionException e) {
            errorStr += "Invalid Option: " + e.getMessage() + "\n";
        }

        // unitMap
        HashMap<String, String> unitMapStr = new HashMap<>();
        for (String key : parameterMap.keySet()) {
            if (!key.equals("targetTerrName") &&
                !key.equals("myTerrName")) {
                unitMapStr.put(key, parameterMap.get(key));
            }
        }
        try {
            this.unitMap = this.buildUnitMap(unitMapStr);
        } catch (InvalidOptionException e) {
            errorStr += "Invalid Option: " + e.getMessage() + "\n";
        }

        if (errorStr.length() != 0) {
            throw new InvalidOptionException(errorStr);
        }
        return;
    }

    @Override
    protected void ntop(Game game, JSONObject orderJSON)
    throws WrongOrderVersionException,
            InvalidOptionException{
        String myTerrName = orderJSON.getString("myTerrName");
        String targetTerrName = orderJSON.getString("targetTerrName");
        JSONObject unitMapJSON = orderJSON.getJSONObject("unitMap");
        int playerID = orderJSON.getInt("playerID");

        // construct parameter map
        HashMap<String, String> parameterMap= new HashMap<>();
        parameterMap.put("myTerrName", myTerrName);
        parameterMap.put("targetTerrName", targetTerrName);
        for (Object key : unitMapJSON.keySet()) {
            String keyStr = (String)key;
            String valueStr = unitMapJSON.getString(keyStr);
            parameterMap.put(keyStr, valueStr);
        }

        this.parseOrderStr(game, playerID, parameterMap);
    }

    @Override
    public JSONObject pton() {
        // orderJSON:
        //   myTerrName: String
        //   targetTerrName: String
        //   unitMap: JSONObject (levelStr : numStr)
        //   playerID: int
        JSONObject orderJSON = new JSONObject();
        orderJSON.put("myTerrName", this.myTerr.getName());
        orderJSON.put("targetTerrName", this.targetTerr.getName());
        orderJSON.put("playerID", this.player.getPlayerID());
        JSONObject unitMapJSON = new JSONObject();
        for (int level : this.unitMap.keySet()) {
            String levelStr = Integer.toString(level);
            int num = this.unitMap.get(level);
            String numStr = Integer.toString(num);
            unitMapJSON.put(levelStr, numStr);
        }
        orderJSON.put("unitMap", unitMapJSON);

        // orderN:
        //   type:attack
        //   order:orderJSON
        JSONObject orderN = new JSONObject();
        orderN.put("type", "attack");
        orderN.put("order", orderJSON);
        return orderN;
    }

    @Override
    public void verify() throws InvalidLogicException {
      /*
        1. number of units, of each level, is enough on myTerr
        2. myTerr belongs to player
        3. targetTerr does not
        4. targetTerr & myTerr are adjacent
        5. the food of that player owns is enough
      */
      String errorString = "";

      // checkpoint 1
        HashMap<Integer, ArrayList<Unit>> myTerrUnitMap = myTerr.getOwnerArmy().getUnitMap();
        for (int level : this.unitMap.keySet()) {
            if (!myTerrUnitMap.containsKey(level)) {
                errorString += "Your attack order requires level" + level + " unit(s), ";
                errorString += "however you do not have any on territory " + this.myTerr.getName() + "\n";
            } else {
                int requiresNum = this.unitMap.get(level);
                int actualNum = myTerrUnitMap.get(level).size();
                if (actualNum< requiresNum) {
                    errorString += "Your attack order requires " + requiresNum +" level" + level + " unit(s), ";
                    errorString += "however you only have " + actualNum + " on territory " + this.myTerr.getName() + "\n";
                }
            }
        }

        // checkpoint 2
        if (this.myTerr.getOwner().getPlayerID() != this.player.getPlayerID()) {
            errorString += "You do not own the territory: " + this.myTerr.getName() +"\n";
        }

        // checkpoint 3
        if (this.targetTerr.getOwner().getPlayerID() == this.player.getPlayerID()) {
            errorString += "You cannot attack your own territory: " + this.targetTerr.getName() +"\n";
        }

        // checkpoint 4
        if(!isAdjacent(this.myTerr, this.targetTerr)){
            errorString += this.myTerr.getName() + " and " + this.targetTerr.getName();
            errorString += " are not adjacent Territories, cannot attack!\n";
        }

        // checkpoint 5
        int totalUnitNum = 0;
        for (int level : this.unitMap.keySet()) {
            totalUnitNum += this.unitMap.get(level);
        }
        int totalFoodCost = totalUnitNum; // attack cost 1 food per unit attacking
        if (totalFoodCost > player.getFood()) {
            errorString += "Your attacking army of " + totalUnitNum + " will cost " + totalFoodCost + "food, ";
            errorString += "however you only have " + player.getFood() +" food\n";
        }

        if (errorString.length() != 0) {
            throw new InvalidLogicException(errorString);
        }
    }

    // check if two territories are adjacent
    protected boolean isAdjacent(Territory myTerr, Territory targetTerr){
        if (myTerr.getNeighborMap().containsKey(targetTerr.getTerrID())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void execute() throws InvalidLogicException {
        this.verify();
        // add the myTerr's army to the targetTerr's attackArmy list
        Army attackArmy = new ArmyJSON(-1);
        this.myTerr.formArmy(this.unitMap, attackArmy);
        attackArmy.setOwner(this.player);
        this.targetTerr.addAttackArmy(attackArmy);

        // deduct food cost from player
        int totalUnits = 0;
        for (int level : this.unitMap.keySet()) {
            totalUnits += this.unitMap.get(level);
        }
        int totalFoodCost = 1 * totalUnits; // each attack unit cost 1 food
        this.player.setFood(this.player.getFood() - totalFoodCost);
    }
}
