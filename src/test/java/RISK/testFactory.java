package RISK;

import RISK.Army.Army;
import RISK.Army.ArmyRO;
import RISK.Factory.NtopFactoryJSON;
import RISK.Factory.PtonFactoryJSON;
import RISK.Game.GameInitial;
import RISK.Order.AttackOrder;
import RISK.Order.MoveOrder;
import RISK.Player.Player;
import RISK.Player.PlayerRO;
import RISK.Territory.Territory;
import RISK.Territory.TerritoryRO;
import RISK.Utils.Status;
import RISK.Utils.TerritoryNames;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

public class testFactory {
    private GameInitial game = new GameInitial();
    private PtonFactoryJSON pf = new PtonFactoryJSON();
    private NtopFactoryJSON nf = new NtopFactoryJSON();

    private ArrayList<Territory> territories = game.getTerritories();
    private ArrayList<Player> players = game.getPlayers();
    private ArrayList<Army> armies = game.getArmies();

    private HashMap<Integer, Territory> territoryHashMap = new HashMap<>();
    private HashMap<Integer, Player> playerHashMap = new HashMap<>();

    @Test
    void testTerritoryPton() {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("{\"Territory\":{\"owner\":1,\"attackArmyList\":[],\"ownerArmy\":1,\"name\":\"Narnia\",\"neighborList\":[2,7],\"terrID\":1}}");
        expected.add("{\"Territory\":{\"owner\":1,\"attackArmyList\":[],\"ownerArmy\":2,\"name\":\"Midkemia\",\"neighborList\":[1,7,8,3],\"terrID\":2}}");
        expected.add("{\"Territory\":{\"owner\":1,\"attackArmyList\":[],\"ownerArmy\":3,\"name\":\"Oz\",\"neighborList\":[2,8,5,4],\"terrID\":3}}");
        expected.add("{\"Territory\":{\"owner\":2,\"attackArmyList\":[],\"ownerArmy\":4,\"name\":\"Gondor\",\"neighborList\":[3,5],\"terrID\":4}}");
        expected.add("{\"Territory\":{\"owner\":2,\"attackArmyList\":[],\"ownerArmy\":5,\"name\":\"Mordor\",\"neighborList\":[4,3,8,6],\"terrID\":5}}");
        expected.add("{\"Territory\":{\"owner\":2,\"attackArmyList\":[],\"ownerArmy\":6,\"name\":\"Hogwarts\",\"neighborList\":[5,8,9],\"terrID\":6}}");
        expected.add("{\"Territory\":{\"owner\":3,\"attackArmyList\":[],\"ownerArmy\":7,\"name\":\"Elantris\",\"neighborList\":[1,2,8,9],\"terrID\":7}}");
        expected.add("{\"Territory\":{\"owner\":3,\"attackArmyList\":[],\"ownerArmy\":8,\"name\":\"Scadrial\",\"neighborList\":[7,2,3,5,6,9],\"terrID\":8}}");
        expected.add("{\"Territory\":{\"owner\":3,\"attackArmyList\":[],\"ownerArmy\":9,\"name\":\"Roshar\",\"neighborList\":[7,8,6],\"terrID\":9}}");
        int index = 0;
        for(Territory territory: territories) {
            JSONObject actual = pf.terrPton(territory);
            assertEquals(expected.get(index), actual.toString());
            index++;
        }
    }

    @Test
    void testPlayerPton() {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("{\"Player\":{\"name\":\"Blue\",\"playerID\":1,\"status\":\"SETUP\",\"terrList\":[1,2,3]}}");
        expected.add("{\"Player\":{\"name\":\"Red\",\"playerID\":2,\"status\":\"SETUP\",\"terrList\":[4,5,6]}}");
        expected.add("{\"Player\":{\"name\":\"Purple\",\"playerID\":3,\"status\":\"SETUP\",\"terrList\":[7,8,9]}}");
        int index = 0;
        for (Player player: players) {
            JSONObject actual = pf.playerPton(player);
            assertEquals(expected.get(index), actual.toString());
            index++;
        }
    }

