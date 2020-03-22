package RISK.Player;


import java.util.ArrayList;

import RISK.Territory.Territory;
import RISK.Territory.TerritoryRO;
import RISK.Utils.Status;

/*
    Writer & Maintainer: hby
 */
public class PlayerRO {

    protected int playerID;
    protected String name;
    protected Status status;
    protected ArrayList<Territory> terrList;

    public int getPlayerID() {
        return playerID;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    public ArrayList<TerritoryRO> getTerrListRO() {
        return new ArrayList<>(terrList);
    }
}
