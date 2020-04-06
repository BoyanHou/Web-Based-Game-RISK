package RISK;

import java.io.IOException;
import java.util.Scanner;

import RISK.ClassBuilder.ClassBuilderEvo2;
import RISK.Displayer.Displayer;
import RISK.Factory.NtopFactoryJSON;
import RISK.Factory.PtonFactoryJSON;
import RISK.Game.GameClientJSON;


public class RISKGameClient {

  public static void run(String serverIP,
                         int serverPort,
                         Scanner scanner,
                         Displayer displayer) {
    
    GameClientJSON client = setupClient(serverIP, serverPort, scanner, displayer);
    while (client.getGameState() != 2) { // while game hasn't ended
      if (client.getGameState()!= 1) { // if not auditing
        testClientChooseMoves(client, displayer);
      }
      testClientListenForUpdate(client, displayer);
    }
    System.out.println("Client finishes game");
 }

  public static GameClientJSON setupClient(String serverIP,
                                           int serverPort,
                                           Scanner scanner,
                                           Displayer displayer) {
    try {
      GameClientJSON client = new GameClientJSON(serverIP,
                                                 8000,
                                                 scanner);
      // acquire info from server, setup game
      //Displayer displayer = new TextDisplayer();
      NtopFactoryJSON ntopFactory = new NtopFactoryJSON();
      ClassBuilderEvo2 classBuilder = new ClassBuilderEvo2(ntopFactory);
      client.initializeConnection(classBuilder, displayer);

      System.out.println("Assigned playerID:" + client.getPlayerID());
      return client;
    } catch (IOException e) {
      System.out.println("Client IOException");
    }
    return null;
  }

  public static void testClientChooseMoves(GameClientJSON client,
                                           Displayer displayer) {
    try {
      PtonFactoryJSON ptonFactory = new PtonFactoryJSON();
      //Displayer displayer = new TextDisplayer();
      client.chooseMoves(ptonFactory, displayer);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void testClientListenForUpdate(GameClientJSON client,
                                               Displayer displayer) {
    try {
      NtopFactoryJSON ntopFactory = new NtopFactoryJSON();
      ClassBuilderEvo2 classBuilder = new ClassBuilderEvo2(ntopFactory);
      client.listenForUpdates(classBuilder, displayer);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
