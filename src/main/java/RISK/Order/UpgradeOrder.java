package RISK.Order;

import RISK.Game.Game;

import java.util.HashMap;

public abstract class UpgradeOrder<T> extends Order<T> {
    // these constructors are private because they are not supposed to be used publicly;
    // use the further-specific classes' constructors instead

    // construct from strings
    protected UpgradeOrder(Game game,
                           int playerID,
                           HashMap<String, String> parameterMap)
            throws WrongOrderVersionException,
            InvalidOptionException{
        super(game, playerID, parameterMap);
    }

    // construct order by ntop
    protected UpgradeOrder(Game game, T orderJSON)
            throws WrongOrderVersionException,
            InvalidOptionException{
        super(game, orderJSON);
    }
}
