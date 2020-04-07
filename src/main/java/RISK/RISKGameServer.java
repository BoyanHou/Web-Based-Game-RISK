package RISK;

import RISK.ClassBuilder.ClassBuilderEvo2;
import RISK.CombatResolver.CombatResolver;
import RISK.CombatResolver.DiceCombatResolver;
import RISK.Game.GameServerJSON;
import RISK.Order.OrderFactory;
import RISK.Order.OrderFactoryEvo2;

import java.io.IOException;

public class RISKGameServer {
  public static void run(int port,
                         String terrPath,
                         String playerPath,
      String armyPath) {
    System.out.println("ServerSetup");
    GameServerJSON server = serverSetUp(port,
                                        terrPath,
                                        playerPath,
        armyPath);
    if (server == null) {
      System.out.println("Server Setup Failed");
    }
    serverAcceptConnections(server);
    while (server.getGameState() == 0) { // game still runs
      serverAcceptOrders(server);
      serverResolveCombats(server);
    }
    System.out.println("Server finishes game");
  }

  public static GameServerJSON serverSetUp(int port,
                                           String terrPath,
                                           String playerPath,
                                           String armyPath) {
        try {
            ClassBuilderEvo2 classBuilder = new ClassBuilderEvo2();
            OrderFactory orderFactory = new OrderFactoryEvo2();
            GameServerJSON serverJSON = new GameServerJSON(
                    port,
                    classBuilder,
                    terrPath,
                    playerPath,
                    armyPath,
                    orderFactory);
            return serverJSON;
        } catch (Exception e) {
        }
        return null;
    }

    public static void serverAcceptConnections(GameServerJSON server) {
        try {
            server.acceptConnections();
        } catch (IOException e) {
            System.out.println("Server failed to accept connections");
        }
    }

  public static void serverAcceptOrders(GameServerJSON server) {
    try {
      server.acceptOrders();
    } catch (IOException e) {
      System.out.println("Server failed to accept connections");
    }
  }

  public static void serverResolveCombats(GameServerJSON server) {
    try {
      CombatResolver combatResolver = new DiceCombatResolver(20);
      server.resolveCombats(combatResolver);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
