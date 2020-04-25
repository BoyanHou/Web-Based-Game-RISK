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
import RISK.Unit.*;
import RISK.Utils.TerritoryNames;
import org.json.JSONObject;

public abstract class Territory<T> extends TerritoryRO<T> implements BattleField{

  public Territory() {}

  public Territory(int terrID, String name) {
    this.terrID = terrID;
    this.name = name;
    this.size = 0;
    this.food = 0;
    this.tech = 0;
    this.outdated = false;
    this.fogged = false;
    this.unitGenMap = new HashMap<>();

    this.owner = null;
    this.ownerArmy = null;
    this.neighborMap =  new HashMap<>();
    this.attackArmyMap = new HashMap<>();
    this.spyMap = new HashMap<>();
  }
  //////////////
  /// getters
  //////////////
  public HashMap<Integer, Integer> getUnitGenMap() {return this.unitGenMap;}

  public Player getOwner() {return this.owner;}
  public Army getOwnerArmy() {return this.ownerArmy;}
  public HashMap<Integer, Territory> getNeighborMap() {return this.neighborMap;}
  public HashMap<Integer, Army> getAttackArmyMap() {return this.attackArmyMap;}
  public HashMap<Integer, ArrayList<Spy>> getSpyMap() {return this.spyMap;}

  ///////////////
  ///  setters
  ///////////////
  public void setSize(int size) {this.size = size;}
  public void setFood(int food) {this.food = food;}
  public void setTech(int tech) {this.tech = tech;}
  public void setOutdated(boolean outdated) {this.outdated = outdated;}
  public void setFogged(boolean fogged) {this.fogged = fogged;}
  public void setUnitGenMap(Map<Integer, Integer> unitGenMap) {this.unitGenMap = (HashMap)unitGenMap;}

  public void setOwner(Player owner) {this.owner = owner;}
  public void setOwnerArmy(Army ownerArmy) {this.ownerArmy = ownerArmy;}
  public void setNeighborMap(HashMap<Integer, Territory> neighborMap) {this.neighborMap = (HashMap)(neighborMap);}
  public void setAttackArmyMap(Map<Integer, Army> attackArmyMap) {this.attackArmyMap = (HashMap)(attackArmyMap);}
  public void setSpyMap(Map<Integer, Spy> spyMap) {this.spyMap = (HashMap)spyMap;}


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

  public boolean isVisible(int playerID) {
    // 1: visible because is owner
    if (this.owner.getPlayerID() == playerID) {
      return true;
    }

    // 2: visible because of spy
    if (this.spyMap.containsKey(playerID) && this.spyMap.get(playerID).size() != 0) {
      return true;
    }

    // 3: visible because of attack army
    if (this.attackArmyMap.containsKey(playerID)) {
      return true;
    }

    // 4: NOT visible because of fog
    if (this.fogged) {
      return false;
    }

    // 5: visible because of neighbor
    for (int neighTerrID : this.neighborMap.keySet()) {
      Territory neighbor = this.neighborMap.get(neighTerrID);
      if (neighbor.getOwner().getPlayerID() == playerID) {
        return true;
      }
    }

    // otherwise: not visible to this playerID
    return false;
  }

  // for making spy
  public boolean ownerArmyHasAnyUnit() {
    return this.ownerArmy.hasAnyUnit();
  }

  // for making spy
  public void reduceOneLowestLevelUnit() throws InvalidOptionException {
    this.ownerArmy.reduceOneLowestLevelUnit();
  }

  // for moving spy
  public boolean hasAnySpyOfPlayer(int ownerID) {
    // if this player does not have any spy here
    if (!this.spyMap.containsKey(ownerID) ||
            this.spyMap.get(ownerID).size() == 0) {
      return false;
    } else {
      return true;
    }
  }

  // for moving spy
  public void addOneSpy(int ownerID, Spy spy) {
    if (!this.spyMap.containsKey(ownerID)) {
      ArrayList<Spy> spyList = new ArrayList<>();
      this.spyMap.put(ownerID, spyList);
    }
    this.spyMap.get(ownerID).add(spy);
  }

  // for moving spy
  public Spy reduceOneSpy(int ownerID) throws InvalidOptionException{
    if (!this.hasAnySpyOfPlayer(ownerID)) {
      throw new InvalidOptionException("You do not have any spy on territory:" + this.getName());
    }
    // reduce one spy
    ArrayList<Spy> spyList = this.spyMap.get(ownerID);
    Spy spy = spyList.get(spyList.size() - 1);
    spyList.remove(spyList.size() - 1);

    if (spyList.size() == 0) {
      spyMap.remove(ownerID);
    }

    return spy;
  }

  public abstract Territory getCopy() throws UnitLevelException;
}
