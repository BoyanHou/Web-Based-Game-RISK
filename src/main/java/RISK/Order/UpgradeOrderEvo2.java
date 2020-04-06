package RISK.Order;

import RISK.Game.Game;
import RISK.Player.Player;
import RISK.Territory.Territory;
import RISK.Unit.Unit;
import RISK.Unit.UnitLevelException;
import RISK.Unit.UnitLevelMapper;
import RISK.Utils.IntFormatException;
import RISK.Utils.NumUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class UpgradeOrderEvo2 extends UpgradeOrder<JSONObject> {
    protected int fromLevel;
    protected int toLevel;
    Territory onTerr;
    // player field is in Order class

    // construct by strings
    public UpgradeOrderEvo2(Game game,
                           int playerID,
                           HashMap<String, String> unitMapStr)
            throws WrongOrderVersionException,
            InvalidOptionException{
        super(game, playerID, unitMapStr);
    }
    // construct by ntop
    public UpgradeOrderEvo2(Game game, JSONObject orderN)
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
        HashMap<Integer, Territory> myTerrMap = player.getTerrMap();
        String errorStr = "";

        // onTerr
        if (!parameterMap.containsKey("onTerrName")) {
            throw new WrongOrderVersionException("Cannot find attribute: \"onTerrName\" for upgradeOrder, you might be using the wrong order version!");
        }
        String onTerrName = parameterMap.get("onTerrName");
        try {
            this.onTerr = this.lookupTerrByName(onTerrName, myTerrMap);
        } catch (InvalidOptionException e) {
            errorStr += "Invalid Option: " + e.getMessage() + "\n";
        }

        // fromLevel
        if (!parameterMap.containsKey("fromLevel")) {
            throw new WrongOrderVersionException("Cannot find attribute: \"fromLevel\" for upgradeOrder, you might be using the wrong order version!");
        }
        String fromLevel = parameterMap.get("fromLevel");
        try {
            this.fromLevel = NumUtils.strToInt(fromLevel);
        } catch (IntFormatException e) {
            errorStr += "Invalid Option: " + fromLevel + " is not a valid integer level!\n";
        }

        // toLevel
        if (!parameterMap.containsKey("toLevel")) {
            throw new WrongOrderVersionException("Cannot find attribute: \"toLevel\" for upgradeOrder, you might be using the wrong order version!");
        }
        String toLevel = parameterMap.get("toLevel");
        try {
            this.toLevel = NumUtils.strToInt(toLevel);
        } catch (IntFormatException e) {
            errorStr += "Invalid Option: " + toLevel + " is not a valid integer level!\n";
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
        String fromLevelStr = orderJSON.getString("fromLevel");
        String toLevelStr = orderJSON.getString("toLevel");
        String onTerrName = orderJSON.getString("onTerrName");
        int playerID = orderJSON.getInt("playerID");

        // construct parameter map
        HashMap<String, String> parameterMap= new HashMap<>();
        parameterMap.put("fromLevel", fromLevelStr);
        parameterMap.put("toLevel", toLevelStr);
        parameterMap.put("onTerrName", onTerrName);

        this.parseOrderStr(game, playerID, parameterMap);
    }

    @Override
    public JSONObject pton() {
        // orderJSON:
        //   fromLevel: String
        //   toLevel: String
        //   onTerrName: String
        //   playerID: int
        JSONObject orderJSON = new JSONObject();
        orderJSON.put("onTerrName", this.onTerr.getName());
        String fromLevelStr = Integer.toString(this.fromLevel);
        orderJSON.put("fromLevel", fromLevelStr);
        String toLevelStr = Integer.toString(this.toLevel);
        orderJSON.put("toLevel", toLevelStr);
        orderJSON.put("playerID", this.player.getPlayerID());

        // orderN:
        //   type:upgrade
        //   order:orderJSON
        JSONObject orderN = new JSONObject();
        orderN.put("type", "upgrade");
        orderN.put("order", orderJSON);
        return orderJSON;
    }

    @Override
    public void verify() throws InvalidLogicException {
      /*
        1. onTerr belongs to player
        2. there is at least one unit on "onTerr" with level "fromLevel"
        3. fromLevel < toLevel
        4. to level is a pre-defined level
        5. the tech of that player owns is enough
      */
        String errorString = "";

        // checkpoint 1
        if (this.onTerr.getOwner().getPlayerID() != this.player.getPlayerID()) {
            errorString += "You do not own the territory: " + this.onTerr.getName() +"\n";
        }

        // checkpoint 2
        HashMap<Integer, ArrayList<Unit>> terrUnitMap = this.onTerr.getOwnerArmy().getUnitMap();
        if (!terrUnitMap.containsKey(fromLevel) ||
                !(terrUnitMap.get(fromLevel).size() > 0)) {
            errorString += "You do not have any level" + fromLevel + " unit(s) on "+ onTerr.getName() +"\n";
        }

        // checkpoint 3
        if(fromLevel >= toLevel){
            errorString += fromLevel + ">=" + toLevel +", you must upgrade to a higher level\n";
        }

        // checkpoint 4
        try {
            UnitLevelMapper.mapCost(toLevel);
        } catch (UnitLevelException e) {
            errorString += e.getMessage() + "\n";
        }

        // checkpoint 5
        int cost = 0;
        try {
            cost = this.getCost(this.fromLevel, this.toLevel);
        } catch(InvalidOptionException e) {
            errorString += e.getMessage() + "\n";
        }
        if (this.player.getTech() < cost) {
            errorString += "You need " + cost + " tech points to perform this upgrade; ";
            errorString += "However you only have " + this.player.getTech() + "\n";
        }

        if (errorString.length() != 0) {
            throw new InvalidLogicException(errorString);
        }
        return;
    }

    // helper function
    // calculate the cost of one upgrade
    protected int getCost(int fromLevel, int toLevel) throws InvalidOptionException {
        String errorString = "";
        int oldWorth = 0;
        try {
            oldWorth = UnitLevelMapper.mapCost(fromLevel);
        } catch (UnitLevelException e) {
            errorString += e.getMessage() + "\n";
        }
        int newWorth = 0;
        try {
            newWorth = UnitLevelMapper.mapCost(toLevel);
        } catch (UnitLevelException e) {
            errorString += e.getMessage() + "\n";
        }
        if (errorString.length() != 0) {
            throw new InvalidOptionException(errorString);
        }
        int cost = newWorth - oldWorth;
        return cost;
    }
    @Override
    public void execute() throws InvalidLogicException {
        // perform the upgrade on a valid unit, on the specified territory
        String errorStr = "";
        this.verify();
        try {
            this.onTerr.upgradeUnit(this.fromLevel, this.toLevel);
        } catch (InvalidOptionException e) {
            errorStr += e.getMessage() + "\n";
        }

        // deduct the tech point from player
        int cost = 0;
        try {
            this.getCost(this.fromLevel, this.toLevel);
        } catch (InvalidOptionException e) {
            errorStr += e.getMessage() + "\n";
        }
        this.player.setTech(this.player.getTech() - cost);

        if (errorStr.length() != 0) {
            throw new InvalidLogicException(errorStr);
        }
        return;
    }
}
