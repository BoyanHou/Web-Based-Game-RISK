package RISK.Game;


import RISK.ClassBuilder.BuildClassesException;
import RISK.ClassBuilder.ClassBuilder;
import RISK.Territory.Territory;
import RISK.Unit.UnitLevelException;

import java.io.IOException;
import java.util.HashMap;

public class GameDisplayable extends Game{

  protected HashMap<Integer, Territory> outdatedTerrMap;
  protected int playerID;

  public GameDisplayable() {}

  public GameDisplayable(String terrPath,
                         String playerPath,
                         String armyPath,
                         ClassBuilder classBuilder) throws IOException, BuildClassesException {
    super(terrPath, playerPath, armyPath, classBuilder);
    this.outdatedTerrMap = new HashMap<>();
  }

  public int getPlayerID() {
    return this.playerID;
  }

  // back up currently visible terrs into the outdatedTerrMap, in case the next moment they turn invisible
  protected void backupCurrentlyVisibleTerrs () {
    for (int terrID : this.terrMap.keySet()) {
      Territory terr = this.terrMap.get(terrID);
      if (terr.isVisible(this.playerID)) {
        Territory terrCopy = null;
        try {
          terrCopy = terr.getCopy();
        } catch (UnitLevelException e) {
          System.out.println(e.getMessage()); // impossible to happen
        }
        this.outdatedTerrMap.put(terrID, terrCopy);
      }
    }
    return;
  }

  public HashMap<Integer, Territory> getOutdatedTerrMap() {
    return outdatedTerrMap;
  }
}
