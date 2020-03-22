package RISK.Territory;

import java.util.ArrayList;
import java.util.List;

import RISK.Army.Army;
import RISK.CombatResolver.BattleField;
import RISK.CombatResolver.CombatResolver;
import RISK.Player.Player;
import RISK.Utils.TerritoryNames;

public class Territory extends TerritoryRO implements BattleField{
  public Territory(int terrID, TerritoryNames name) {
    this.name = name;
    this.terrID = terrID;
    this.neighborList = null;
    this.ownerArmy = null;
    this.owner = null;
    this.attackArmyList = null;
  }

  public void setOwner(Player owner) {
    this.owner = owner;
  }

  public void setOwnerArmy(Army ownerArmy) {
    this.ownerArmy = ownerArmy;
  }

  public void setNeighborList(List<Territory> neighbor) {
    this.neighborList = new ArrayList<>(neighbor);
  }

  public void setAttackArmyList(List<Army> attackArmyList) {
    this.attackArmyList = new ArrayList<>(attackArmyList);
  }

  public Army getOwnerArmy() {
    return ownerArmy;
  }

  public int getOwerArmyUnitListSize() {
    return ownerArmy.getUnitListSize();
  }
    

  public Player getOwner() {
    return owner;
  }

  public ArrayList<Territory> getNeighborList() {
    return neighborList;
  }

  public ArrayList<Army> getAttackArmyList() {
    return attackArmyList;
  }

  @Override
  public void resolveCombat(CombatResolver combatResolver) {
    combatResolver.resolve(this);
  }
}
