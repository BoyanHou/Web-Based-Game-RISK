package RISK.Order;

import RISK.Player.Player;
import RISK.Player.PlayerRO;

/*
    Writer & Maintainer: hby

 */
public abstract class Order {

    protected Player player;

    public Player getPlayer() {
        return player;
    }

    public abstract boolean validate();

    public abstract void execute();
}
