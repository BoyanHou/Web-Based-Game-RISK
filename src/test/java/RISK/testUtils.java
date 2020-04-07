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
}
