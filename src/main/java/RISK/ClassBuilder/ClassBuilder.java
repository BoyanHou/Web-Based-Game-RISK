package RISK.ClassBuilder;

import RISK.Army.Army;
import RISK.Game.Game;
import RISK.Player.Player;
import RISK.Territory.Territory;

import java.util.HashMap;

public abstract class ClassBuilder<T> {

  // record the object fields of models at the beginning
  // after all models are built, fill their object fields with already-built models
  protected interface ArmyObjectFields{
    public abstract void getConnected(Game game, Army army);
  }
  protected interface TerrObjectFields{
    public abstract void getConnected(Game game, Territory terr);
  }
  protected interface PlayerObjectFields{
    public abstract void getConnected(Game game, Player player);
  }

  protected HashMap<Integer, ArmyObjectFields> armyFieldMap;
  protected HashMap<Integer, TerrObjectFields> terrFieldMap;
  protected HashMap<Integer, PlayerObjectFields> playerFieldMap;
  
  // entry func
  public void buildAllClasses(T classesT, Game game) throws BuildClassesException {
    this.buildPrimitiveClasses(classesT, game);
    this.fillClasses(game);
  }

  // build classes & put into maps
  // omit object fields for now
  protected abstract void buildPrimitiveClasses(T classesT, Game game) throws BuildClassesException;

  // fill in object fields using the maps
  protected abstract void fillClasses(Game game) throws BuildClassesException;
}
