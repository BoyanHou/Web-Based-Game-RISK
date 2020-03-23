package RISK;

import java.io.IOException;
import java.util.Scanner;

import RISK.ClassBuilder.ClassBuilderJSON;
import RISK.Displayer.Displayer;
import RISK.Factory.NtopFactoryJSON;
import RISK.Factory.PtonFactoryJSON;
import RISK.Game.GameClientJSON;
import RISK.Utils.TextDisplayer;

public class RISKGameClient {

  public static void run(String serverIP,
                         int serverPort,
                         Scanner scanner) {
    
    GameClientJSON client = setupClient(serverIP, serverPort, scanner);
    while (client.getGameState() != 2) { // while game hasn't ended
      if (client.getGameState()!= 1) { // if not auditing
        testClientChooseMoves(client);
      }
      testClientListenForUpdate(client);
    }
    System.out.println("Client finishes game");
  }

  public static GameClientJSON setupClient(String serverIP,
                                           int serverPort,
                                           Scanner scanner) {
    try {
      GameClientJSON client = new GameClientJSON(serverIP,
                                                 8000,
                                                 scanner);
      // acquire info from server, setup game
      Displayer displayer = new TextDisplayer();
      NtopFactoryJSON ntopFactory = new NtopFactoryJSON();
      ClassBuilderJSON classBuilder = new ClassBuilderJSON(ntopFactory);
      client.initializeConnection(classBuilder, displayer);

      System.out.println("Assigned playerID:" + client.getPlayerID());
      return client;
    } catch (IOException e) {
      System.out.println("Client IOException");
    }
    return null;
  }

  public static void testClientChooseMoves(GameClientJSON client) {
    try {
      PtonFactoryJSON ptonFactory = new PtonFactoryJSON();
      Displayer displayer = new TextDisplayer();
      client.chooseMoves(ptonFactory, displayer);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void testClientListenForUpdate(GameClientJSON client) {
    try {
      Displayer displayer = new TextDisplayer();
      NtopFactoryJSON ntopFactory = new NtopFactoryJSON();
      ClassBuilderJSON classBuilder = new ClassBuilderJSON(ntopFactory);
      client.listenForUpdates(classBuilder, displayer);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
