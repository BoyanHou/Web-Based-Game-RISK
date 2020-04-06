package RISK.Game;

import RISK.CombatResolver.CombatResolver;
import RISK.Order.OrderFactory;
import RISK.Unit.UnitLevelException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public abstract class GameServer<T> extends Game {
  protected int gameState; // 0 for running, 1 for stop
  protected ServerSocket serverSocket;
  protected Map<Integer, Socket> socketMap;
  OrderFactory<T> orderFactory;
    
  public abstract void acceptConnections() throws IOException;

  public abstract void acceptOrders() throws IOException;
  
  public abstract void resolveCombats(CombatResolver combatResolver) throws IOException, UnitLevelException;
 
  protected abstract void broadCast(String msg,
                                    HashMap<Integer, Messenger> msgerMap) throws IOException;

  protected abstract T packUpContent();

  public int getGameState() {
    return this.gameState;
  }

}
