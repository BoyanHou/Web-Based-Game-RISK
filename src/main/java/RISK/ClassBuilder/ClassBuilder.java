package RISK.ClassBuilder;

import RISK.Factory.NtopFactory;
import RISK.Game.Game;

import java.util.HashMap;

public abstract class ClassBuilder<T> {

  protected NtopFactory<T> ntopFactory;

  protected HashMap<Integer, ClassBuilderJSON.ArmyObjectFields> armyFieldMap;
  protected HashMap<Integer, ClassBuilderJSON.TerrObjectFields> terrFieldMap;
  protected HashMap<Integer, ClassBuilderJSON.PlayerObjectFields> playerFieldMap;

//  protected HashMap<Integer, Army> armyMap;
//  protected HashMap<Integer, Territory> terrMap;
//  protected HashMap<Integer, Player> playerMap;

  
  // entry func
  public void buildAllClasses(T classes, Game game) {
    this.buildPrimitiveClasses(classes, game);
    this.fillClasses(game);
  }

  // build classes & put into maps
  // omit object fileds for now
  protected abstract void buildPrimitiveClasses(T classes, Game game);

  // fill in object fields using the maps
  protected abstract void fillClasses(Game game);
}
