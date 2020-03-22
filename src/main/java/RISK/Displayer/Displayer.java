package RISK.Displayer;

import RISK.Game.GameDisplayable;

/*
    Writer & Maintainer:
    Displayer: this interface is used for display the game

 */

public interface Displayer {
  
  void display(GameDisplayable game); // display map & overall condition

  void display(GameDisplayable game, String text); // all plus text

}
