package RISK.Tests;

import RISK.RISKGameServer;

public class ServerTest {
    public static void main(String args[]) {
        RISKGameServer.run(8000,
                "terr_test_3.txt",
                "player_test_3.txt",
                "army_test_3.txt"); }
//                    "territoriesJSON_2.txt",
//                  "playersJSON_2.txt",
//                   "armiesJSON_2.txt"); }
}
