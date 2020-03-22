/*
    Writer & Maintainer:

 */
package RISK.Player;


import RISK.Territory.Territory;
import RISK.Utils.Status;

import java.util.ArrayList;
import java.util.List;

public class Player extends PlayerRO {

    public Player(int id, String name) {
        this.playerID = id;
        this.name = name;
        this.status = Status.SETUP;
        this.terrList = new ArrayList<>();
    }

    public ArrayList<Territory> getTerrList() {
        return this.terrList;
    }

    public void setTerrList(List<Territory> lst) {
        this.terrList = new ArrayList<>(lst);
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void addTerr(Territory toAdd) {
      this.terrList.add(toAdd);
    }

    public void delTerr(Territory toDel) {
      int len = this.terrList.size();
      int toDelTerrID = toDel.getTerrID();
      int toDelIndex = -1;

      for (int i = 0; i < len; i++) {
        if (this.terrList.get(i).getTerrID() == toDelTerrID) {
          toDelIndex = i;
          break;
        }
      }

      if (toDelIndex != -1) {
          this.terrList.remove(toDelIndex);
      }
      return;
    }

}
