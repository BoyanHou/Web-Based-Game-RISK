package RISK.Game;


import RISK.ClassBuilder.ClassBuilder;
import RISK.Displayer.Displayer;
import RISK.Factory.PtonFactory;
import RISK.Order.AttackOrder;
import RISK.Order.MoveOrder;
import RISK.Player.Player;
import RISK.Territory.Territory;
import RISK.Territory.TerritoryRO;
import RISK.Utils.IntFormatException;
import RISK.Utils.NumUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class GameClientJSON extends GameClient<JSONObject> {

  
  public GameClientJSON(String serverIP, int serverPort, Scanner scanner)
    throws UnknownHostException,
           IOException {
    this.socket = new Socket(serverIP, serverPort);
    this.scanner = scanner;
    this.messenger = new Messenger(this.socket);
    this.gameState = 0; // set to be in-game
  }

  @Override
  public void initializeConnection(ClassBuilder<JSONObject> classBuilder,
                                   Displayer displayer)
    throws IOException {
    
    // request init info from server
    this.messenger.send("INIT");
    
    // receive init info from server
    String initInfo = this.messenger.recv();
    JSONObject initInfoJO = new JSONObject(initInfo);

    // get id
    this.playerID = initInfoJO.getInt("playerID");
    // get classes
    JSONObject classes = initInfoJO.getJSONObject("classes");

    // build classes
    classBuilder.buildAllClasses(classes, this);
    
    // display
    this.getDisplayed(displayer);
  }

  
  @Override
  public void chooseMoves(PtonFactory<JSONObject> ptonFactory,
                          Displayer displayer) throws IOException {
    // get player name
    String name = this.playerMap.get(this.playerID).getName();

    String prompt1 = "Player" + name + ", please choose a move: ";
    String orderTypePrompt = "press <M> to move your units\n" + "press <A> to attack an enemy";

    // my terrMap
    ArrayList<TerritoryRO> myTerrListRO = this.playerMap.get(this.playerID).getTerrListRO();
    HashMap<Integer, TerritoryRO> myTerrMapRO = new HashMap<>();
    for (TerritoryRO terrRO : myTerrListRO) {
      myTerrMapRO.put(terrRO.getTerrID(), terrRO);
    }

    String ownTerrPrompt = "Your " + myTerrMapRO.size() + " territories are:\n";
    for (int terrID : myTerrMapRO.keySet()) {
      String terrName = myTerrMapRO.get(terrID).getName().toString();
      ownTerrPrompt += "Press <" + terrID + "> for \"" + terrName + "\"\n";
    }

    String enemyTerrPrompt = "Enemy territories are:\n";
    for (int terrID : this.terrMap.keySet()) {
      if (!myTerrMapRO.containsKey(terrID)) {
        String terrName = terrMap.get(terrID).getName().toString();
        enemyTerrPrompt += "Press <" + terrID + "> for \"" + terrName + "\"\n";
      }
    }

    String chooseAgain = "please choose again!";

    // promt player to choose move
    this.getDisplayed(displayer, prompt1 + "\n" + orderTypePrompt);

    // one order at a time!
    // if one order is valid, execute it, then proceed to next
    Boolean continueChoosing = true;
    do {
      Boolean orderValid = false;

      do {
        String orderType = scanner.next();
        try {
          if (orderType.equals("M")) { // read-in moveOrder
            String promptFromTerr = "Choose a territory of yours to move your army from;\n" + ownTerrPrompt;
            String promptToTerr = "Choose a territory of yours to move your army to;\n" + ownTerrPrompt;
            String promptNum = "How many units are you going to move?";
            this.acceptMoveOrder(promptFromTerr,
                                 promptToTerr,
                                 promptNum,
                                 ptonFactory,
                                 displayer,
                                 myTerrMapRO);
            
          } else if (orderType.equals("A")) { // read-in attackOrder
            String promptMyTerr = "Choose a territory of yours to attack from;\n" + ownTerrPrompt;
            String promptTargetTerr = "Choose an enemy's territory to attack;\n" + enemyTerrPrompt;
            String promptNum = "How many units are going to attack?";
            this.acceptAttackOrder(promptMyTerr,
                                   promptTargetTerr,
                                   promptNum,
                                   ptonFactory,
                                   displayer,
                                   myTerrMapRO);
          } else {
            throw new InvalidOptionException();
          }
          orderValid = true;
        } catch (IntFormatException e) {
          this.getDisplayed(displayer, "Error: that is not a valid integer, " + chooseAgain);
        } catch (InvalidOptionException e) {
          this.getDisplayed(displayer, "Error: that is not a viable option, " + chooseAgain);
        } catch (InvalidOrderException e) {
          this.getDisplayed(displayer, "Error: that order is logically invalid, " + chooseAgain);
        }
        if (!orderValid) {
          this.getDisplayed(displayer, orderTypePrompt);
        }
      } while (!orderValid);

      Boolean choiceValid = false;
      // make another order? 
      do {
        try {
          continueChoosing = continueChoosingOrNot(displayer);
          if (continueChoosing) {
            this.getDisplayed(displayer, orderTypePrompt);
          }
          choiceValid = true;
        } catch (InvalidOptionException e) {
          this.getDisplayed(displayer, "Error: that is not a viable option, " + chooseAgain);
        }
      } while (!choiceValid);
    } while (continueChoosing);

    // inform server that I'm done ordering
    this.messenger.send("DONE");
    return;
  }

  protected Boolean continueChoosingOrNot(Displayer displayer)
    throws InvalidOptionException {
    // prompt
    String promptContinue = "Do you want to make another order?\n" + "<Y>Yes <N>No";        
    this.getDisplayed(displayer, promptContinue);
    
    // parse
    String continueStr = scanner.next();
    if (continueStr.equals("Y")) {
      return true;
    } else if (continueStr.equals("N")) {
      return false;
    } else {
      throw new InvalidOptionException(); // throws InvalidOptionException
    }
  }

  protected void acceptMoveOrder(String promptFromTerr,
                                 String promptToTerr,
                                 String promptNum,
                                 PtonFactory<JSONObject> ptonFactory,
                                 Displayer displayer,
                                 HashMap<Integer, TerritoryRO> myTerrMapRO)
      throws IntFormatException,
             InvalidOptionException,
             InvalidOrderException,
             IOException {
    
    // fromTerr
    this.getDisplayed(displayer, promptFromTerr);
    String fromTerrIDStr = this.scanner.next();
    // convert & validate
    // throws IntFormatException
    int fromTerrID = NumUtils.strToInt(fromTerrIDStr);
    // throws InvalidOptionException
    if (!myTerrMapRO.containsKey(fromTerrID)) {
      throw new InvalidOptionException();
    }
    // get
    Territory fromTerr = this.terrMap.get(fromTerrID);
          
    // toTerr
    this.getDisplayed(displayer, promptToTerr);
    String toTerrIDStr = scanner.next();
    // convert & validate
    // throws IntFormatException
    int toTerrID = NumUtils.strToInt(toTerrIDStr);
    // thorws InvalidOptionException
    if (!myTerrMapRO.containsKey(toTerrID)) {
      throw new InvalidOptionException();
    }
    // get 
    Territory toTerr = this.terrMap.get(toTerrID);

    // num
    this.getDisplayed(displayer, promptNum);
    String numStr = scanner.next();
    // throws IntFormatException
    int num = NumUtils.strToInt(numStr);

    // player
    Player player = this.playerMap.get(this.playerID);
              
    // make order
    MoveOrder moveOrder = new MoveOrder(fromTerr, toTerr, num, player);
    
    // validate order locally
    if (!moveOrder.validate()) {
      throw new InvalidOrderException();
    }
    // validate order on server
    JSONObject mvJO = ptonFactory.movePton(moveOrder);
    JSONObject outJO = new JSONObject();
    outJO.put("type", "move");
    outJO.put("order", mvJO);
    Boolean validFromServer = this.validateOnServer(outJO); // blocks here!
    if (!validFromServer) {
      throw new InvalidOrderException();
    }
    
    // execute the order
    moveOrder.execute();
  }

  protected void acceptAttackOrder(String promptMyTerr,
                                   String promptTargetTerr,
                                   String promptNum,
                                   PtonFactory<JSONObject> ptonFactory,
                                   Displayer displayer,
                                   HashMap<Integer, TerritoryRO> myTerrMapRO)
    throws IntFormatException,
           InvalidOptionException,
           InvalidOrderException,
           IOException {

    // myTerr
    this.getDisplayed(displayer, promptMyTerr);
    String myTerrIDStr = scanner.next();
    // throws IntFormatException, InvalidOptionException
    int myTerrID = NumUtils.strToInt(myTerrIDStr);
    if (!myTerrMapRO.containsKey(myTerrID)) {
      throw new InvalidOptionException();
    }
    // get
    Territory myTerr = this.terrMap.get(myTerrID);

    // targetTerr
    this.getDisplayed(displayer, promptTargetTerr);
    String targetTerrIDStr = scanner.next();
    // throws IntFormatException, InvalidOptionException
    int targetTerrID = NumUtils.strToInt(targetTerrIDStr);
    if (!this.terrMap.containsKey(targetTerrID) ||
        myTerrMapRO.containsKey(targetTerrID)) {
      throw new InvalidOptionException();
    }
    // get
    Territory targetTerr = this.terrMap.get(targetTerrID);
              
    // num
    this.getDisplayed(displayer, promptNum);
    String numStr = scanner.next();
    // throws IntFormatException
    int num = NumUtils.strToInt(numStr);

    // player
    Player player = this.playerMap.get(this.playerID);
              
    // make order
    AttackOrder attackOrder = new AttackOrder(myTerr, targetTerr, num, player);
    // validate order locally
    if (!attackOrder.validate()) {
      throw new InvalidOrderException();
    }
    // validate order on server
    JSONObject atkJO = ptonFactory.attackPton(attackOrder);
    
    JSONObject outJO = new JSONObject();
    outJO.put("type", "attack");
    outJO.put("order", atkJO);
    Boolean validFromServer = this.validateOnServer(outJO); // blocks here!
    if (!validFromServer) {
      throw new InvalidOrderException();
    }

    // execute the order
    attackOrder.execute();
  }

  // send orders to server for validation
  protected Boolean validateOnServer(JSONObject orderJO) throws IOException {
    String ordersStr = orderJO.toString();
    this.messenger.send(ordersStr);

    // recv validity from server
    String isValid = this.messenger.recv(); // !!blocks here
    if (isValid.equals("VALID")) {
      return true;
    } else {
      return false;
    }
  }
  
  @Override
  public void listenForUpdates(ClassBuilder<JSONObject> classBuilder,
                                  Displayer displayer) throws IOException {
    // receive init info from server
    String str = this.messenger.recv();

    // keep updating classes
    while (!str.equals("ENDOFTURN")) {
      JSONObject classes = new JSONObject(str);
      // display updated classes
      classBuilder.buildAllClasses(classes, this);
      this.getDisplayed(displayer);
      str = this.messenger.recv();
    }


    // receive result info from server
    String resultMsg = this.messenger.recv();


    if (resultMsg.equals("LOSE")) {
      String losePrompt = "You have lost the game!";
      this.getDisplayed(displayer, losePrompt);
      if (this.willingToAudit(displayer)) {
        this.messenger.send("Y"); // inform server (to not be socket-disconnected)
        this.gameState = 1; // audit game
      } else {
        this.messenger.send("N");  // inform server (to get socket-disconnected)
        this.gameState = 2; // quit game
      }

    } else if (resultMsg.equals("CONTINUE")) {  // continue the game
      return;
    } else { // there is a winner: produce the message, then quit
      this.getDisplayed(displayer, resultMsg);
      this.gameState = 2;  // quit game
    }
  }

  protected boolean willingToAudit (Displayer displayer) throws IOException {
    String auditPrompt = "Would you like to continue to watch the game ?\n";
    auditPrompt += "<Y>Yes <N>No";
    Boolean ans = false;
    Boolean inputValid = false;
    while (!inputValid) {
      this.getDisplayed(displayer, auditPrompt);
      String ansStr = this.scanner.next();
      if (ansStr.equals("Y")) {
        ans = true;
        inputValid = true;
      } else if (ansStr.equals("N")) {
        ans = false;
        inputValid = true;
      } else {
        String invalidPrompt = "That's not a valid option! Please choose again.";
        this.getDisplayed(displayer, invalidPrompt);
        this.getDisplayed(displayer, auditPrompt);
        inputValid = false;
      }

    }
    return ans;
  }
}

