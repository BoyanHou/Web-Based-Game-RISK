package RISK.Utils;

import java.io.*;

public class TxtReader {
  public static String readStrFromFile (String path) throws IOException{
    String res = "";    

    // open txt file
    FileReader fileReader = new FileReader(path);
    BufferedReader bufferReader = new BufferedReader(fileReader);

    // read line by line
    String row;
    while((row = bufferReader.readLine()) != null) {  
      res += row;
    }

    bufferReader.close();
    
    return res;
  }
}
