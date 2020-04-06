package RISK;

import java.util.Scanner;
import RISK.Utils.IntFormatException;
import RISK.Utils.NumUtils;

public class RISKGame {
//  public static void main(String[] args) {
//    try {
//      if (args.length == 3 && args[0].equals("server")) {
//        int portNum = NumUtils.strToInt(args[1]);
//        int playerNum = NumUtils.strToInt(args[2]);
//        String resourceRoot = "./src/main/resources/";
//        String terrPath = resourceRoot + "territoriesJSON_" + playerNum + ".txt";
//        String playerPath = resourceRoot + "playersJSON_" + playerNum + ".txt";
//        String armyPath = resourceRoot + "armiesJSON_" + playerNum + ".txt";
//
//        RISKGameServer.run(portNum, terrPath, playerPath, armyPath);
//      } else if (args.length == 3 && args[0].equals("client")) {
//        int serverPort = NumUtils.strToInt(args[1]);
//        String serverIP = args[2];
//        Scanner scanner = new Scanner(System.in); // use console input
//        Displayer displayer = new TextDisplayer();
//        RISKGameClient.run(serverIP, serverPort, scanner, displayer);
//      } else {
//        String mannual = "use 1: server <portNum> <playerNum>\n";
//        mannual += "    2: client <serverPort> <serverIP>";
//        System.out.println(mannual);
//      }
//    } catch (IntFormatException e) {
//      String portWarn = "Integer Format Incorrect!";
//      System.out.println(portWarn);
//    }
//    return;
//  }
}
