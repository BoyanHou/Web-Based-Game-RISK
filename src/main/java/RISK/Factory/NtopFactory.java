package RISK.Factory;

import RISK.Army.Army;
import RISK.Order.AttackOrder;
import RISK.Order.MoveOrder;
import RISK.Player.Player;
import RISK.Territory.Territory;
import RISK.Game.Game;

import java.util.HashMap;

/*
Read a JSON and return a RO object
fg */
public interface NtopFactory<T> {

  public MoveOrder moveNtop(T moveOrderN, Game game);
  
  public AttackOrder attackNtop(T atkOrderN, Game game);

  public Territory terrNtop(T terrN);
  
  public Army armyNtop(T armyN);
  
  public Player playerNtop(T playerN);
}

