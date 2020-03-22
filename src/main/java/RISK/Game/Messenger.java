package RISK.Game;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Messenger {
  Socket socket;
  
  public Messenger(Socket socket) throws IOException {
    this.socket = socket;
  }

  // send a string: ensure full transmission
//  public void send(String str) throws IOException {
//    Boolean success = false;
//    do {
//      this.simpleSend(str);
//      String condition = this.recv();
//      if (condition.equals("@RECEIVED")) {
//        success = true;
//      }
//    } while (!success);
//    return;
//  }

  public void send(String str) throws IOException {
    this.simpleSend(str);
    String confirmation = this.simpleRecv();  // block here for "ACK"
    if (!confirmation.equals("ACK")) {
      throw new IOException();
    }
  }

  public void simpleSend(String str) throws IOException {
    OutputStream outputStream = this.socket.getOutputStream();
    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
    bufferedWriter.write(str + "\n") ;
    bufferedWriter.flush();
  }

//  public String recv() throws IOException {
//    String res = "";
//    Boolean valid = false;
//    do {
//      res = this.simpleRecv();
//      if (res.length() >= 15) {
//        String header = res.substring(0, 7);
//        int len = res.length();
//        String trailer = res.substring(len-8, len);
//        if (header.equals("@HEADER") && trailer.equals("@TRAILER")) {
//          res = res.substring(7, len - 8);
//          this.simpleSend("@RECEIVED");
//          valid = true;
//        }  else {
//          this.simpleSend("@SENDAGAIN");
//        }
//      } else {
//        this.simpleSend("@SENDAGAIN");
//      }
//    }
//    while(!valid);
//    return res;
//  }
  public String recv() throws IOException {
    String str = this.simpleRecv();
    this.simpleSend("ACK");
    return str;
  }

  // receive a string
  public String simpleRecv() throws IOException {
    String str;
    InputStream inputStream = this.socket.getInputStream();
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    str = bufferedReader.readLine();
    return str;
  }
}
