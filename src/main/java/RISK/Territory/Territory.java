package RISK.Territory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import RISK.Army.Army;
import RISK.CombatResolver.BattleField;
import RISK.CombatResolver.CombatResolver;
import RISK.Order.InvalidOptionException;
import RISK.Player.Player;
import RISK.Unit.Soldier;
import RISK.Unit.Unit;
import RISK.Unit.UnitLevelException;
import RISK.Utils.TerritoryNames;

public abstract class Territory<T> extends TerritoryRO<T> implements BattleField{
  public Territory(int terrID, String name) {
    this.name = name;
    this.terrID = terrID;
    this.neighborMap =  new HashMap<>();
    this.ownerArmy = null;
    this.owner = null;
    this.attackArmyMap = new HashMap<>();
    this.unitGenMap = new HashMap<>();
    this.food = 0;
    this.tech = 0;
    this.size = 0;
  }

  public void setOwner(Player owner) {
    this.owner = owner;
  }
  public void setOwnerArmy(Army ownerArmy) {
    this.ownerArmy = ownerArmy;
  }

  public void setNeighborMap(HashMap<Integer, Territory> neighborMap) {
    this.neighborMap = (HashMap)(neighborMap);
  }
  public HashMap<Integer, Territory> getNeighborMap() {
    return this.neighborMap;
  }

  public void setUnitGenMap(Map<Integer, Integer> unitGenMap) {
    this.unitGenMap = (HashMap)unitGenMap;
  }
  public HashMap<Integer, Integer> getUnitGenMap() {return this.unitGenMap;}

  public void setAttackArmyMap(Map<Integer, Army> attackArmyMap) {
    this.attackArmyMap = (HashMap)(attackArmyMap);
  }
  public HashMap<Integer, Army> getAttackArmyMap() {
    return this.attackArmyMap;
  }

  public void setSize(int size) {this.size = size;}
  public void setFood(int food) {this.food = food;}
  public void setTech(int tech) {this.tech = tech;}

  public Army getOwnerArmy() {
    return this.ownerArmy;
  }
  public Player getOwner() {return this.owner;}

  @Override
  public void resolveCombat(CombatResolver combatResolver) {
      combatResolver.resolve(this);
  }

  public void generateUnits() throws UnitLevelException {
    for (int level : this.unitGenMap.keySet()) {
      for (int num = 0; num < this.unitGenMap.get(level); num++) {
        Unit unit = new Soldier(level);
        this.getOwnerArmy().addUnit(unit);
      }
    }
  }

  public void addAttackArmy(Army army) {
    int attackArmyOwnerID = army.getOwner().getPlayerID();
    if (this.attackArmyMap.containsKey(attackArmyOwnerID)) {
      Army existingArmy = attackArmyMap.get(attackArmyOwnerID);
      existingArmy.absorb(army);
    } else {
      this.attackArmyMap.put(attackArmyOwnerID, army);
    }
  }

  // form an army from the existing owner army on this territory
  public void formArmy(HashMap<Integer, Integer> unitMapInt,
                       Army armyContainer) {
    for (int level : unitMapInt.keySet()) {
      int levelTotal = unitMapInt.get(level);
      for (int num = 0; num <levelTotal; num++ ) {
        // get units from end of ArrayList
        ArrayList<Unit> units = (ArrayList<Unit>) this.ownerArmy.getUnitMap().get(level);
        int endIndex = units.size() - 1;
        armyContainer.addUnit(units.get(endIndex));
        units.remove(endIndex);
      }
    }
  }

  // find a unit that matches the fromLevel on this territory
  // upgrade it to the toLevel
  public void upgradeUnit(int fromLevel, int toLevel)
  throws InvalidOptionException{
    HashMap<Integer, ArrayList<Unit>> unitMap = this.ownerArmy.getUnitMap();
    // if no such unit: exception
    if (!this.ownerArmy.getUnitMap().containsKey(fromLevel) ||
            unitMap.get(fromLevel).size() == 0) {
      throw new InvalidOptionException("No level" + fromLevel +" unit on " + this.getName());
    }
    ArrayList<Unit> units = unitMap.get(fromLevel);
    int unitIndex = units.size() - 1;
    Unit unit = units.get(unitIndex);

    try {
      unit.setLevel(toLevel);
      // move unit to new level list
      if (!unitMap.containsKey(toLevel)) {
        ArrayList<Unit> newLevelList = new ArrayList<>();
        unitMap.put(toLevel, newLevelList);
      }
      unitMap.get(fromLevel).remove(unitIndex); // remove from old list
      unitMap.get(toLevel).add(unit);           // add to new list
    } catch (UnitLevelException e) {
      throw new InvalidOptionException(e.getMessage());
    }
    return;
  }
  public abstract T pton();
}
