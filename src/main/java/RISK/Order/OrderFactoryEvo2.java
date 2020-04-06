package RISK.Order;

import RISK.Game.Game;
import RISK.Game.GameClient;
import org.json.JSONObject;

import java.util.HashMap;

public class OrderFactoryEvo2 extends OrderFactory<JSONObject> {
    @Override
    public MoveOrder makeMoveOrder(Game game,
                                   int playerID,
                                   HashMap<String, String> parameterMap)
            throws InvalidOptionException,
            WrongOrderVersionException {
        return new MoveOrderEvo2(game, playerID ,parameterMap);
    }

    @Override
    public AttackOrder makeAttackOrder(Game game,
                                       int playerID,
                                       HashMap<String, String> parameterMap)
            throws InvalidOptionException,
            WrongOrderVersionException {
        return new AttackOrderEvo2(game, playerID, parameterMap);
    }

    @Override
    public UpgradeOrder makeUpgradeOrder(Game game,
                                         int playerID,
                                         HashMap<String, String> parameterMap)
            throws InvalidOptionException,
            WrongOrderVersionException {
        return new UpgradeOrderEvo2(game, playerID, parameterMap);
    }

    @Override
    public Order makeOrderByNtop(Game game, String orderN)
            throws ProtocolException,
            InvalidOptionException,
            WrongOrderVersionException {
        JSONObject jo = new JSONObject(orderN);
        String orderType = jo.getString("type");
        if (orderType == null) {
            throw new ProtocolException("Protocol Exception: cannot find attribute \"type\" in the given network-form order");
        }
        JSONObject orderJSON = jo.getJSONObject("order");
        if (orderJSON == null) {
            throw new ProtocolException("Protocol Exception: cannot find attribute \"order\" in the given network-form order");
        }

        if (orderType.equals("move")) {
            return new MoveOrderEvo2(game, orderJSON);
        } else if (orderType.equals("attack")) {
            return new AttackOrderEvo2(game, orderJSON);
        } else if (orderType.equals("upgrade")) {
            return new UpgradeOrderEvo2(game, orderJSON);
        } else {
            throw new ProtocolException("Protocol Exception: cannot match the order type: " + orderType + " in the given network-form order");
        }
    }
}
