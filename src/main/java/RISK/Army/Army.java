package RISK.Army;

import RISK.Player.Player;
import RISK.Unit.Unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/*
    Writer & Maintainer: hby
    ArmyRO: the group to attack or defend
*/
public abstract class Army<T> extends ArmyRO<T> {

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
}
