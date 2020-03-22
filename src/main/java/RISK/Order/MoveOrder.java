package RISK.Order;

import RISK.Player.Player;
import RISK.Territory.Territory;
import RISK.Unit.Soldier;
import RISK.Unit.Unit;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MoveOrder extends Order {

    protected Territory fromTerr;
    protected Territory toTerr;
    protected int num;
  
    public MoveOrder(Territory fromTerr, Territory toTerr, int num, Player player) {
        this.fromTerr = fromTerr;
        this.toTerr = toTerr;
        this.num = num;
        this.player = player;
    }

    public Territory getFromTerr() {
        return fromTerr;
    }

    public Territory getToTerrID() {
        return toTerr;
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
        1. the num < formTerr.numOfUnits
        2. fromTerr and toTerr both belong to the player
        3. there is a path from fromTerr to toTerr through player's territories
         */
        if (num > fromTerr.getOwerArmyUnitListSize()) {

          System.out.println("Error: The number of moving units invalid");
          return false;
        }

        if ((fromTerr.getOwner().getPlayerID() != this.player.getPlayerID()) ||
                (toTerr.getOwner().getPlayerID() != this.player.getPlayerID()) ) {
          System.out.println("Error: The territory doesn't belong to the player");
          return false;
        }

        if(ifPathExist(fromTerr, toTerr) == false){
          System.out.println("Error: No path between the territories");
          return false;
        }
        
        return true;
    }

    private boolean ifPathExist(Territory fromTerr, Territory toTerr){
         // BFS to detect the avaliable road
      
        Queue<Territory> queue = new LinkedList<Territory>();
        Queue<Territory> visited = new LinkedList<Territory>();
        queue.add(fromTerr);
        visited.add(fromTerr);

        while(queue.size() > 0){
          Territory terr = queue.poll();
          ArrayList<Territory> neighborList = terr.getNeighborList();
          for(Territory newTerr: neighborList){
            if(newTerr == toTerr){
              return true;
            }
            if(newTerr.getOwner().getName() == this.player.getName()){
              //System.out.println(newTerr.getOwner().getName());
              queue.add(newTerr);
            }
          }
          visited.add(terr);
        }
        
        return false;
  }

    @Override
    public void execute() {
        /*
        fromTerr.units - num and toTerr.units + num
         */
        int loopTimes = this.num;
        Unit unit = new Soldier();
        fromTerr.getOwnerArmy().reduceUnit(this.num);
        for(int i = 0; i < this.num; i++){
          toTerr.getOwnerArmy().addUnit(unit);
        }        
        
     }

}
