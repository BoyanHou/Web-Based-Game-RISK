package RISK;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

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
            
      for (int i = 0; i < clientThreads.length; i++) {
        GameClientJSON client;
        if (i != clientThreads.length - 1) {
          // two weak player's orders
          String weakOrder = "";
          weakOrder += "A\n" + (i+1) + "\n4\n1\nN\n"; // attack terr4 from own terr
          InputStream is = new ByteArrayInputStream(weakOrder.getBytes(StandardCharsets.UTF_8));

          Scanner weakPlayerScanner = new Scanner(is);
          client = this.getTestClient(port, weakPlayerScanner);

        } else {
          // one strong player's scanner
          String strongOrder = "";
          strongOrder += "A\n3\n1\n50\nN\n";    // attack terr1 from terr3
          InputStream is = new ByteArrayInputStream(strongOrder.getBytes(StandardCharsets.UTF_8));
          Scanner strongPlayerScanner = new Scanner(is);
          client = this.getTestClient(port, strongPlayerScanner);
        }
        
        ClientThread clientThread = new ClientThread(client);
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
      RISKGameServer.serverAcceptOrders(server);
      System.out.println("HHHH YEAH");
    }
  }

  public class ClientThread extends Thread {
    GameClientJSON client;

    public ClientThread(GameClientJSON client) {
      this.client = client;
    }

    @Override
    public void run() {
      
      RISKGameClient.testClientChooseMoves(this.client);
    }
  }

  protected GameClientJSON getTestClient(int port, Scanner scanner) {
    GameClientJSON client = RISKGameClient.setupClient("0.0.0.0", port, scanner);
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
