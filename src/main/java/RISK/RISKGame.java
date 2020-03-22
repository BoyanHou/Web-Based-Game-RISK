package RISK;

import RISK.Utils.*;

import RISK.Utils.IntFormatException;

public class RISKGame {
  public static void main(String[] args) {
    try {
      if (args.length == 3 && args[0].equals("server")) {
        int portNum = NumUtils.strToInt(args[1]);
        int playerNum = NumUtils.strToInt(args[2]);
        RISKGameServer.run(portNum, playerNum);
      } else if (args.length == 3 && args[0].equals("client")) {
        int serverPort = NumUtils.strToInt(args[1]);
        String serverIP = args[2];
        RISKGameClient.run(serverIP, serverPort);        
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
