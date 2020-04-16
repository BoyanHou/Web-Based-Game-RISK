package RISK.Order;

import RISK.Game.Game;
import RISK.Player.Player;
import RISK.Territory.Territory;
import RISK.Unit.Spy;
import RISK.Unit.UnitLevelException;
import RISK.Utils.IntFormatException;
import org.json.JSONObject;

import java.util.HashMap;

public class ConvertSpyOrderEvo3 extends ConvertSpyOrder<JSONObject> {
    protected Territory onTerr;
    // player field is in Order class

    // construct by strings
    public ConvertSpyOrderEvo3(Game game,
                        int playerID,
                        HashMap<String, String> parameterMap)
            throws WrongOrderVersionException,
            InvalidOptionException{
        super(game, playerID, parameterMap);
    }
    // construct by ntop
    public ConvertSpyOrderEvo3(Game game, JSONObject orderN)
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

        // onTerr
        if (!parameterMap.containsKey("onTerrName")) {
            throw new WrongOrderVersionException("Cannot find attribute: \"onTerrName\" for convertSpyOrder, you might be using the wrong order version!");
        }
        String onTerrName = parameterMap.get("onTerrName");
        try {
            this.onTerr = this.lookupTerrByName(onTerrName, myTerrMap);
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
        String onTerrName = orderJSON.getString("onTerrName");
        int playerID = orderJSON.getInt("playerID");

        // construct parameter map
        HashMap<String, String> parameterMap= new HashMap<>();
        parameterMap.put("onTerrName", onTerrName);

        this.parseOrderStr(game, playerID, parameterMap);
    }

    @Override
    public JSONObject pton() {
        // orderJSON:
        //   onTerrName: String
        //   playerID: int
        JSONObject orderJSON = new JSONObject();
        orderJSON.put("onTerrName", this.onTerr.getName());
        orderJSON.put("playerID", this.player.getPlayerID());

        // orderN:
        //   type:convertSpy
        //   order:orderJSON
        JSONObject orderN = new JSONObject();
        orderN.put("type", "convertSpy");
        orderN.put("order", orderJSON);
        return orderN;
    }

    @Override
    public void verify() throws InvalidLogicException {
      /*
        1. myTerr belongs to player
        2. there is at least 1 unit in onTerr (to convert the spy from)
        3. the tech of that player owns is enough (20)
      */
        String errorString = "";

        // checkpoint 1
        if (this.onTerr.getOwner().getPlayerID() != this.player.getPlayerID()) {
            errorString += "You do not own the territory: " + this.onTerr.getName() +"\n";
        }

        // checkpoint 2
        if (!this.onTerr.ownerArmyHasAnyUnit()) {
            errorString += "Your territory : " + this.onTerr.getName() +" does not have any existing units to convert the spy from!\n";
        }

        // checkpoint 3
        int totalTechCost = 20; // 1 spy cost 20 tech
        if (totalTechCost > player.getTech()) {
            errorString += "Your spy on territory: " + onTerr.getName() + " will cost 20 tech; ";
            errorString += "however you only have " + player.getTech() +" tech\n";
        }

        if (errorString.length() != 0) {
            throw new InvalidLogicException(errorString);
        }
    }

    @Override
    public void execute() throws InvalidLogicException {
        this.verify();

        // remove 1 unit from onTerr onwer army
        try {
            this.onTerr.reduceOneLowestLevelUnit();
        } catch (InvalidOptionException e) {
            throw new InvalidLogicException(e.getMessage()); // impossible to happen, because verified at the beginning
        }

        // add spy to onTerr
        Spy spy;
        try {
            spy = new Spy();
        } catch (UnitLevelException e) {
            throw new InvalidLogicException(e.getMessage()); // this is impossible
        }
        this.onTerr.addOneSpy(this.player.getPlayerID(), spy);

        // deduct tech cost from player
        int totalTechCost = 20; // each spy cost 20 tech

        this.player.setTech(this.player.getTech() - totalTechCost);
    }

}
