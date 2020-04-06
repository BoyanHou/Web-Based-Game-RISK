package RISK.Order;

import RISK.Game.Game;
import RISK.Player.Player;
import RISK.Territory.Territory;
import RISK.Utils.IntFormatException;
import RISK.Utils.NumUtils;

import java.util.HashMap;

/*
    Writer & Maintainer: hby

 */
public abstract class Order<T> {

    protected Player player;

    // construct order by ntop
    public Order(Game game, T orderT)
            throws WrongOrderVersionException,
                InvalidOptionException{
        this.ntop(game, orderT);
    }

    // construct order from strings
    public Order(Game game,
                 int playerID,
                 HashMap<String, String> parameterMap)
        throws WrongOrderVersionException,
                InvalidOptionException{
        this.player = game.getPlayerMap().get(playerID);
        this.parseOrderStr(game, playerID, parameterMap);
    }

    // parse input strings as order
    protected abstract void parseOrderStr(Game game,
                                          int playerID,
                                          HashMap<String, String> parameterMap)
            throws WrongOrderVersionException,
            InvalidOptionException;

    // convert order from "network" to "presentation"
    protected abstract void ntop(Game game, T orderT)
            throws WrongOrderVersionException,
                InvalidOptionException;

    // convert order from "presentation" to "network"
    public abstract T pton();

    // verify this order
    public abstract void verify() throws InvalidLogicException;

    // execute this order; verify it first
    public abstract void execute() throws InvalidLogicException;

    // a helper function, name says it all
    protected Territory lookupTerrByName(String terrName,
                                         HashMap<Integer, Territory> terrMap)
            throws InvalidOptionException {
        for (Territory terr : terrMap.values()) {
            if (terr.getName().equals(terrName)) {
                return terr;
            }
        }
        throw new InvalidOptionException("No territory called \"" + terrName + "\" found in valid range");
    }
    // another helper function
    // build unit map from <string, string> to <int, int>
    protected HashMap<Integer, Integer> buildUnitMap(HashMap<String, String> unitMapStr)
            throws InvalidOptionException {
        HashMap<Integer, Integer> unitMap = new HashMap<>();
        for (String levelStr : unitMapStr.keySet()) {
            try {
                int level = NumUtils.strToInt(levelStr);
                String numStr = unitMapStr.get(levelStr);
                int num = NumUtils.strToInt(numStr);
                unitMap.put(level, num);
            } catch (IntFormatException e) {
                throw new InvalidOptionException("Wrong Integer Format");
            }
        }
        return unitMap;
    }
}
