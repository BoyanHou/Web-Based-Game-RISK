package RISK;

import RISK.ClassBuilder.ClassBuilderJSON;
import RISK.CombatResolver.CombatResolver;
import RISK.CombatResolver.DiceCombatResolver;
import RISK.Factory.NtopFactory;
import RISK.Factory.NtopFactoryJSON;
import RISK.Factory.PtonFactory;
import RISK.Factory.PtonFactoryJSON;
import RISK.Game.GameServerJSON;
import org.json.JSONObject;

import java.io.IOException;

public class RISKGameServer {
  public static void run(int port,
                         String terrPath,
                         String playerPath,
                         String armyPath) {
    GameServerJSON server = serverSetUp(port,
                                        terrPath,
                                        playerPath,
                                        armyPath);
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
            NtopFactoryJSON ntopFactory = new NtopFactoryJSON();
            ClassBuilderJSON classBuilder = new ClassBuilderJSON(ntopFactory);
            GameServerJSON serverJSON = new GameServerJSON(port, classBuilder,
                                                           terrPath,
                                                           playerPath,
                                                           armyPath);
            return serverJSON;
        } catch (Exception e) {
        }
        return null;
    }

    public static void serverAcceptConnections(GameServerJSON server) {
        try {
            PtonFactory<JSONObject> ptonFactory = new PtonFactoryJSON();
            server.acceptConnections(ptonFactory);
        } catch (IOException e) {
            System.out.println("Server failed to accept connections");
        }
    }

  public static void serverAcceptOrders(GameServerJSON server) {
    try {
      NtopFactory<JSONObject> ntopFactory = new NtopFactoryJSON();
      server.acceptOrders(ntopFactory);
    } catch (IOException e) {
      System.out.println("Server failed to accept connections");
    }
  }

  public static void serverResolveCombats(GameServerJSON server) {
    try {
      CombatResolver combatResolver = new DiceCombatResolver(20);
      PtonFactory<JSONObject> ptonFactory = new PtonFactoryJSON();
      server.resolveCombats(combatResolver, ptonFactory);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
