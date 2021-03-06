package RISK.CombatResolver;

import RISK.Army.Army;
import RISK.Player.Player;
import RISK.Territory.Territory;
import RISK.Unit.Unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;



public class DiceCombatResolver implements CombatResolver{
  protected int diceSides;
  protected final int defenderWin;
  protected final int attackerWin;
  

  public DiceCombatResolver(int diceSides) {
    this.diceSides = diceSides;
    this.defenderWin = 0;
    this.attackerWin = 1;
  }
  
  @Override
  public void resolve(Territory terr) {
    
    HashMap<Integer, Army> attackArmyMap = terr.getAttackArmyMap();

    Army ownerArmy = terr.getOwnerArmy();
    for (Army attackArmy : attackArmyMap.values()) {

      Player defender = terr.getOwner();
      Player attacker = attackArmy.getOwner();

      int combatResult = combatUnitResolver(ownerArmy, attackArmy);
      if (combatResult == defenderWin) {
        // do nothing
      }
      if (combatResult == attackerWin) {
        // update defending army
        Army defendArmy = terr.getOwnerArmy();
        defendArmy.setOwner(attackArmy.getOwner());     // owner
        defendArmy.setUnitMap(attackArmy.getUnitMap());  // units

        // update terr ownership
        defender.looseTerr(terr.getTerrID());
        attacker.addTerr(terr);
        terr.setOwner(attackArmy.getOwner());
      }
      
    }
    // clear attack army map
    terr.setAttackArmyMap(new HashMap<>());
  }

  public int combatUnitResolver(Army defenderArmy, Army attackerArmy) {
    int toss = 1;
    while (true) {
      
      ArrayList<Unit> defenderUnits ;
      ArrayList<Unit> attackerUnits;
      if (toss == -1) { 
        defenderUnits = this.getUnits("highest", defenderArmy);
        attackerUnits = this.getUnits("lowest", attackerArmy);
      } else {
        defenderUnits = this.getUnits("lowest", defenderArmy);
        attackerUnits = this.getUnits("highest", attackerArmy);
      }
      toss *= -1;
      if (defenderUnits == null) {
        return attackerWin;
      }
      if (attackerUnits == null) {
        return defenderWin;
      }
      while (defenderUnits.size() != 0 && attackerUnits.size() != 0) {
        int defenderUnitIndex = defenderUnits.size()-1;
        int attackerUnitIndex = attackerUnits.size()-1;
        Unit defenderUnit = defenderUnits.get(defenderUnitIndex);
        Unit attackerUnit = attackerUnits.get(attackerUnitIndex);
        int battleResult = this.randomDiceResult(
                defenderUnit.getBonus(),
                attackerUnit.getBonus());
        if (battleResult == attackerWin) {
          defenderUnits.remove(defenderUnitIndex);
        } else {
          attackerUnits.remove(attackerUnitIndex);
        }
      }
    }
  }
  
  // if defender win, return 0, else return 1
  public int randomDiceResult(int defenderBonus,
                              int attackerBonus){
    Random newRan = new Random();
    int defenderNum = newRan.nextInt(diceSides) + defenderBonus;
    int attackerNum = newRan.nextInt(diceSides) + attackerBonus;
    if (defenderNum >= attackerNum) {
      return defenderWin;
    }
    else {
      return attackerWin;
    }
  }

  protected ArrayList<Unit> getUnits(String condition, Army army) {

    HashMap<Integer, ArrayList<Unit>> unitMap = army.getUnitMap();
    if (condition.equals("highest")) {
      for (int level = 6; level >= 0; level--) {
        if (unitMap.containsKey(level)) {
          ArrayList<Unit> units = unitMap.get(level);
          if (units.size() == 0) {
            unitMap.remove(level);
          } else {
            return units;
          }
        }
      }
    } else {
      for (int level = 6; level >= 0; level--) {
        if (unitMap.containsKey(level)) {
          ArrayList<Unit> units = unitMap.get(level);
          if (units.size() == 0) {
            unitMap.remove(level);
          } else {
            return units;
          }
        }
      }
    }

    return null;
  }
}
