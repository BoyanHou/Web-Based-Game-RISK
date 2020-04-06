package RISK.Tests;

import RISK.RISKGameServer;

public class ServerTest {
    public static void main(String args[]) {
        RISKGameServer.run(8000,
                    "/Users/liyating/Documents/651/ece651-spr20-g7/src/main/java/RISK/terr.txt",
                  "/Users/liyating/Documents/651/ece651-spr20-g7/src/main/java/RISK/player.txt",
                   "/Users/liyating/Documents/651/ece651-spr20-g7/src/main/java/RISK/army.txt"); }
}
