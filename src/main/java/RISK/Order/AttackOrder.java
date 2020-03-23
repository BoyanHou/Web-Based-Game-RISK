package RISK.Order;

import RISK.Army.Army;
import RISK.Player.Player;
import RISK.Territory.Territory;
import RISK.Unit.Soldier;
import RISK.Unit.Unit;

import java.util.ArrayList;


public class AttackOrder extends Order {

    protected Territory myTerr;
    protected Territory targetTerr;
    protected int num;

    public AttackOrder(Territory myTerr, Territory targetTerr, int num, Player player) {
        this.myTerr = myTerr;
        this.targetTerr = targetTerr;
        this.num = num;
        this.player = player;
    }

    public Territory getMyTerrTerr() {
        return myTerr;
    }

    public Territory getTargetTerr() {
        return targetTerr;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public boolean validate() {
      /*
        1. the num < myTerr.numOfUnits
        2. fromTerr and toTerr  belong to different player
        3. the myTerr and targetTerr have to be adjacent
      */
       if (num > myTerr.getOwerArmyUnitListSize()) {

          System.out.println("Error: The number of attacking units invalid");
          return false;
        }

       if (myTerr.getOwner().getPlayerID() == targetTerr.getOwner().getPlayerID() ) {
          System.out.println("Error: The territory belong to same player");
          return false;
        }

       if(ifAdjacent(myTerr, targetTerr) == false){
          System.out.println("Error: Two territories are not adjacent");
          return false;
        }
      
      return true;
    }

    // check if two territories are adjacent
    private boolean ifAdjacent(Territory myTerr, Territory targetTerr){
          ArrayList<Territory> neighborList = myTerr.getNeighborList();
          for(Territory newTerr: neighborList){
            if(newTerr == targetTerr){
              return true;
            }
          }
          return false;
      }

    @Override
    public void execute() {
      // add the myTerr's army to the targetTerr's attackArmy list
      ArrayList<Unit> units = new ArrayList<>();
      Soldier soldier = new Soldier();
      for (int i = 0; i < this.num; i++) {
        units.add(soldier);
      }
      Army attackArmy = new Army(units, myTerr.getOwner(), -1);
      Army myArmy = myTerr.getOwnerArmy();
      myArmy.reduceUnit(this.num);
      // check if there are army from same player, and merge
      if (checkMergeArmy() == false) {
        targetTerr.getAttackArmyList().add(attackArmy);
      }
      
    }
  
   // check if there are armies from same player, and merge
   private boolean checkMergeArmy() {
     ArrayList<Army> attackArmyList = targetTerr.getAttackArmyList();
     for (Army army : attackArmyList) {
       if (army.getOwner() == this.myTerr.getOwner()) {
          Soldier soldier = new Soldier();
          for (int i = 0; i < this.num; i++) {
            army.addUnit(soldier);
          }
          return true;
       }
     }
     return false;
     
   }
  

}
