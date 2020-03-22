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
  public static void run(int port, int playerNum) {
    GameServerJSON server = testServerSetUp(port, playerNum);
        testServerAcceptConnections(server);
        while (server.getGameState() == 0) { // game still runs
            testServerAcceptOrders(server);
            testServerResolveCombats(server);
        }
        System.out.println("Server finishes game");
    }

  public static GameServerJSON testServerSetUp(int port, int playerNum) {
        try {
            NtopFactoryJSON ntopFactory = new NtopFactoryJSON();
            ClassBuilderJSON classBuilder = new ClassBuilderJSON(ntopFactory);
            String resourceRoot = "./src/main/resources/";
            String terrPath = resourceRoot + "territoriesJSON_" + playerNum + ".txt";
            String playerPath = resourceRoot + "playersJSON_" + playerNum + ".txt";
            String armyPath = resourceRoot + "armiesJSON_" + playerNum + ".txt";
            GameServerJSON serverJSON = new GameServerJSON(port, classBuilder,
                                                           terrPath,
                                                           playerPath,
                                                           armyPath);
            return serverJSON;
        } catch (Exception e) {
        }
        return null;
    }

    public static void testServerAcceptConnections(GameServerJSON server) {
        try {
            PtonFactory<JSONObject> ptonFactory = new PtonFactoryJSON();
            server.acceptConnections(ptonFactory);
        } catch (IOException e) {
            System.out.println("Server failed to accept connections");
        }
    }

    public static void testServerAcceptOrders(GameServerJSON server) {
        try {
            NtopFactory<JSONObject> ntopFactory = new NtopFactoryJSON();
            server.acceptOrders(ntopFactory);
        } catch (IOException e) {
            System.out.println("Server failed to accept connections");
        }
    }

    public static void testServerResolveCombats(GameServerJSON server) {
        try {
            CombatResolver combatResolver = new DiceCombatResolver(20);
            PtonFactory<JSONObject> ptonFactory = new PtonFactoryJSON();
            server.resolveCombats(combatResolver, ptonFactory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
