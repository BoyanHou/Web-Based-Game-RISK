package RISK;

import RISK.ClassBuilder.ClassBuilderEvo2;
import RISK.ClientOperator.ClientOperationException;
import RISK.ClientOperator.ClientOperator;
import RISK.ClientOperator.ClientOperatorEvo2;
import RISK.GUI.app;
import RISK.Game.GameClientJSON;
import RISK.Order.OrderFactoryEvo2;
import RISK.Utils.IntFormatException;
import RISK.Utils.NumUtils;
import org.json.JSONObject;

public class RISKGame {
  public static void main(String[] args) {
    try {
      if (args.length == 3 && args[0].equals("server")) {
        int portNum = NumUtils.strToInt(args[1]);
        int playerNum = NumUtils.strToInt(args[2]);
        String resourceRoot = "./src/main/resources/";
        String terrPath = resourceRoot + "territoriesJSON_" + playerNum + ".txt";
        String playerPath = resourceRoot + "playersJSON_" + playerNum + ".txt";
        String armyPath = resourceRoot + "armiesJSON_" + playerNum + ".txt";

        RISKGameServer.run(portNum, terrPath, playerPath, armyPath);
      } else if (args.length == 3 && args[0].equals("client")) {
          String serverPort = args[1];
        String serverIP = args[2];
        // make client
          ClassBuilderEvo2 classBuilder = new ClassBuilderEvo2();
          GameClientJSON client = new GameClientJSON(classBuilder);
          // make client operator
          OrderFactoryEvo2 orderFactory = new OrderFactoryEvo2();
          ClientOperator<JSONObject> clientOperator = new ClientOperatorEvo2<JSONObject>(client, orderFactory);
          // connect 
          try {
            clientOperator.initConnection(serverIP, serverPort);
          } catch (ClientOperationException e) {
              System.out.println(e.getMessage());
          }
          // run GUI
        new app(clientOperator);
      } else {
        String mannual = "use 1: server <portNum> <playerNum>\n";
        mannual += "    2: client <serverPort> <serverIP>";
        System.out.println(mannual);
      }
    } catch (IntFormatException e) {
      String portWarn = "Integer Format Incorrect!";
      System.out.println(portWarn);
    }
    return;
  }
}
