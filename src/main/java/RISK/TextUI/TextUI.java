package RISK.TextUI;
import RISK.ClientOperator.ClientOperationException;
import RISK.ClientOperator.ClientOperator;
import RISK.Game.Game;
import RISK.Game.GameClient;
import RISK.Player.Player;
import RISK.Territory.Territory;
import RISK.Unit.Unit;
import RISK.Utils.IntFormatException;
import RISK.Utils.MsgException;
import RISK.Utils.NumUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class TextUI {
    protected Scanner scanner;
    protected boolean display;
    protected ClientOperator clientOperator;
    protected GameClient client;
    protected int playerID;

    public TextUI(ClientOperator clientOperator,
                  Scanner scanner,
                  boolean display) {
        this.client = clientOperator.gameClient;
        this.playerID = clientOperator.gameClient.getPlayerID();
        this.clientOperator = clientOperator;
        this.scanner = scanner;
        this.display = display;
    }

    public void run () throws ClientOperationException{
        this.connectionUI();
        while (this.client.getGameState() == 0) {
            this.opUI();
            this.clientOperator.stopMakingOrder();
            this.receiveUpdateUI();
        }
        while (this.client.getGameState() == 1) {
            this.receiveUpdateUI();
        }
        return;
    }

    protected void connectionUI() {
        this.text("ip");
        String ip = this.scanner.next();

        this.text("port");
        String port = this.scanner.next();
        try {
            this.clientOperator.initConnection(ip, port);
        } catch (ClientOperationException e) {
            this.text(e.getMessage());
            this.connectionUI();
        }
        this.dMap();
    }


    protected void opUI() {
        this.dMap();
        this.text("order? m/a/u");
        String choice = this.scanner.next();
        HashMap<String, String> paraMap = new HashMap<>();
        try {
            if (choice.equals("m")) {
                this.text("move:fromID");
                String fromTerr = scanner.next();
                String fromTerrName = this.client.getTerrMap().get(NumUtils.strToInt(fromTerr)).getName();
                paraMap.put("fromTerrName", fromTerrName);
                this.text("move:toID");
                String toTerr = scanner.next();
                String toTerrName = this.client.getTerrMap().get(NumUtils.strToInt(toTerr)).getName();
                paraMap.put("toTerrName", toTerrName);
                boolean con = true;
                while (con) {
                    this.text("lv?");
                    String level = scanner.next();
                    this.text("num?");
                    String num = scanner.next();
                    paraMap.put(level, num);
                    this.text("next? y/n");
                    String conStr = this.scanner.next();
                    if (!conStr.equals("y")) {
                        con = false;
                    }
                }
                this.clientOperator.makeOrder("move", paraMap);
            } else if (choice.equals("a")) {
                this.text("atk:myTerrID");
                String myTerr = scanner.next();
                String myTerrName = this.client.getTerrMap().get(NumUtils.strToInt(myTerr)).getName();
                paraMap.put("myTerrName", myTerrName);
                this.text("atk:targetTerrID");
                String targetTerr = scanner.next();
                String targetTerrName = this.client.getTerrMap().get(NumUtils.strToInt(targetTerr)).getName();
                paraMap.put("targetTerrName", targetTerrName);
                boolean con = true;
                while (con) {
                    this.text("lv?");
                    String level = scanner.next();
                    this.text("num?");
                    String num = scanner.next();
                    paraMap.put(level, num);
                    this.text("next? y/n");
                    String conStr = this.scanner.next();
                    if (!conStr.equals("y")) {
                        con = false;
                    }
                }
                this.clientOperator.makeOrder("attack", paraMap);
            } else {
                this.text("onTerr?");
                String onTerr = this.scanner.next();
                String onTerrName = this.client.getTerrMap().get(NumUtils.strToInt(onTerr)).getName();
                paraMap.put("onTerrName", onTerrName);
                this.text("from Level?");
                String fromLevel = this.scanner.next();
                paraMap.put("fromLevel", fromLevel);
                this.text("to Level?");
                String toLevel = this.scanner.next();
                paraMap.put("toLevel", toLevel);
                this.clientOperator.makeOrder("upgrade", paraMap);
            }
        } catch(MsgException e) {
            this.text(e.getMessage());
            this.opUI();
        } catch (IntFormatException e) {
            this.opUI();
        }
        text("another? y/n");
        String ans = this.scanner.next();
        if (ans.equals("y")) {
          this.opUI();
        }
    }

    protected void receiveUpdateUI() throws ClientOperationException {
        this.text("waiting for updates...");
        String result = "";
        try {
            result = this.clientOperator.listenForUpdates();
            if (result.equals("CONTINUE")) {
                return;
            } else if (result.equals("LOSE")) {
                this.auditQueryUI();
                return;
            } else {
                this.text(result);
            }
        } catch (ClientOperationException e) {
            this.text(e.getMessage());
            throw e;
        }
    }

    protected void auditQueryUI() {
        this.text("audit? y/n");
        String audit = this.scanner.next();
        try {
            if (audit.equals("y")) {
                this.clientOperator.AuditOrNot("YES");
            } else {
                this.clientOperator.AuditOrNot("NO");
            }
        } catch(ClientOperationException e) {
            this.text(e.getMessage());
            this.auditQueryUI();
        }
    }

    protected void text(String str) {
        if (this.display) {
            System.out.println(str);
        }
    }
    protected void dMap() {
        if (this.display) {
            int id = this.client.getPlayerID();
            Player playerX = this.client.getPlayerMap().get(id);
            String playerStr = "id: " + id + "|food:" + playerX.getFood()+"|tech:" + playerX.getTech();
            this.text(playerStr);
            for (int playerID : this.client.getPlayerMap().keySet()) {
                Player player = this.client.getPlayerMap().get(playerID);
                this.text(playerID + "'s terrs:");
                HashMap<Integer, Territory> terrMap =  player.getTerrMap();
                for (Territory terr : terrMap.values()) {
                    String terrStr1 = terr.getTerrID() + ":";
                    HashMap<Integer, ArrayList<Unit>> unitMap = terr.getOwnerArmy().getUnitMap();
                    for (int level : unitMap.keySet()) {
                        terrStr1 += level + "." + unitMap.get(level).size() + "|";
                    }
                    this.text(terrStr1);
                    String terrStr2 = "nei:";
                    HashMap<Integer, Territory> neiMap = terr.getNeighborMap();
                    for (int neiID : neiMap.keySet()) {
                        terrStr2 += neiID + ",";
                    }
                    this.text(terrStr2);
                }
            }
        }
    }
}
