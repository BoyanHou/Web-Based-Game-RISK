package RISK.Army;

import RISK.Player.Player;
import RISK.Unit.Unit;

import java.util.ArrayList;


/*
    Writer & Maintainer: hby
    ArmyRO: the group to attack or defend
*/
public class Army extends ArmyRO {

  public Army(ArrayList<Unit> units, Player owner, int armyID) {
    this.unitList = units;
    this.owner = owner;
    this.armyID = armyID;
  }

  public Player getOwner() {
    return owner;
  }

  public void setUnits(ArrayList<Unit> units) {
    this.unitList = units;
  }

  public void addUnit(Unit unit) {
    unitList.add(unit);
  }

  /*
    Remove the num units for the head.
  */
  public Boolean reduceUnit(int num) {
    if (unitList.isEmpty() && unitList.size() > num) {
      return false;
    }
    while (num > 0) {
      unitList.remove(0);
      num--;
    }
    return true;
  }

  public void setOwner(Player owner) {
    this.owner = owner;
  }
}
