package RISK;

import RISK.ClassBuilder.ClassBuilder;
import RISK.ClassBuilder.ClassBuilderEvo2;
import RISK.Game.GameClient;
import RISK.Game.GameClientJSON;
import RISK.Order.OrderFactory;
import RISK.Order.OrderFactoryEvo2;
import RISK.Utils.MsgException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class baseGameClientMaker {
    GameClient client;
    OrderFactory orderFactory;

    @Test
    public void makeClient() {
        String terrPath = "./src/main/resources/territoriesJSON_2.txt";
        String playerPath = "./src/main/resources/playersJSON_2.txt";
        String armyPath = "./src/main/resources/armiesJSON_2.txt";

        ClassBuilder classBuilder = new ClassBuilderEvo2();
        try {
            this.client = new GameClientJSON(1, terrPath, playerPath, armyPath, classBuilder);
        } catch (MsgException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("IO Exception while building client!");
        }
        this.orderFactory = new OrderFactoryEvo2();
    }
}
