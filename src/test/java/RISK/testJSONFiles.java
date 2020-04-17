package RISK;

import RISK.ClassBuilder.ClassBuilder;
import RISK.ClassBuilder.ClassBuilderEvo2;
import RISK.Game.Game;
import RISK.Utils.MsgException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class testJSONFiles {
    @Test
    void testSpecificSet() {
        String terrPath = "./src/main/resources/terr_test_2_spy.txt";
        String playerPath = "./src/main/resources/player_test_2_spy.txt";
        String armyPath = "./src/main/resources/army_test_2_spy.txt";

        ClassBuilder classBuilder = new ClassBuilderEvo2();
        Game game = null;
        try {
            game = new Game(terrPath, playerPath, armyPath, classBuilder);
        } catch (MsgException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("IO Exception while building game!");
        }
        assert(game != null);
    }

    @Test
    void testAllFormalSets() {
        String terrPathPrefix = "./src/main/resources/territoriesJSON_";
        String playerPrefix = "./src/main/resources/playersJSON_";
        String armyPrefix = "./src/main/resources/armiesJSON_";
        for (int i = 2; i <= 5; i++) {
            String terrPath = terrPathPrefix + Integer.toString(i) + ".txt";
            String playerPath = playerPrefix + Integer.toString(i) + ".txt";
            String armyPath = armyPrefix + Integer.toString(i) + ".txt";

            ClassBuilder classBuilder = new ClassBuilderEvo2();
            Game game = null;
            try {
                game = new Game(terrPath, playerPath, armyPath, classBuilder);
            } catch (MsgException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println("IO Exception while building game!");
            }
            assert(game != null);
        }
    }
}
