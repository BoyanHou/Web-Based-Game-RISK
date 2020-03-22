package RISK;

import org.junit.jupiter.api.Test;

import RISK.Game.GameServerJSON;

public class testGameServer {
  @Test
  public void test_ServerSetup() {
    int port = 8000;
    int playerNum = 3;
    GameServerJSON server = RISKGameServer.testServerSetUp(port, playerNum);
    assert(server.getPlayerMap().size() == 3);
  }

}
