package RISK;

import java.net.ServerSocket;
import java.net.Socket;

import org.junit.jupiter.api.Test;

import RISK.Game.Messenger;

public class testMssenger {
  @Test
  public void test_Msger() {
    try {
      Sender sender = new Sender();
      Receiver receiver = new Receiver();
      receiver.start();
      Thread.sleep(10000);
      sender.start();

      sender.join();
      receiver.join();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public class Sender extends Thread {
    @Override
    public void run() {
      try {
        Socket socket;
      
        socket = new Socket("0.0.0.0", 7000);
        Messenger msger = new Messenger(socket);
        msger.send("ABC123");
        
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public class Receiver extends Thread
  {
    @Override
    public void run() {
      try {
        ServerSocket ss;
        
        ss = new ServerSocket(7000);
        Socket socket = ss.accept();
        Messenger msger = new Messenger(socket);
        String msg = msger.recv();
        System.out.println(msg);
        ss.close();
        
      } catch (Exception e) {
        e.printStackTrace();
      }
      
    }
  }

}
