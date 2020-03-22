package RISK.CombatResolver;

import RISK.Army.Army;
import RISK.Player.Player;
import RISK.Territory.Territory;

import java.util.ArrayList;
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
    
    ArrayList<Army> attackArmyList = terr.getAttackArmyList();

    Army ownerArmy = terr.getOwnerArmy();
    while(!attackArmyList.isEmpty()){
      Army attackArmy = attackArmyList.remove(0);
      Player defender = terr.getOwner();
      Player attacker = attackArmy.getOwner();
      int combatResult = combatUnitResolver(ownerArmy, attackArmy);
      if (combatResult == defenderWin) {
        // do nothing
      }
      else if (combatResult == attackerWin) {
        // update defending army
        Army defendArmy = terr.getOwnerArmy();
        defendArmy.setOwner(attackArmy.getOwner());     // owner
        defendArmy.setUnits(attackArmy.getUnitList());  // units

        // update terr ownership
        defender.delTerr(terr);
        attacker.addTerr(terr);
        terr.setOwner(attackArmy.getOwner());
      } else {
        System.out.println("Error: status is -1, check it in DiceCombatResolever");
      }
      
    }
  }

  public int combatUnitResolver(Army ownerArmy, Army attackArmy) {
    while (true) {
      if (ownerArmy.getUnitListSize() == 0) {
        return attackerWin;
      }
      if (attackArmy.getUnitListSize() == 0) {
        return defenderWin;
      }
      if (randomDiceResult() == defenderWin) {
        ownerArmy.reduceUnit(1);
      }
      else {
        attackArmy.reduceUnit(1);
      }
    }
  }
  
  // if defender win, return 0, else return 1
  public int randomDiceResult(){
    Random newRan = new Random();
    int ran0 = newRan.nextInt(diceSides);
    int ran1 = newRan.nextInt(diceSides);
    if (ran0 >= ran1) {
      return defenderWin;
    }
    else {
      return attackerWin;
    }
  }
}
