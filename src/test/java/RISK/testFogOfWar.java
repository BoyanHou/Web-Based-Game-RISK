package RISK;

import RISK.Army.Army;
import RISK.ClassBuilder.ClassBuilder;
import RISK.ClassBuilder.ClassBuilderEvo2;
import RISK.Game.Game;
import RISK.Order.*;
import RISK.Player.Player;
import RISK.Territory.Territory;
import RISK.Unit.Spy;
import RISK.Unit.Unit;
import RISK.Unit.UnitLevelException;
import RISK.Unit.UnitLevelMapper;
import RISK.Utils.MsgException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class testFogOfWar {
    Game game;
    OrderFactory orderFactory;
    protected void makeGame() {
        String terrPath = "./src/main/resources/terr_test_2_spy.txt";
        String playerPath = "./src/main/resources/player_test_2_spy.txt";
        String armyPath = "./src/main/resources/army_test_2_spy.txt";

        ClassBuilder classBuilder = new ClassBuilderEvo2();
        try {
            this.game = new Game(terrPath, playerPath, armyPath, classBuilder);
        } catch (MsgException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("IO Exception while building game!");
        }

        this.orderFactory = new OrderFactoryEvo2();
    }

    @Test
    void test_convertSpyOrder() throws Exception {
        // make game
        this.makeGame();
        assert(this.game != null);

        // some set-ups
        int techCost = 20;  // the techCost used in this game
        String orderType = "convertSpy";
        int playerID = 1;
        String onTerrName = "Terr1xx";
        Player player = this.game.getPlayerMap().get(playerID);

        Territory onTerr = null;
        for (Territory territory : this.game.getTerrMap().values()) {
            if (territory.getName().equals(onTerrName)) {
                onTerr = territory;
            }
        }
        assert(onTerr != null);  // has such terr with given name
        ArrayList<Spy> spyList = onTerr.getSpyList(playerID);
        //assert(spyList != null); // spy list is initialized

        // make order by parameter map
        HashMap<String, String> parameterMap = new HashMap<>();
        parameterMap.put("onTerrName", onTerrName);
        Order orderMadeByMap;
        orderMadeByMap = this.orderFactory.makeOrderByStrings(this.game, playerID, parameterMap,orderType);
        assert(orderMadeByMap != null);  // the way of making ConvertSpyOrder from param strings is correct

        // convert order into JSONObject format
        JSONObject orderN = (JSONObject)orderMadeByMap.pton();

        // make order by JSONObject string
        String orderNStr = orderN.toString();
        Order orderMadeByN = this.orderFactory.makeOrderByNtop(this.game, orderNStr);
        assert(orderMadeByMap!=null);  // the way of making ConvertSpyOrder from JSON is correct

        // verify
        orderMadeByN.verify();

        // record lowest level unit num
        int originalLowestLevelUnitCount = this.getLowestLevelUnitsCount(onTerr.getOwnerArmy());
        // record original spy counts
        int originalSpyCount = spyList.size();
        // record player's original tech points
        int originalTech = player.getTech();

        // execute
        orderMadeByN.execute();
        // assert one spy is made from a lowest level unit, on the target terr, with cost of tech
        int newLowestLevelUnitCount = this.getLowestLevelUnitsCount(onTerr.getOwnerArmy());
        int newSpyCount = spyList.size();
        int newTech = player.getTech();

        assert((newSpyCount - originalSpyCount) == 1);
        assert((originalLowestLevelUnitCount - newLowestLevelUnitCount) == 1);
        assert((originalTech - newTech) == 20);
    }

//    @Test
//    void test_verifyMakeSpyOrder() throws Exception {
//
//    }

    protected int getLowestLevelUnitsCount(Army army) {
        HashMap<Integer, ArrayList<Unit>> unitMap = army.getUnitMap();
        int ans = 0;
        for (int i = 0; ; i++) {
            try {
                UnitLevelMapper.mapBonus(i);
            } catch (UnitLevelException e) {
                return 0;
            }
            if (unitMap.containsKey(i) && unitMap.get(i).size() != 0) {
                return unitMap.get(i).size();
            }
        }
    }

}
