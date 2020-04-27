package RISK;

import RISK.ClassBuilder.ClassBuilder;
import RISK.ClassBuilder.ClassBuilderEvo2;
import RISK.Game.Game;
import RISK.Order.OrderFactory;
import RISK.Order.OrderFactoryEvo2;
import RISK.Utils.MsgException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class baseGameMaker {
    Game game;
    OrderFactory orderFactory;
    @Test
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
}
