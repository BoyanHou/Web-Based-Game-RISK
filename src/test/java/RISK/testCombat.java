package RISK;

import RISK.Utils.TerritoryNames;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import RISK.Territory.*;
import RISK.Player.*;
import RISK.Order.*;
import RISK.Unit.*;
import RISK.Army.*;
import RISK.Game.*;
import RISK.CombatResolver.*;

import java.util.ArrayList;


class testCombat {


  // private GameInitial game = new GameInitial();
  // private ArrayList<Territory> territories = game.getTerritories();

  
  // @Test
  // void testAttackOrder(){
  //    Territory fromTerr = null;
  //    Territory toTerr = null;
  //    Territory elantris = null;
  //    Territory scadrial = null;
  //    Territory mordor = null;
  //    for(Territory terr: territories) {
  //      if (terr.getName() == TerritoryNames.Narnia) {
  //        fromTerr = terr;
  //      }
  //      if (terr.getName() == TerritoryNames.Oz) {
  //        toTerr = terr;
  //      }
  //      if (terr.getName() == TerritoryNames.Elantris){
  //        elantris = terr; 
  //      }
  //      if (terr.getName() == TerritoryNames.Scadrial){
  //        scadrial = terr;
  //      }
  //      if (terr.getName() == TerritoryNames.Mordor){
  //        mordor = terr;
  //      }
       
  //    }

  //   int num = 6;
  //   Player player1 = new Player(1, "Green");
  //   Player player2 = new Player(2, "Test");
  //   AttackOrder atkOrder = new AttackOrder(fromTerr, toTerr, num, player1);
  //   assertEquals(false, atkOrder.validate());
  //   atkOrder.setNum(15);
  //   assertEquals(false, atkOrder.validate());
  //   atkOrder.validate();
  //   atkOrder.setNum(6);
  //   AttackOrder atkOrder1 = new AttackOrder(fromTerr, elantris, num, player1);
  //   atkOrder1.validate();
  //   ArrayList<Unit> units = new ArrayList<>();
  //   Soldier soldier = new Soldier();
  //   for (int i = 0; i < 15; i++) {
  //     units.add(soldier);
  //   }
  //   Army amy1 = new Army(units, fromTerr.getOwner(), -1);
  //   Army amy2 = new Army(units, scadrial.getOwner(), -1);
  //   Army amy3 = new Army(units, player2, -1);
  //   fromTerr.setOwnerArmy(amy1);
  //   scadrial.setOwnerArmy(amy2);
  //   ArrayList<Army> armyList = new ArrayList<>();
  //   armyList.add(amy1);
  //   armyList.add(amy1);
  //   armyList.add(amy3);
  //   armyList.add(amy3);
  //   scadrial.setAttackArmyList(armyList);
  //   AttackOrder atkOrder2 = new AttackOrder(fromTerr, scadrial, 1, player1);
  //   AttackOrder atkOrder3 = new AttackOrder(mordor, scadrial, 1, player1);
  //   assertEquals(false, atkOrder2.validate());    
  //   atkOrder2.execute();
  //   atkOrder3.execute();
  //   assertEquals(fromTerr, atkOrder2.getMyTerrTerr());
  //   assertEquals(scadrial, atkOrder2.getTargetTerr());
  //   assertEquals(1, atkOrder2.getNum());
    
  //   CombatResolver newC = new DiceCombatResolver(25);
  //   scadrial.resolveCombat(newC);
  //   scadrial.resolveCombat(newC);
  //   scadrial.resolveCombat(newC);
  //   DiceCombatResolver newD = new DiceCombatResolver(25);
  //   newD.combatUnitResolver(amy1,amy2);
  //   newD.combatUnitResolver(amy2,amy3);
  //   newD.combatUnitResolver(amy1,amy3);

  //   ArrayList<Unit> units1 = new ArrayList<>(); 
  //   for (int i = 0; i < 1; i++) {
  //     units1.add(soldier);
  //   }
  //   Army amy5 = new Army(units1, fromTerr.getOwner(), -1);
  //   ArrayList<Army> armyList2 = new ArrayList<>();
  //   armyList2.add(amy5);
    
  //   scadrial.setAttackArmyList(armyList);
    
  //   //scadrial.setOwnerArmy(amy2);
  //       scadrial.resolveCombat(newC);
    
  //  }
  
}
