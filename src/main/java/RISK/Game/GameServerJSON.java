package RISK.Game;

import RISK.ClassBuilder.ClassBuilder;
import RISK.CombatResolver.CombatResolver;
import RISK.Factory.NtopFactory;
import RISK.Factory.PtonFactory;
import RISK.Order.AttackOrder;
import RISK.Order.MoveOrder;
import RISK.Order.Order;
import RISK.Player.Player;
import RISK.Territory.Territory;
import RISK.Unit.Soldier;
import RISK.Utils.TxtReader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class GameServerJSON extends GameServer<JSONObject> {

  // read info from txt files to build up game 
  public GameServerJSON(int port,
                        ClassBuilder<JSONObject> classBuilder,
                        String terrPath, String playerPath, String armyPath)
    throws IOException{
    this.gameState = 0; // set gameState as running
    String terrStr = TxtReader.readStrFromFile(terrPath);
    JSONArray terrJOs = new JSONArray(terrStr);

    String playerStr = TxtReader.readStrFromFile(playerPath);
    JSONArray playerJOs = new JSONArray(playerStr);
    
    String armyStr = TxtReader.readStrFromFile(armyPath);
    JSONArray armyJOs = new JSONArray(armyStr);
    
    JSONObject classes = new JSONObject();
    classes.put("Terr", terrJOs);
    classes.put("Player", playerJOs);
    classes.put("Army", armyJOs);
    
    classBuilder.buildAllClasses(classes, this);

    this.serverSocket = new ServerSocket(port);
    this.socketMap = new HashMap<>();
  }

  @Override
  public void acceptConnections(PtonFactory<JSONObject> ptonFactory)
    throws IOException {

    JSONObject classes = this.packUpContent(ptonFactory);
    
    ArrayList<Thread> connectionThreads = new ArrayList<>();
    for (int playerID : this.playerMap.keySet()) {
      Socket socket = this.serverSocket.accept();
      this.socketMap.put(playerID, socket);
      JSONObject initInfo = new JSONObject();
      initInfo.put("playerID", playerID);
      initInfo.put("classes", classes);
      
      Thread connectionThread = new ConnectionThread(socket,
                                                     initInfo);
      connectionThreads.add(connectionThread);
      connectionThread.start();
    }

    // wait for threads to finish
    try {
      for (Thread connectionThread : connectionThreads) {
        connectionThread.join();
      }
    } catch (InterruptedException e) {
      System.out.println("OrderThread Interrupted!");
      return;
    }
  }

  @Override
  public void acceptOrders(NtopFactory<JSONObject> ntopFactory) throws IOException {
    ArrayList<Thread> orderThreads = new ArrayList<>();

    // playerMap will be updated after end of this turn,
    // so lost players will not be able to send orders
    for (int playerID : this.playerMap.keySet()) {
      Socket socket = this.socketMap.get(playerID);
      Thread orderThread = new OrderThread(socket, playerID, this, ntopFactory);
      orderThreads.add(orderThread);
      orderThread.start();
    }

    // for threads to finish
    try {
      for (Thread orderThread : orderThreads) {
        orderThread.join();
      }
    } catch (InterruptedException e) {
      System.out.println("OrderThread Interrupted!");
      return;
    }
  }

  @Override
  public void resolveCombats(CombatResolver combatResolver,
                             PtonFactory<JSONObject> ptonFactory) throws IOException {
    // make messegners
    // messengers are made based on socketMap,
    // so that it's possible a player's not in playerMap (lost) but
    // still be able to watch the game by staying in the msgerMap
    HashMap<Integer, Messenger> msgerMap = new HashMap<>();
    for (int playerID : this.socketMap.keySet()) {
      Messenger msger = new Messenger(this.socketMap.get(playerID));
      msgerMap.put(playerID, msger);
    }

    // resolve all combats on all terrioties, one by one
    for (int terrID : this.terrMap.keySet()) {
      Territory terr = this.terrMap.get(terrID);
      terr.resolveCombat(combatResolver);     // resolve combat
//      // maybe sleep 2 seconds here?
//      JSONObject classes = this.packUpContent(ptonFactory);
//      this.broadCast(classes.toString(), msgerMap); // broadCast new models
    }

    // broadcast final battle result
    JSONObject classes = this.packUpContent(ptonFactory);
    this.broadCast(classes.toString(), msgerMap); // broadCast new models

    // go through each territory after all combats finish
    // ADD ONE NEW UNIT into the territory
    for (int terrID : this.terrMap.keySet()) {
      Territory terr = this.terrMap.get(terrID);
      terr.getOwnerArmy().addUnit(new Soldier());
    }

    // broadcast again for updated units
    classes = this.packUpContent(ptonFactory);
    this.broadCast(classes.toString(), msgerMap); // broadCast new models

    // broadcast info to enter next turn
    this.broadCast("ENDOFTURN", msgerMap);

    // process the game result of this turn
    this.processTurnResult(msgerMap);
  }

  protected void processTurnResult(HashMap<Integer, Messenger> msgerMap) throws IOException {
    HashSet<Integer> thisRoundLoserIDs = new HashSet<>();
    int winnerID = -1;
    for (int playerID : this.playerMap.keySet()) {
      Player player = this.playerMap.get(playerID);
      int terrNum = player.getTerrList().size();
      if (terrNum == this.terrMap.size()) { // you got to win'em all!
        winnerID =playerID;
        break;
      } else if (terrNum == 0) { // you gotta lose'em all!
        thisRoundLoserIDs.add(playerID);
      }
    }

    // first, check if anybody wins the game
    if (winnerID != -1) {
      Player winner = this.playerMap.get(winnerID);
      // broadCast winner & end the game
      String congratWinner = "Player " + winner.getName() + " just won the game! Congrats!";
      this.broadCast(congratWinner, msgerMap);
      this.gameState = 1; // game ends
    } else {
      ArrayList<AuditThread> threads = new ArrayList<>();
      // if no winner, then check on losers
      for (int thisRoundLoserID : thisRoundLoserIDs) {
        Messenger msger = msgerMap.get(thisRoundLoserID);
        msger.send("LOSE");
        AuditThread thread = new AuditThread(
                msger,
                this.socketMap,
                thisRoundLoserID);
        threads.add(thread);
        thread.start();

        this.playerMap.remove(thisRoundLoserID);
      }
      // collect threads
      for (Thread thread : threads) {
        try {
          thread.join();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      // let non-losers that remains in socketMap to continue
      HashMap<Integer, Messenger> nonLoserMsgerMap = new HashMap<>();
      for (int playerID : msgerMap.keySet()) {
        if (!thisRoundLoserIDs.contains(playerID)) {
          nonLoserMsgerMap.put(playerID, msgerMap.get(playerID));
        }
      }
      this.broadCast("CONTINUE", nonLoserMsgerMap);
    }

  }

  protected class AuditThread extends Thread {
      Messenger msger;
      Map<Integer, Socket> socketMap;
      int loserID;

      public AuditThread(Messenger msger,
                         Map<Integer, Socket> socketMap,
                         int loserID) {
        this.msger = msger;
        this.socketMap = socketMap;
        this.loserID = loserID;
      }

      public synchronized void deleteSocket () {
        this.socketMap.remove(loserID);
      }

      @Override
      public void run () {
        String continueAudit = "";
        try {
          continueAudit = this.msger.recv();
        } catch (IOException e) {
          e.printStackTrace();
        }
        if (continueAudit.equals("N")) {
          this.deleteSocket();
        }
      }
  }

  @Override
  protected void broadCast(String msg,
                           HashMap<Integer, Messenger> msgerMap) throws IOException {

    for (int playerID : msgerMap.keySet()) {
      Messenger msger = msgerMap.get(playerID);
      msger.send(msg);
    }
    
  }


  @Override
  protected JSONObject packUpContent(PtonFactory<JSONObject> ptonFactory) {
    JSONObject res = new JSONObject();
  
    // terrs
    JSONArray terrJOs = new JSONArray();
    for (int terrID : this.terrMap.keySet()) {
      JSONObject terrJO = ptonFactory.terrPton(this.terrMap.get(terrID));
      terrJOs.put(terrJO);
    }
    res.put("Terr", terrJOs);

    // players
    JSONArray playerJOs = new JSONArray();
    for (int playerID : this.playerMap.keySet()) {
      JSONObject playerJO = ptonFactory.playerPton(this.playerMap.get(playerID));
      playerJOs.put(playerJO);
    }
    res.put("Player", playerJOs);

    // armies
    JSONArray armyJOs = new JSONArray();
    for (int armyID : this.armyMap.keySet()) {
      JSONObject armyJO = ptonFactory.armyPton(this.armyMap.get(armyID));
      armyJOs.put(armyJO);
    }
    res.put("Army", armyJOs);

    return res;
  }

  protected class ConnectionThread extends Thread {
    protected Messenger messenger;
    protected JSONObject initInfo;
    
    public ConnectionThread (Socket socket,
                             JSONObject initInfo)
      throws IOException{
      this.messenger = new Messenger(socket);
      this.initInfo = initInfo;
    }
    
    @Override
    public void run () {
      try {
        System.out.println("CONNECTION THREAD: RUNNING");
        String setUpCode = this.messenger.recv(); // blocks here!
       
        if (!setUpCode.equals("INIT")) {
          System.out.println("Received \"" + setUpCode + "\" != \"INIT\"");
          return;
        }
        this.messenger.send(this.initInfo.toString());
      } catch (IOException e) {
        
        System.out.println("Failed to send out init info!");
        return;
      }
    }
  }

  protected class OrderThread extends Thread {
    Messenger messenger;
    Game game;
    int playerID;
    NtopFactory<JSONObject> ntopFactory;
    
    public OrderThread (Socket socket,
                        int playerID,
                        Game game,
                        NtopFactory<JSONObject> ntopFactory)
    throws IOException {
      this.messenger = new Messenger(socket);
      this.game = game;
      this.playerID = playerID;
      this.ntopFactory = ntopFactory;
    }

    protected synchronized void executeSynchronizedOrder(Order order) {
      order.execute();
      return;
    }
    
    @Override
    public void run () {
      try {
        String msgStr = this.messenger.recv();
      
        while (!msgStr.equals("DONE")) {
          JSONObject msgJO = new JSONObject(msgStr);
        
          String type = msgJO.getString("type");
          JSONObject orderJO = msgJO.getJSONObject("order");
          
          Boolean orderAccepted = false;
          if (type.equals("move")) { // moveOrder
            MoveOrder moveOrder = this.ntopFactory.moveNtop(orderJO, this.game);
            if (moveOrder.validate() &&
                moveOrder.getPlayer().getPlayerID() == this.playerID) {
              orderAccepted = true;
              moveOrder.execute();  // execute order
            }
          } else if (type.equals("attack")) { // attackOrder
            AttackOrder attackOrder = this.ntopFactory.attackNtop(orderJO, this.game);
            if (attackOrder.validate() &&
                attackOrder.getPlayer().getPlayerID() == this.playerID) {
              orderAccepted = true;
              this.executeSynchronizedOrder(attackOrder); // execute order
            }
          }

          if (orderAccepted) {
            this.messenger.send("VALID");
          } else {
            this.messenger.send("INVALID");
          }
          msgStr = this.messenger.recv();
        }
      } catch (IOException e) {
        System.out.println("IOException Occured in thread");
      }
    }
  }
}

