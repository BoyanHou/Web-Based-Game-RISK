package RISK;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import RISK.Displayer.Displayer;
import RISK.Displayer.DisplayerStub;
import RISK.Game.GameClientJSON;
import RISK.Game.GameServerJSON;

public class testGameServer {
  //@Test
  public void test_ServerSetup() {
    int port = 9000;
    GameServerJSON server = getTestServer(port);

    assert (server.getPlayerMap().size() == 3);
    assert (server.getArmyMap().size() == 4);
    assert (server.getTerrMap().size() == 4);
  }

  @Test
  public void test_3Client1ServerInteraction() {
    try {
      int port = 8000;
      int playerNum = 3;

      // setup local server first
      GameServerJSON server = getTestServer(port);
      ServerThread serverThread = new ServerThread(server);
      serverThread.start();
    
      // clients connect to server
      // and initialize 
      ClientThread clientThreads[] = new ClientThread[playerNum];
      // player orders
      String orders[] = new String[playerNum];
      // player1: terr1(1)
      orders[0] = "";
      orders[0] += "A\n1\n4\n1\nN\n"; // attack terr4 from terr1 (round1)
      orders[0] += "N\n";             // dies, no auditing (round2)
      
      // player2: terr2(1)
      orders[1] = "";
      orders[1] += "A\n2\n4\n1\nN\n";   // attack terr4 from terr2 (round1)
      orders[1] += "A\n2\n4\n1\nN\n";   // attack terr4 from terr2 (round2)

      // player3: terr3(50), terr4(50)
      orders[2] = "";
      orders[2] += "A\n3\n1\n50\nN\n"; // attack terr1 from terr3 (round1)
      orders[2] += "A\n1\n2\n30\nN\n"; // attack terr2 from terr1 (round2)

      // player3 should have won the game after round2
      
      for (int i = 0; i < clientThreads.length; i++) {
        GameClientJSON client;
        if (i != clientThreads.length - 1) {
          // two weak player's orders
          String weakOrder = orders[i];
          InputStream is = new ByteArrayInputStream(weakOrder.getBytes(StandardCharsets.UTF_8));
          Scanner weakPlayerScanner = new Scanner(is);
          Displayer displayer = new DisplayerStub();
          client = this.getTestClient(port, weakPlayerScanner, displayer);

        } else {
          // one strong player's scanner
          String strongOrder = orders[2];
          InputStream is = new ByteArrayInputStream(strongOrder.getBytes(StandardCharsets.UTF_8));
          Scanner strongPlayerScanner = new Scanner(is);
          Displayer displayer = new DisplayerStub();
          client = this.getTestClient(port, strongPlayerScanner, displayer);
        }
        Displayer displayer = new DisplayerStub();
        ClientThread clientThread = new ClientThread(client, displayer);
        clientThread.start();
        clientThreads[i] = clientThread;
      }

      for (ClientThread clientThread : clientThreads) {
        clientThread.join();
      }
      
      serverThread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public class ServerThread extends Thread {
    GameServerJSON server;

    public ServerThread(GameServerJSON server) {
      this.server = server;
    }

    public void run() {
      RISKGameServer.serverAcceptConnections(this.server);
 
      while (server.getGameState() == 0) { // game still runs
        RISKGameServer.serverAcceptOrders(server);
        RISKGameServer.serverResolveCombats(server);
      }
      System.out.println("Server finishes game");
      //System.out.println("HHHH YEAH");
    }
  }

  public class ClientThread extends Thread {
    GameClientJSON client;
    Displayer displayer;

    public ClientThread(GameClientJSON client, Displayer displayer) {
      this.displayer = displayer;
      this.client = client;
    }

    @Override
    public void run() {      
      while (client.getGameState() != 2) { // while game hasn't ended
        if (client.getGameState()!= 1) { // if not auditing
          RISKGameClient.testClientChooseMoves(this.client, displayer);
        }
        RISKGameClient.testClientListenForUpdate(this.client, displayer);
      }
      System.out.println("Client finishes game");
    }
  }

  protected GameClientJSON getTestClient(int port, Scanner scanner, Displayer displayer) {
    GameClientJSON client = RISKGameClient.setupClient("0.0.0.0", port, scanner, displayer);
    return client;
  }


  protected GameServerJSON getTestServer(int port) {
    GameServerJSON server = RISKGameServer.serverSetUp(port,
                                                       "./src/main/resources/territoriesJSON_test3.txt",
                                                       "./src/main/resources/playersJSON_test3.txt",
                                                       "./src/main/resources/armiesJSON_test3.txt");
    return server;
  }

}
