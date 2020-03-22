package RISK.Utils;

import java.io.*;

public class TxtReader {
  public static String readStrFromFile (String path) throws IOException{
    String res = "";    

    // open txt file
    FileReader fileReader = new FileReader(path);
    BufferedReader bufferReader = new BufferedReader(fileReader);
    StringBuffer stringBuffer = new StringBuffer();

    // read line by line
    String row;
    while((row = bufferReader.readLine()) != null) {  
      res += row;
    }
    
    return res;
  }
}
