package RISK.Game;
import RISK.ClassBuilder.BuildClassesException;
import RISK.ClassBuilder.ClassBuilder;
import RISK.Displayer.Displayer;
import RISK.Order.*;
import RISK.Territory.Territory;
import RISK.Unit.Unit;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
public abstract class GameClient<T> extends GameDisplayable {

  protected Messenger messenger;
  protected int gameState; // 0: inGame 1: auditing 2: gameEnds
  protected ClassBuilder<T> classBuilder;

  public GameClient(ClassBuilder<T> classBuilder) {
    this.classBuilder = classBuilder;
    this.gameState = 0; // set to be in-game
    this.messenger = null;
  }

  public int getGameState() {
    return this.gameState;
  }
  public void setGameState(int gameState) {
    this.gameState = gameState;
  }
  
  // 1. receive playerID from server
  // 2. receive models from server to setup HashMaps
  public abstract void initializeConnection(String serverIP,
                                            int serverPort)
          throws MessengerException, BuildClassesException;

  // this round ends: wait for new models from server to update
  public abstract String listenForUpdates()
          throws MessengerException, BuildClassesException;

  // Send signal to server that I'm done making orders; end this round!
  public void endRound() throws MessengerException {
    try {
      this.messenger.send("DONE");
    } catch (IOException e) {
      throw new MessengerException("Failed to signal the server for end of round!");
    }
  }

  // accept data from ClientOperator
  // make it into order
  // verify order locally & remotely
  public abstract void acceptOrder(Order Order)
          throws InvalidLogicException, MessengerException;

  public abstract void willingToAudit(boolean will) throws MessengerException;
}

