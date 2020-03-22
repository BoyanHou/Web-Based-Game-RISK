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

  public static void run(String serverIP, int serverPort) {

    GameClientJSON client = testClientSetUp(serverIP, serverPort);
    while (client.getGameState() != 2) { // while game hasn't ended
      if (client.getGameState()!= 1) { // if not auditing
        testClientChooseMoves(client);
      }
      testClientListenForUpdate(client);
    }
    System.out.println("Client finishes game");
  }

  public static GameClientJSON testClientSetUp(String serverIP, int serverPort) {
    try {
      Scanner scanner = new Scanner(System.in);
      GameClientJSON client = new GameClientJSON("0.0.0.0",
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
