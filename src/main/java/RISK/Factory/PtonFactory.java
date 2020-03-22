package RISK.Factory;

import RISK.Army.ArmyRO;
import RISK.Order.AttackOrder;
import RISK.Order.MoveOrder;
import RISK.Player.PlayerRO;
import RISK.Territory.TerritoryRO;

public interface PtonFactory<T> {
    T movePton(MoveOrder moveOrder);

    T attackPton(AttackOrder attackOrder);

    T terrPton(TerritoryRO terr);

    T armyPton(ArmyRO army);

    T playerPton(PlayerRO player);
}
