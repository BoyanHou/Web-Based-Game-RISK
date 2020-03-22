package RISK.Game;

import RISK.Displayer.Displayer;

public class GameDisplayable extends Game{

  protected int playerID;

  public void getDisplayed(Displayer displayer) {
    displayer.display(this);
  }

  public void getDisplayed(Displayer displayer, String str) {
    displayer.display(this, str);
  }

  public int getPlayerID() {
    return this.playerID;
  }  
}
