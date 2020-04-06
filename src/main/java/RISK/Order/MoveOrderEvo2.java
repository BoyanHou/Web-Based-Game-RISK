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
import java.util.HashSet;

public class MoveOrderEvo2 extends MoveOrder<JSONObject>{

    protected Territory fromTerr;
    protected Territory toTerr;
    protected HashMap<Integer, Integer> unitMap; // level:num
    // player field is in Order class

    // construct by strings
    public MoveOrderEvo2(Game game,
                         int playerID,
                         HashMap<String, String> unitMapStr)
            throws WrongOrderVersionException,
            InvalidOptionException{
        super(game, playerID, unitMapStr);
    }
    // construct by ntop
    public MoveOrderEvo2(Game game, JSONObject orderN)
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

        // fromTerr
        if (!parameterMap.containsKey("fromTerrName")) {
            throw new WrongOrderVersionException("Cannot find attribute: \"fromTerrName\" for moveOrder, you might be using the wrong order version!");
        }
        String fromTerrName = parameterMap.get("fromTerrName");
        try {
            this.fromTerr = this.lookupTerrByName(fromTerrName, myTerrMap);
        } catch (InvalidOptionException e) {
            errorStr += "Invalid Option: " + e.getMessage() + "\n";
        }

        // toTerr
        if (!parameterMap.containsKey("toTerrName")) {
            throw new WrongOrderVersionException("Cannot find attribute: \"toTerrName\" for moveOrder, you might be using the wrong order version!");
        }
        String toTerrName = parameterMap.get("toTerrName");
        try {
            this.toTerr = this.lookupTerrByName(toTerrName, myTerrMap);
        } catch (InvalidOptionException e) {
            errorStr += "Invalid Option: " + e.getMessage() + "\n";
        }

