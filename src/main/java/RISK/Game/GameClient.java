package RISK.Game;
import RISK.ClassBuilder.ClassBuilder;
import RISK.Displayer.Displayer;
import RISK.Factory.PtonFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public abstract class GameClient<T> extends GameDisplayable {
  protected Socket socket;
  protected Scanner scanner;
  protected Messenger messenger;
  protected int gameState; // 0: inGame 1: auditing 2: gameEnds
  
  // 1. receive playerID from server
  // 2. receive modles from server to setup HashMaps
  public abstract void initializeConnection(ClassBuilder<T> classBuilder,
                                            Displayer displayer) throws IOException;

  // 1. choose & validate moves, send to server
  // 2. start this all over again if server finds out a invalid move
  public abstract void chooseMoves(PtonFactory<T> ptonFactory,
                                   Displayer displayer) throws IOException;

  // wait for new models from server to update & display
  public abstract void listenForUpdates(ClassBuilder<T> classBuilder,
                                           Displayer displayer) throws IOException;

  public int getGameState() {
    return this.gameState;
  }
}

