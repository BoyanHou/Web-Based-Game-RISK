package RISK.Utils;

public class NumUtils {

  // convert string to int
  // currently does not support non-digit (e.g. '+', '-', etc)
  public static int strToInt(String str) throws IntFormatException {
    if (str.length() == 0) {
      throw new IntFormatException();
    }
    
    int res = 0;
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      if (!Character.isDigit(ch)) {
        throw new IntFormatException();
      }
      int digit = ch - '0';
      if (i == 0 && str.length() != 1 && digit == 0) { // no leading zeroes!
        throw new IntFormatException();
      }
      res = res * 10 + digit;
    }

    return res;
  }
}
