//code reviewed by Zian Li
package RISK.Army;

import RISK.Order.InvalidOptionException;
import RISK.Player.Player;
import RISK.Unit.Spy;
import RISK.Unit.Unit;
import RISK.Unit.UnitLevelException;
import RISK.Unit.UnitLevelMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



/*
    Writer & Maintainer: hby
    ArmyRO: the group to attack or defend
*/
public abstract class Army<T> extends ArmyRO<T> {

  public Army() {}

  public Army(int armyID) {
    this.owner = null;
    this.armyID = armyID;
    this.unitMap = new HashMap<>();
  }

  public Army(Player owner, int armyID) {
    this.armyID = armyID;
    this.owner = owner;
    this.unitMap = new HashMap<>();
  }

  public Army(Map<Integer, ArrayList<Unit>> unitMap, Player owner, int armyID) {
    this.unitMap = (HashMap)unitMap;
    this.owner = owner;
    this.armyID = armyID;
  }

  public Player getOwner() {
    return this.owner;
  }
  public HashMap<Integer, ArrayList<Unit>> getUnitMap() {
    return this.unitMap;
  }
  public void setOwner(Player owner) {
    this.owner = owner;
  }
  public void setUnitMap(Map<Integer, ArrayList<Unit>> unitMap) {
    this.unitMap = (HashMap)unitMap;
  }

  public void addUnit(Unit unit) {
    int level = unit.getLevel();
    if (!this.unitMap.containsKey(level)) {
      ArrayList<Unit>unitList = new ArrayList<>();
      this.unitMap.put(level, unitList);
    }
    this.unitMap.get(level).add(unit);
  }

  public void absorb(Army army) {
    HashMap<Integer, ArrayList<Unit>> units = army.getUnitMap();
    ArrayList<Integer> levelToRemove = new ArrayList<>();
    for (int level : units.keySet()) {
      if (!this.unitMap.containsKey(level)) {
        ArrayList<Unit> unitList = new ArrayList<>();
        this.unitMap.put(level, unitList);
      }
      for (Unit unit :units.get(level)) {
        this.unitMap.get(level).add(unit);
      }
      levelToRemove.add(level);
    }
    for (int level : levelToRemove) {
      units.remove(level);
    }
  }

  public abstract T pton();

  // for making spy
  public void reduceOneLowestLevelUnit() throws InvalidOptionException {
    for (int i = 0; ;i++) {
      try {
        UnitLevelMapper.mapName(i);
      } catch (UnitLevelException e) {
        throw new InvalidOptionException("There is no existing unit available in this army");
      }
      if (this.unitMap.containsKey(i) &&
              this.unitMap.get(i).size() != 0) {
        ArrayList<Unit> unitList = this.unitMap.get(i);
        unitList.remove(unitList.size() - 1);
        break;
      }
    }
  }

  public abstract Army getCopy() throws UnitLevelException;
}
