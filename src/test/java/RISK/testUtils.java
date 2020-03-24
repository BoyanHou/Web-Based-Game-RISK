package RISK;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import RISK.Army.ArmyRO;
import RISK.Game.GameServerJSON;
import RISK.Utils.NumUtils;

public class testUtils {
  
  @Test
  public void testNumUtils() {
    try {
      NumUtils.strToInt("");
    } catch (Exception e) {
        System.out.println("Successfully detect empty string error!");
    }
    try {
      NumUtils.strToInt("abc");
    } catch (Exception e) {
      System.out.println("Successfully detect non-digit error!");
    }
    try {
      NumUtils.strToInt("007");
    } catch (Exception e) {
      System.out.println("Successfully detect leading zero error!");
    }

  }

  @Test
  public void testGameRO() {
    GameServerJSON server = this.getTestServer(9123);
    HashMap<Integer, ArmyRO> armyMapRO = server.getArmyMapRO();
    for (int armyID : armyMapRO.keySet()) {
      assert (armyID == armyMapRO.get(armyID).getArmyID());
    }
  }

  protected GameServerJSON getTestServer(int port) {
    GameServerJSON server = RISKGameServer.serverSetUp(port,
                                                       "./src/main/resources/territoriesJSON_test3.txt",
                                                       "./src/main/resources/playersJSON_test3.txt",
                                                       "./src/main/resources/armiesJSON_test3.txt");
    return server;
  }

  



}