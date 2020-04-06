package RISK.Game;


import RISK.ClassBuilder.BuildClassesException;
import RISK.ClassBuilder.ClassBuilder;
import RISK.Order.*;
import org.json.JSONObject;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class GameClientJSON extends GameClient<JSONObject> {

  public GameClientJSON(ClassBuilder<JSONObject> classBuilder) {
    super(classBuilder);
  }

  @Override
  public void initializeConnection(String serverIP,
                                   int serverPort)
    throws MessengerException, BuildClassesException {

    Socket socket;
    try {
      socket = new Socket(serverIP, serverPort);
    } catch (UnknownHostException e) {
      throw new MessengerException("That host address is unknown!");
    } catch (IOException e) {
      throw new MessengerException("Failed to connect to that address!");
    }

    try {
      this.messenger = new Messenger(socket);
    } catch (IOException e) {
      throw new MessengerException("IOException happens while creating messenger for this client!");
    }
    
    // request init info from server
    try {
      this.messenger.send("INIT");
    } catch (IOException e) {
      throw new MessengerException("Failed to send init request to server!");
    }

    // receive init info from server
    String initInfo;
    try {
      initInfo = this.messenger.recv();
    } catch (IOException e) {
      throw new MessengerException("Failed to receive init info from server!");
    }
    JSONObject initInfoJO = new JSONObject(initInfo);

    // get id
    this.playerID = initInfoJO.getInt("playerID");
    // get classes
    JSONObject classes = initInfoJO.getJSONObject("classes");
    // build classes
    this.classBuilder.buildAllClasses(classes, this);
  }

  @Override
  public void acceptOrder(Order order)
    throws InvalidLogicException, MessengerException {

    // verify order locally
    try {
      order.verify();
    } catch (InvalidLogicException e) {
      throw new InvalidLogicException("Denied by local Client: " + e.getMessage());
    }
    // verify order on server
    JSONObject orderN = (JSONObject)order.pton();

    try {
      this.verifyOnServer(orderN); // blocks here!
    } catch (InvalidLogicException e) {
      throw new InvalidLogicException("Denied by server: " + e.getMessage());
    } catch (IOException e) {
      throw new MessengerException("Cannot send order to server for verification!");
    }
    // execute the order
    order.execute();
  }

  // send orders to server for validation
  protected void verifyOnServer(JSONObject orderN)
          throws IOException, InvalidLogicException {
    String orderNStr = orderN.toString();
    this.messenger.send(orderNStr);

    // recv validity from server
    String msg = this.messenger.recv(); // !!blocks here
    if (!msg.equals("VALID")) {
      throw new InvalidLogicException(msg);
    }
    return;
  }
  
  @Override
  public String listenForUpdates()
          throws MessengerException, BuildClassesException {
    // receive init info from server
    try {
      String str = this.messenger.recv();
      // keep updating classes
      while (!str.equals("ENDOFTURN")) {
        JSONObject classes = new JSONObject(str);
        classBuilder.buildAllClasses(classes, this);
        str = this.messenger.recv();
      }
    } catch (IOException e) {
      throw new MessengerException("IO Error while receiving updates from server");
    }

    // receive result info from server
    String resultMsg = "";
    try {
      resultMsg = this.messenger.recv();
    } catch (IOException e) {
      throw new MessengerException("IO Error while receiving round reult from server");
    }

    if (resultMsg.equals("LOSE")) { // player lost the game
      this.gameState = 1;
    } else if (resultMsg.equals("CONTINUE")) {  // continue the game
      // do nothing
    } else { // there is a winner
      this.gameState = 2;  // quit game
    }
    return resultMsg;
  }

  @Override
  public void willingToAudit (boolean willAudit) throws MessengerException {
    try {
      if (willAudit) {
        this.messenger.send("Y"); // inform server (to not be socket-disconnected)
        this.gameState = 1; // audit game
      } else {
        this.messenger.send("N");  // inform server (to get socket-disconnected)
        this.gameState = 2; // quit game
      }
    } catch (IOException e) {
      throw new MessengerException("IO error while sending audit will to server");
    }
    return;
  }
}

