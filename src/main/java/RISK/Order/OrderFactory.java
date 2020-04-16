package RISK.Order;

import RISK.Game.Game;
import RISK.Game.GameClient;

import java.util.HashMap;

public abstract class OrderFactory<T> {

    public abstract Order makeOrderByNtop (Game game, String orderN) throws ProtocolException, InvalidOptionException, WrongOrderVersionException;

    public abstract Order makeOrderByStrings(Game game,
                                    int playerID,
                                    HashMap<String, String> parameterMap,
                                    String orderType)
            throws InvalidOptionException, WrongOrderVersionException;
}
