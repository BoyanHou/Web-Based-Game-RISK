package RISK.Order;

import RISK.Army.Army;
import RISK.Army.ArmyJSON;
import RISK.Game.Game;
import RISK.Player.Player;
import RISK.Territory.Territory;
import RISK.Unit.Spy;
import RISK.Unit.Unit;
import RISK.Utils.MsgException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MoveSpyOrderEvo3 extends MoveSpyOrder<JSONObject>{
    protected Territory fromTerr;
    protected Territory toTerr;
    // player field is in Order class

    // construct by strings
    public MoveSpyOrderEvo3(Game game,
                           int playerID,
                           HashMap<String, String> parameterMap)
            throws WrongOrderVersionException,
            InvalidOptionException{
        super(game, playerID, parameterMap);
    }
    // construct by ntop
    public MoveSpyOrderEvo3(Game game, JSONObject orderN)
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
            throw new WrongOrderVersionException("Cannot find attribute: \"fromTerrName\" for moveSpyOrder, you might be using the wrong order version!");
        }
        String fromTerrName = parameterMap.get("fromTerrName");
        try {
            this.fromTerr = this.lookupTerrByName(fromTerrName, game.getTerrMap());
        } catch (InvalidOptionException e) {
            errorStr += "Invalid Option: " + e.getMessage() + "\n";
        }

        // toTerr
        if (!parameterMap.containsKey("toTerrName")) {
            throw new WrongOrderVersionException("Cannot find attribute: \"toTerrName\" for attackOrder, you might be using the wrong order version!");
        }
        String toTerrName = parameterMap.get("toTerrName");
        try {
            this.toTerr = this.lookupTerrByName(toTerrName, game.getTerrMap());
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
        int playerID = orderJSON.getInt("playerID");

        // construct parameter map
        HashMap<String, String> parameterMap= new HashMap<>();
        parameterMap.put("fromTerrName", fromTerrName);
        parameterMap.put("toTerrName", toTerrName);

        this.parseOrderStr(game, playerID, parameterMap);
    }

    @Override
    public JSONObject pton() {
        // orderJSON:
        //   fromTerrName: String
        //   toTerrName: String
        //   playerID: int
        JSONObject orderJSON = new JSONObject();
        orderJSON.put("fromTerrName", this.fromTerr.getName());
        orderJSON.put("toTerrName", this.toTerr.getName());
        orderJSON.put("playerID", this.player.getPlayerID());

        // orderN:
        //   type:moveSpy
        //   order:orderJSON
        JSONObject orderN = new JSONObject();
        orderN.put("type", "moveSpy");
        orderN.put("order", orderJSON);
        return orderN;
    }

    @Override
    public void verify() throws InvalidLogicException {
      /*
        1. there is at least 1 spy (belongs to the player) on fromTerr
        2. fromTerr & toTerr are adjacent
        3. the food of that player owns is enough
      */
        String errorString = "";

        // checkpoint 1
        if (!this.fromTerr.hasAnySpyOfPlayer(this.player.getPlayerID())) {
            errorString += "You do not have any spy on terr:" + this.fromTerr.getName() + "\n";
        }

        // checkpoint 2
        if(!isAdjacent(this.fromTerr, this.toTerr)){
            errorString += this.fromTerr.getName() + " and " + this.toTerr.getName();
            errorString += " are not adjacent Territories, cannot move the spy!\n";
        }

        // checkpoint 3
        int totalFoodCost = toTerr.getSize() + fromTerr.getSize();
        if (totalFoodCost > player.getFood()) {
            errorString += "Your moving spy will cost " + totalFoodCost + "food, ";
            errorString += "however you only have " + player.getFood() +" food\n";
        }

        if (errorString.length() != 0) {
            throw new InvalidLogicException(errorString);
        }
    }

    // check if two territories are adjacent
    protected boolean isAdjacent(Territory fromTerr, Territory toTerr){
        if (fromTerr.getNeighborMap().containsKey(toTerr.getTerrID())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void execute() throws InvalidLogicException {
        this.verify();
        try {
            // move 1 spy from fromTerr to toTerr
            Spy spy = fromTerr.reduceOneSpy(this.player.getPlayerID());
            toTerr.addOneSpy(this.player.getPlayerID(), spy);
        } catch (MsgException e) {
            throw new InvalidLogicException(e.getMessage()); // impossible to happen, because we verified at the beginning
        }

        // deduct food cost from player
        int totalFoodCost = this.fromTerr.getSize() + this.toTerr.getSize();
        this.player.setFood(this.player.getFood() - totalFoodCost);
    }
}
