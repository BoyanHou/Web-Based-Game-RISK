// code reviewed by Yating Li
package RISK.ClientOperator;

import RISK.Game.GameClient;
import RISK.Order.OrderFactory;

import java.util.HashMap;

public abstract class ClientOperator<T> {
    public GameClient<T> gameClient;
    OrderFactory<T> orderFactory;
    public ClientOperator(GameClient<T> gameClient, OrderFactory<T> orderFactory) {
        this.orderFactory = orderFactory;
        this.gameClient = gameClient;
    }

    // initialize connection with server
    // build all classes
    public abstract void initConnection (String serverIPStr, String serverPortStr)
            throws ClientOperationException;

    // tell the server: i've finished making order this turn
    public abstract void stopMakingOrder() throws ClientOperationException;

    // listen for updates from server
    // will update game models during the process
    // return a String:
    //    "LOSE": player has lost the game --> should decide & call willAudit() function to inform server, to audit or not
    //    "CONTINUE": player can proceed to next round
    //     otherwise: someone has won, print this String and exit game
    public abstract String listenForUpdates() throws ClientOperationException;

    // attack order: enter "attack" for orderType
    //   "myTerrName" : "XXX"
    //   "targetTerrName" : "XXX"
    //   "2":"1" <-- level2 units: 1
    //   "5":"3" <-- level5 units: 3

    // move order: enter "move" for orderType
    //   "fromTerrName" : "XXX"
    //   "toTerrName" : "XXX"
    //   "2":"1" <-- level2 units: 1
    //   "5":"3" <-- level5 units: 3

    // upgrade order: enter "upgrade" for orderType
    //   "onTerrName" : "XXX"
    //   "fromLevel":"1"
    //   "toLevel":"3"

    // fog order: enter "fog" for orderType
    //   "onTerrName": "XXX"

    // convertSpy order: enter "convertSpy" for orderType
    //   "onTerrName": "XXX"

    // moveSpy order: enter "moveSpy" for orderType
    //   fromTerrName: "XXX"
    //   toTerrName: "XXX"
    public abstract void makeOrder(String orderType, HashMap<String, String> parameterMap)
            throws ClientOperationException;

    // call this function after lose the game
    // willAudit = "YES": inform server that you want to continue to audit the game
    // willAudit = "NO": inform server that you will exit the game
    public abstract void AuditOrNot (String willAudit) throws ClientOperationException;
}
