package RISK;

import RISK.ClassBuilder.ClassBuilder;
import RISK.ClassBuilder.ClassBuilderEvo2;
import RISK.Game.Game;
import RISK.Order.*;
import RISK.Utils.MsgException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

public class testFogOfWar {
    Game game;
    OrderFactory orderFactory;
    public testFogOfWar() {
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

        //this.orderFactory = new OrderFactoryEvo3();
    }

    @Test
    void test_makeSpyOrder() throws Exception {
        String orderType = "makeSpy";
        int playerID = 1;

        // make order by parameter map
        HashMap<String, String> parameterMap = new HashMap<>();
        parameterMap.put("onTerrName", "terr1");
        Order orderMadeByMap;
        orderMadeByMap = this.orderFactory.makeOrderByStrings(this.game, playerID, parameterMap,orderType);

        // convert order into JSONObject format
        JSONObject orderN = (JSONObject)orderMadeByMap.pton();

        // make order by JSONObject string
        String orderNStr = orderN.toString();
        Order orderMadeByN = this.orderFactory.makeOrderByNtop(this.game, orderNStr);
    }

    @Test
    void test_verifyMakeSpyOrder() throws Exception {

    }

}
