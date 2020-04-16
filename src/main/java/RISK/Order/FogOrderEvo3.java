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

public class FogOrderEvo3 extends FogOrder<JSONObject>{
    protected Territory onTerr;
    // player field is in Order class

    // construct by strings
    public FogOrderEvo3(Game game,
                           int playerID,
                           HashMap<String, String> parameterMap)
            throws WrongOrderVersionException,
            InvalidOptionException{
        super(game, playerID, parameterMap);
    }
    // construct by ntop
    public FogOrderEvo3(Game game, JSONObject orderN)
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
            throw new WrongOrderVersionException("Cannot find attribute: \"onTerrName\" for fogOrder, you might be using the wrong order version!");
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
        //   type:fog
        //   order:orderJSON
        JSONObject orderN = new JSONObject();
        orderN.put("type", "fog");
        orderN.put("order", orderJSON);
        return orderN;
    }

    @Override
    public void verify() throws InvalidLogicException {
      /*
        1. onTerr belongs to player
        2. There is NOT already a fog at onTerr
        3. the tech of that player owns is enough (20)
      */
        String errorString = "";

        // checkpoint 1
        if (this.onTerr.getOwner().getPlayerID() != this.player.getPlayerID()) {
            errorString += "You do not own the territory: " + this.onTerr.getName() +"\n";
        }

        // checkpoint 2
        if (this.onTerr.isFogged()) {
            errorString += "The territory : " + this.onTerr.getName() +" has already been cloaked!\n";
        }

        // checkpoint 3
        int totalTechCost = 20; // 1 fog cost 20 tech
        if (totalTechCost > player.getTech()) {
            errorString += "Your cloaking on territory: " + onTerr.getName() + " will cost 20 tech; ";
            errorString += "however you only have " + player.getTech() +" tech\n";
        }

        if (errorString.length() != 0) {
            throw new InvalidLogicException(errorString);
        }
    }

    @Override
    public void execute() throws InvalidLogicException {
        this.verify();

        // add fog to onTerr
        this.onTerr.setFogged(true);

        // deduct tech cost from player
        int totalTechCost = 20; // each fog cost 20 tech
        this.player.setTech(this.player.getTech() - totalTechCost);
    }
}
