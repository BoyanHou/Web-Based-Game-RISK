package RISK.Order;

import RISK.Game.Game;
import RISK.Game.GameClient;

import java.util.HashMap;

public abstract class OrderFactory<T> {
    public abstract MoveOrder makeMoveOrder(Game game,
                                   int playerID,
                                   HashMap<String, String> parameterMap)
            throws InvalidOptionException, WrongOrderVersionException;

    public abstract AttackOrder makeAttackOrder(Game game,
                                       int playerID,
                                       HashMap<String, String> parameterMap)
            throws InvalidOptionException, WrongOrderVersionException;

    public abstract UpgradeOrder makeUpgradeOrder(Game game,
                                         int playerID,
                                         HashMap<String, String> parameterMap)
            throws InvalidOptionException, WrongOrderVersionException;

    public abstract Order makeOrderByNtop (Game game, String orderN) throws ProtocolException, InvalidOptionException, WrongOrderVersionException;

    public Order makeOrderByStrings(Game game,
                                    int playerID,
                                    HashMap<String, String> parameterMap,
                                    String orderType)
            throws InvalidOptionException, WrongOrderVersionException{

        if (orderType.equals("move")) {
            return this.makeMoveOrder(game, playerID, parameterMap);
        } else if (orderType.equals("attack")) {
            return this.makeAttackOrder(game, playerID, parameterMap);
        } else if (orderType.equals("upgrade")) {
            return this.makeUpgradeOrder(game, playerID, parameterMap);
        } else {
            throw new InvalidOptionException("Order Factory cannot that order type: " + orderType);
        }

    }
}
