package RISK;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import RISK.Utils.TxtReader;

public class testGameServer {

  @Test
  void open_file_test() {
    try {
      String str = TxtReader.readStrFromFile("./src/main/resources/terr_test_3.txt");
      System.out.println(str);
    } catch (IOException e) {
      System.out.println("IOException");
    }
  }
  
  @Test
  public void test_3Client1ServerInteraction() {
    try {
      // run server thread
      int port = 8000;
      boolean display = true;
      
      //server:
      String terrPath = "./src/main/resources/terr_test_3.txt";
      String playerPath = "./src/main/resources/player_test_3.txt";
      String armyPath = "./src/main/resources/army_test_3.txt";
      ServerThread sThread = new ServerThread(port,
                                              terrPath,
                                              playerPath,
                                              armyPath);      
      sThread.start();

      Thread.sleep(5000);
      
      String connectionStr = "0.0.0.0\n" + Integer.toString(port) + "\n";
      
      // run client threads
      ClientThread clientThreads[] = new ClientThread[3];
      // player orders
      String orders[] = new String[3];
      // player1: terr1(1)
      orders[0] = "";
      orders[0] += connectionStr;
      orders[0] += "u\n1\n0\n1\nn\n"; // upgrade one unit one terr1 from lv1 to lv2 (round1)
      orders[0] += "y\n";             // dies, auditing (round2)
      clientThreads[0] = new ClientThread(orders[0], display);
      clientThreads[0].start();

      Thread.sleep(5000); // insure order
      
      // player2: terr2(1)
      orders[1] = "";
      orders[1] += connectionStr;
      orders[1] += "u\n2\n0\n1\nn\n";   // upgrade one unit on terr2 from lv0 to lv1 (round1)
      orders[1] += "u\n2\n0\n1\nn\n";   // upgrade one unit on terr2 from lv0 to lv1 (round2)
      orders[1] += "u\n2\n0\n1\nn\n";   // upgrade one unit on terr2 from lv0 to lv1 (round3)
      clientThreads[1] = new ClientThread(orders[1], display);
      clientThreads[1].start();


      Thread.sleep(5000); // insure order

      orders[2] = "";
      orders[2] += connectionStr;
      orders[2] += "a\n3\n1\n5\n200\nn\nn\n"; // attack terr1 from terr3, with 10*lv5 (round1)
      orders[2] += "m\n3\n1\n1\n1\ny\n4\n2\ny\n6\n10\nn\nn\n";  // move (round2)
      orders[2] += "a\n1\n2\n5\n100\nn\nn\n"; // attack terr2 from terr1 (round3)
      clientThreads[2] = new ClientThread(orders[2], display);
      clientThreads[2].start();

      // joins
      for (ClientThread clientThread : clientThreads) {
        clientThread.join();
      }
      sThread.join();

      
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public class ClientThread extends Thread {
    String orders;
    boolean display;
    
    public ClientThread(String orders, boolean display) {
      this.orders = orders;
      this.display = display;
    }

    @Override
    public void run() {
      //InputStream is = new ByteArrayInputStream(this.orders.getBytes());
      InputStream is = new ByteArrayInputStream(this.orders.getBytes(StandardCharsets.UTF_8));
      //InputStream in = new ByteArrayInputStream(orders.getBytes());
      //System.setIn(in);
      Scanner scanner = new Scanner(is);
      
      RiskGameClientText r = new RiskGameClientText(scanner);
      r.run(display);
    }
  }

  protected class ServerThread extends Thread {
    int port;
    String terrPath;
    String playerPath;
    String armyPath;

    public ServerThread(int port, String terrPath, String playerPath, String armyPath) {
      this.port = port;
      this.terrPath = terrPath;
      this.playerPath = playerPath;
      this.armyPath = armyPath;
    }

    @Override
    public void run() {
      RISKGameServer.run(port, terrPath, playerPath, armyPath);
    }
  }
}
