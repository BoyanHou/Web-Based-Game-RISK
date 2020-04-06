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

import java.util.ArrayList;


class testMoveOrder {


//  private GameInitial game = new GameInitial();
//  private ArrayList<Territory> territories = game.getTerritories();
//
//
//  @Test
//  void testPath(){
//     Territory fromTerr = null;
//     Territory toTerr = null;
//     for(Territory terr: territories) {
//       if (terr.getName() == TerritoryNames.Narnia) {
//         fromTerr = terr;
//       }
//       if (terr.getName() == TerritoryNames.Oz) {
//         toTerr = terr;
//       }
//     }
//
//    int num = 6;
//    Player player1 = new Player(1, "Test");
//    Player player2 = new Player(2, "Test");
//    MoveOrder mvOrder = new MoveOrder(fromTerr, toTerr, num, player1);
//    ArrayList<Unit> unit = new ArrayList<>();
//    Soldier s = new Soldier();
//    int sNum = 5;
//    while(sNum-- > 0){
//      unit.add(s);
//    }
//    int armyId = 3;
//    Army myArmy = new Army(unit, player1, armyId);
//    fromTerr.setOwnerArmy(myArmy);
//    assertEquals(5, fromTerr.getOwerArmyUnitListSize());
//    assertEquals(false, mvOrder.validate());
//    mvOrder.setNum(4);
//    assertEquals(false, mvOrder.validate());
//    fromTerr.setOwner(player1);
//    toTerr.setOwner(player2);
//    assertEquals(false, mvOrder.validate());
//    toTerr.setOwner(player1);
//    assertEquals(false, mvOrder.validate());
//    mvOrder.execute();
//    assertEquals(fromTerr, mvOrder.getFromTerr());
//    assertEquals(toTerr, mvOrder.getToTerrID());
//    assertEquals(4, mvOrder.getNum());
//    //mvOrder.execute();
//
//  }
//
//
//
//
//
//
//  @Test
//  void testValiate(){
//     Territory fromTerr = null;
//     Territory toTerr = null;
//     for(Territory terr: territories) {
//       if (terr.getName() == TerritoryNames.Narnia) {
//         //System.out.println("here");
//         fromTerr = terr;
//       }
//       if (terr.getName() == TerritoryNames.Oz) {
//         toTerr = terr;
//       }
//     }
//
//
//    int num = 6;
//    Player player1 = new Player(1, "Green");
//    Player player2 = new Player(2, "Green");
//    MoveOrder mvOrder = new MoveOrder(fromTerr, toTerr, num, player1);
//    ArrayList<Unit> unit = new ArrayList<>();
//    Soldier s = new Soldier();
//    int sNum = 5;
//    while(sNum-- > 0){
//      unit.add(s);
//    }
//    int armyId = 3;
//    Army myArmy = new Army(unit, player1, armyId);
//    fromTerr.setOwnerArmy(myArmy);
//    assertEquals(5, fromTerr.getOwerArmyUnitListSize());
//    assertEquals(false, mvOrder.validate());
//    mvOrder.setNum(4);
//    assertEquals(true, mvOrder.validate());
//    fromTerr.setOwner(player1);
//    toTerr.setOwner(player2);
//    assertEquals(false, mvOrder.validate());
//    toTerr.setOwner(player1);
//    assertEquals(true, mvOrder.validate());
//
//  }
//
//
//  @Test
//  void testAttackOrder(){
//     Territory fromTerr = null;
//     Territory toTerr = null;
//     Territory elantris = null;
//     Territory scadrial = null;
//     Territory mordor = null;
//     for(Territory terr: territories) {
//       if (terr.getName() == TerritoryNames.Narnia) {
//         fromTerr = terr;
//       }
//       if (terr.getName() == TerritoryNames.Oz) {
//         toTerr = terr;
//       }
//       if (terr.getName() == TerritoryNames.Elantris){
//         elantris = terr;
//       }
//       if (terr.getName() == TerritoryNames.Scadrial){
//         scadrial = terr;
//       }
//       if (terr.getName() == TerritoryNames.Mordor){
//         mordor = terr;
//       }
//
//     }
//
//    int num = 6;
//    Player player1 = new Player(1, "Green");
//    Player player2 = new Player(2, "Test");
//    AttackOrder atkOrder = new AttackOrder(fromTerr, toTerr, num, player1);
//    assertEquals(false, atkOrder.validate());
//    atkOrder.setNum(15);
//    assertEquals(false, atkOrder.validate());
//    atkOrder.validate();
//    atkOrder.setNum(6);
//    AttackOrder atkOrder1 = new AttackOrder(fromTerr, elantris, num, player1);
//    atkOrder1.validate();
//    ArrayList<Unit> units = new ArrayList<>();
//    Soldier soldier = new Soldier();
//    for (int i = 0; i < 15; i++) {
//      units.add(soldier);
//    }
//    Army amy1 = new Army(units, fromTerr.getOwner(), -1);
//    Army amy2 = new Army(units, scadrial.getOwner(), -1);
//    Army amy3 = new Army(units, player2, -1);
//    fromTerr.setOwnerArmy(amy1);
//    scadrial.setOwnerArmy(amy2);
//    ArrayList<Army> armyList = new ArrayList<>();
//    armyList.add(amy1);
//    armyList.add(amy1);
//    armyList.add(amy3);
//    armyList.add(amy3);
//    scadrial.setAttackArmyList(armyList);
//    AttackOrder atkOrder2 = new AttackOrder(fromTerr, scadrial, 1, player1);
//    AttackOrder atkOrder3 = new AttackOrder(mordor, scadrial, 1, player1);
//    assertEquals(false, atkOrder2.validate());
//    atkOrder2.execute();
//    atkOrder3.execute();
//    assertEquals(fromTerr, atkOrder2.getMyTerrTerr());
//    assertEquals(scadrial, atkOrder2.getTargetTerr());
//    assertEquals(1, atkOrder2.getNum());
//
//
//  }
  
}
