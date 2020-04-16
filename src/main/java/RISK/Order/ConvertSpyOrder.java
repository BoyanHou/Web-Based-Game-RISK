package RISK.Order;

import RISK.Game.Game;

import java.util.HashMap;

public abstract class ConvertSpyOrder<T> extends Order<T>{
    // these constructors are private because they are not supposed to be used publicly;
    // use the further-specific classes' constructors instead

    // construct from strings
    protected ConvertSpyOrder(Game game,
                          int playerID,
                          HashMap<String, String> parameterMap)
            throws WrongOrderVersionException,
            InvalidOptionException{
        super(game, playerID, parameterMap);
    }

    // construct order by ntop
    protected ConvertSpyOrder(Game game, T orderJSON)
            throws WrongOrderVersionException,
            InvalidOptionException{
        super(game, orderJSON);
    }
}