        // unitMap
        HashMap<String, String> unitMapStr = new HashMap<>();
        for (String key : parameterMap.keySet()) {
            if (!key.equals("fromTerrName") &&
                    !key.equals("toTerrName")) {
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

        String fromTerrName = orderJSON.getString("fromTerrName");
        String toTerrName = orderJSON.getString("toTerrName");
        JSONObject unitMapJSON = orderJSON.getJSONObject("unitMap");
        int playerID = orderJSON.getInt("playerID");

        // construct parameter map
        HashMap<String, String> parameterMap= new HashMap<>();
        parameterMap.put("fromTerrName", fromTerrName);
        parameterMap.put("toTerrName", toTerrName);
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
        //   fromTerrName: String
        //   toTerrName: String
        //   unitMap: JSONObject (levelStr : numStr)
        //   playerID: int
        JSONObject orderJSON = new JSONObject();
        orderJSON.put("fromTerrName", this.fromTerr.getName());
        orderJSON.put("toTerrName", this.toTerr.getName());
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
        //   type:move
        //   order:orderJSON
        JSONObject orderN = new JSONObject();
        orderN.put("type", "move");
        orderN.put("order", orderJSON);
        return orderN;
    }

    @Override
    public void verify() throws InvalidLogicException {
      /*
        1. number of units, of each level, is enough on fromTerr
        2. fromTerr belongs to player
        3. toTerr also belongs to player
        4. fromTerr & toTerr are connected
        5. the food of that player owns is enough
      */
        String errorString = "";

        // checkpoint 1
        HashMap<Integer, ArrayList<Unit>> fromTerrUnitMap = this.fromTerr.getOwnerArmy().getUnitMap();
        for (int level : this.unitMap.keySet()) {
            if (!fromTerrUnitMap.containsKey(level)) {
                errorString += "Your move order requires level" + level + " unit(s), ";
                errorString += "however you do not have any on territory " + this.fromTerr.getName() + "\n";
            } else {
                int requiresNum = this.unitMap.get(level);
                int actualNum = fromTerrUnitMap.get(level).size();
                if (actualNum< requiresNum) {
                    errorString += "Your move order requires " + requiresNum +" level" + level + " unit(s), ";
                    errorString += "however you only have " + actualNum + " on territory " + this.fromTerr.getName() + "\n";
                }
            }
        }

        // checkpoint 2
        if (this.fromTerr.getOwner().getPlayerID() != this.player.getPlayerID()) {
            errorString += "You do not own the territory: " + this.fromTerr.getName() +"\n";
        }

        // checkpoint 3
        if (this.toTerr.getOwner().getPlayerID() != this.player.getPlayerID()) {
            errorString += "You do not own the territory: " + this.toTerr.getName() +"\n";
        }

        // checkpoint 4
        if(!isConnected(this.fromTerr, this.toTerr)){
            errorString += this.fromTerr.getName() + " and " + this.toTerr.getName();
            errorString += " are not connected Territories, cannot move!\n";
        }

        // checkpoint 5
        int totalUnitNum = 0;
        for (int level : this.unitMap.keySet()) {
            totalUnitNum += this.unitMap.get(level);
        }
        int leastCost = 0;
        try {
            leastCost = this.getLeastCost(this.fromTerr, this.toTerr);
        } catch (InvalidLogicException e) {
            // do nothing: not-connected case has been dealt with in checkpoint 4
        }
        int totalFoodCost = totalUnitNum * leastCost; // use the least cost for units moving
        if (totalFoodCost > player.getFood()) {
            errorString += "Your moving army of " + totalUnitNum + " will cost " + totalFoodCost + "food, ";
            errorString += "however you only have " + player.getFood() +" food\n";
        }

        if (errorString.length() != 0) {
            throw new InvalidLogicException(errorString);
        }
    }

    // check if two territories are connected
    protected boolean isConnected(Territory fromTerr, Territory toTerr) {
        try {
            this.getLeastCost(fromTerr, toTerr);
        } catch (InvalidLogicException e) {
            return false;
        }
        return true;
    }

    protected int getLeastCost(Territory fromTerr, Territory toTerr)
    throws InvalidLogicException{
        HashSet<Integer> passedTerrIDs = new HashSet<>();
        return this.getLeastCostRecursive(fromTerr, toTerr, passedTerrIDs);
    }

    protected int getLeastCostRecursive(Territory fromTerr,
                                        Territory toTerr,
                                        HashSet<Integer> passedTerrIDs)
            throws InvalidLogicException {
        if (fromTerr.getTerrID() == toTerr.getTerrID()) {
            return toTerr.getSize();   // count the end terr cost
        }
        passedTerrIDs.add(fromTerr.getTerrID());
        int nextLevelMinCost = -1;
        HashMap<Integer, Territory> fromTerrNeighborMap = fromTerr.getNeighborMap();
        for (int terrID : fromTerrNeighborMap.keySet()) {
            if (!passedTerrIDs.contains(terrID)) {
                Territory nextStop = fromTerrNeighborMap.get(terrID);
                int nextLevelCost = -1;
                try {
                    nextLevelCost = this.getLeastCostRecursive(nextStop, toTerr, passedTerrIDs);
                    if (nextLevelMinCost == -1 || nextLevelCost <= nextLevelMinCost) {
                        nextLevelMinCost = nextLevelCost;
                    }
                } catch (InvalidLogicException e) {
                    // do nothing
                }
            }
        }
        passedTerrIDs.remove(fromTerr);
        if (nextLevelMinCost == -1) {
            throw new InvalidLogicException("There is no connected path from "+fromTerr.getName() + " to " + toTerr.getName());
        }
        return nextLevelMinCost + fromTerr.getSize();
    }

    @Override
    public void execute() throws InvalidLogicException {
        this.verify();
        // move army from one territory to another
        Army moveArmy = new ArmyJSON(-1);
        this.fromTerr.formArmy(this.unitMap, moveArmy);
        this.toTerr.getOwnerArmy().absorb(moveArmy);

        // deduct food cost from player
        int totalUnits = 0;
        for (int level : this.unitMap.keySet()) {
            totalUnits += this.unitMap.get(level);
        }
        int leastCost = this.getLeastCost(this.fromTerr, this.toTerr);
        int totalFoodCost = leastCost * totalUnits; // use least cost path for moving units
        this.player.setFood(this.player.getFood() - totalFoodCost);
    }
}
