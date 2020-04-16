package RISK.Game;


import RISK.Territory.Territory;

import java.util.HashMap;

public class GameDisplayable extends Game{

  protected HashMap<Integer, Territory> visibleTerrMap;
  protected int playerID;

  public int getPlayerID() {
    return this.playerID;
  }

  protected void updateVisibleTerrMap () {
    for (int terrID : this.terrMap.keySet()) {
      Territory terr = this.terrMap.get(terrID);
      if (terr.isVisible(this.playerID)) {
        this.visibleTerrMap.put(terrID, terr);
      }
    }

    return;
  }
}