    @Test
    void testArmyPton() {
        ArrayList<String> expects = new ArrayList<>();
        expects.add("{\"Army\":{\"owner\":1,\"armyID\":1,\"unitList\":10}}");
        expects.add("{\"Army\":{\"owner\":1,\"armyID\":2,\"unitList\":12}}");
        expects.add("{\"Army\":{\"owner\":1,\"armyID\":3,\"unitList\":8}}");
        expects.add("{\"Army\":{\"owner\":2,\"armyID\":4,\"unitList\":13}}");
        expects.add("{\"Army\":{\"owner\":2,\"armyID\":5,\"unitList\":14}}");
        expects.add("{\"Army\":{\"owner\":2,\"armyID\":6,\"unitList\":3}}");
        expects.add("{\"Army\":{\"owner\":3,\"armyID\":7,\"unitList\":6}}");
        expects.add("{\"Army\":{\"owner\":3,\"armyID\":8,\"unitList\":5}}");
        expects.add("{\"Army\":{\"owner\":3,\"armyID\":9,\"unitList\":3}}");
        int index = 0;
        for (Army army: armies) {
            JSONObject actual = pf.armyPton(army);
            assertEquals(expects.get(index), actual.toString());
            index++;
        }
    }

    @Test
    void testMoveOrderPton() {
        MoveOrder mv = new MoveOrder(territories.get(0), territories.get(1), 2, players.get(0));
        String expected = "{\"MoveOrder\":{\"fromTerr\":1,\"num\":2,\"toTerr\":2,\"player\":1}}";
        JSONObject result = pf.movePton(mv);
        assertEquals(expected, result.toString());
    }

    @Test
    void testAttackOrderPton() {
        AttackOrder atk = new AttackOrder(territories.get(0), territories.get(1), 2, players.get(0));
        String expected = "{\"AttackOrder\":{\"targetTerr\":2,\"num\":2,\"myTerr\":1,\"player\":1}}";
        JSONObject actual = pf.attackPton(atk);
        assertEquals(expected, actual.toString());
    }

    @Test
    void testMoveOrderNtop() {
        JSONObject input = new JSONObject("{\"MoveOrder\":{\"fromTerr\":1,\"num\":2,\"toTerr\":2,\"player\":1}}");
        prepare();
        MoveOrder actual = nf.moveNtop(input, territoryHashMap, playerHashMap);
        JSONObject actualJson = pf.movePton(actual);
        MoveOrder expected = new MoveOrder(territories.get(0), territories.get(1), 2, players.get(0));
        JSONObject expectedJson = pf.movePton(expected);
        assertEquals(expectedJson.toString(), actualJson.toString());
    }

    void prepare() {
        for(Territory territory: territories) {
            territoryHashMap.put(territory.getTerrID(), territory);
        }
        for(Player player: players) {
            playerHashMap.put(player.getPlayerID(), player);
        }
    }

    @Test
    void testAttackOrderNtop() {
        JSONObject input = new JSONObject("{\"AttackOrder\":{\"targetTerr\":2,\"num\":2,\"myTerr\":1,\"player\":1}}");
        prepare();
        AttackOrder actual = nf.attackNtop(input, territoryHashMap, playerHashMap);
        JSONObject actualJson = pf.attackPton(actual);
        AttackOrder expected = new AttackOrder(territories.get(0), territories.get(1), 2, players.get(0));
        JSONObject expectedJson = pf.attackPton(expected);
        assertEquals(expectedJson.toString(), actualJson.toString());
    }

    @Test
    void testTerrNtop() {
        JSONObject input = new JSONObject("{\"Territory\":{\"owner\":1,\"ownerArmy\":1,\"name\":\"Narnia\",\"neighborList\":[2,7],\"terrID\":1}}");
        TerritoryRO actual = nf.terrNtop(input);
        assertEquals(actual.getTerrID(), 1);
        assertEquals(actual.getName(), TerritoryNames.Narnia);
    }

    @Test
    void testPlayerNtop() {
        JSONObject input = new JSONObject("{\"Player\":{\"name\":\"Blue\",\"playerID\":1,\"status\":\"PLAY\",\"terrList\":[1,2,3]}}");
        PlayerRO actual = nf.playerNtop(input);
        assertEquals(actual.getPlayerID(), 1);
        assertEquals(actual.getName(), "Blue");
        assertEquals(actual.getStatus(), Status.PLAY);
    }

    @Test
    void testArmyNtop() {
        JSONObject input = new JSONObject("{\"Army\":{\"owner\":1,\"armyID\":1,\"unitList\":10}}");
        ArmyRO actual = nf.armyNtop(input);
        assertEquals(actual.getArmyID(), 1);
        assertEquals(actual.getUnitListSize(), 10);
    }

}
