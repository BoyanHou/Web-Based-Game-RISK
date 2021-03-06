package RISK.Order;

import RISK.Game.Game;
import RISK.Player.Player;
import RISK.Territory.Territory;
import RISK.Unit.Soldier;
import RISK.Unit.Unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public abstract class MoveOrder<T> extends Order<T> {
    // these constructors are private because they are not supposed to be used publicly;
    // use the further-specific classes' constructors instead

    // construct from strings
    protected MoveOrder(Game game,
                        int playerID,
                        HashMap<String, String> parameterMap)
            throws WrongOrderVersionException,
            InvalidOptionException{
        super(game, playerID, parameterMap);
    }

    // construct order by ntop
    protected MoveOrder(Game game, T orderJSON)
            throws WrongOrderVersionException,
            InvalidOptionException{
        super(game, orderJSON);
    }
}
