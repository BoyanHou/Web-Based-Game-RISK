// code reviewed by Yating Li
package RISK.ClientOperator;

import RISK.ClassBuilder.BuildClassesException;
import RISK.ClassBuilder.ClassBuilder;
import RISK.Game.GameClient;
import RISK.Game.InvalidOrderException;
import RISK.Game.Messenger;
import RISK.Game.MessengerException;
import RISK.Order.*;
import RISK.Player.Player;
import RISK.Territory.Territory;
import RISK.Utils.IntFormatException;
import RISK.Utils.NumUtils;

import java.util.HashMap;

public class ClientOperatorEvo2<T> extends ClientOperator<T> {

    public ClientOperatorEvo2(GameClient<T> gameClient,
                              OrderFactory<T> orderFactory) {
        super(gameClient, orderFactory);
    }

    @Override
    public void initConnection (String serverIPStr, String serverPortStr)
            throws ClientOperationException {
        String errorStr = "";

        // port
        int serverPort = 0;
        try {
            serverPort = NumUtils.strToInt(serverPortStr);
        } catch (IntFormatException e) {
            errorStr += "Invalid Option: " + "port integer format incorrect" + "\n";
        }
        if (errorStr.length() != 0) {
            throw new ClientOperationException(errorStr);
        }
        try {
            gameClient.initializeConnection(serverIPStr, serverPort);
        } catch (MessengerException e) {
            throw new ClientOperationException("Messenger Exception: " + e.getMessage());
        } catch (BuildClassesException e) {
            throw new ClientOperationException("Failed to build all classes: "+e.getMessage());
        }
    }

    @Override
    public void stopMakingOrder() throws ClientOperationException {
        try {
            gameClient.endRound();
        } catch (MessengerException e) {
            throw new ClientOperationException("Messenger Exception: " + e.getMessage());
        }
    }

    @Override
    public void makeOrder(String orderType, HashMap<String, String> parameterMap)
            throws ClientOperationException {
        Order order = null;
        try {
            order = this.orderFactory.makeOrderByStrings(
                    this.gameClient,
                    this.gameClient.getPlayerID(),
                    parameterMap,
                    orderType);
        } catch (WrongOrderVersionException e) {
            throw new ClientOperationException(e.getMessage());
        } catch (InvalidOptionException e) {
            throw new ClientOperationException(e.getMessage());
        }

        try {
            this.gameClient.acceptOrder(order);
        } catch (InvalidLogicException e) {
            throw new ClientOperationException("Logic error: " + e.getMessage());
        } catch (MessengerException e) {
            throw new ClientOperationException("Messenger error: " + e.getMessage());
        }
    }

    @Override
    public String listenForUpdates() throws ClientOperationException {
        try {
            return this.gameClient.listenForUpdates();
        } catch (MessengerException e) {
            throw new ClientOperationException("Messenger error: " + e.getMessage());
        } catch (BuildClassesException e) {
            throw new ClientOperationException("Failed to update-build classes: " + e.getMessage());
        }
    }

    @Override
    public void AuditOrNot (String willAudit) throws ClientOperationException {
        try {
            if (willAudit.equals("YES")) {
                this.gameClient.willingToAudit(true);
            } else if (willAudit.equals("NO")) {
                this.gameClient.willingToAudit(false);
            } else {
                throw new ClientOperationException("Cannot recognize that option:" + willAudit + "; answer YES or NO");
            }
        } catch (MessengerException e) {
            throw new ClientOperationException("Messenger Error: " + e.getMessage());
        }
    }

}
