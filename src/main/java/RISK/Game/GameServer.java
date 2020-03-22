package RISK.Game;

import RISK.CombatResolver.CombatResolver;
import RISK.Factory.NtopFactory;
import RISK.Factory.PtonFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public abstract class GameServer<T> extends Game {
  protected int gameState; // 0 for running, 1 for stop
  protected ServerSocket serverSocket;
  protected Map<Integer, Socket> socketMap;
    
  public abstract void acceptConnections(PtonFactory<T> ptonFactory) throws IOException;

  public abstract void acceptOrders(NtopFactory<T> ntopFactory) throws IOException; 
  
  public abstract void resolveCombats(CombatResolver combatResolver,
                                      PtonFactory<T> ptonFactory) throws IOException;
 
  protected abstract void broadCast(String msg,
                                    HashMap<Integer, Messenger> msgerMap) throws IOException;

  protected abstract T packUpContent(PtonFactory<T> ptonFactory);

  public int getGameState() {
    return this.gameState;
  }

}
