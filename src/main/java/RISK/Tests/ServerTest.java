package RISK.Tests;

import RISK.RISKGameServer;

public class ServerTest {
    public static void main(String args[]) {
        RISKGameServer.run(8000,
                    "terr.txt",
                  "player.txt",
                   "army.txt");
    }
}
