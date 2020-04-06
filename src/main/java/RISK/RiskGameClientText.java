package RISK;

import RISK.ClassBuilder.ClassBuilder;
import RISK.ClassBuilder.ClassBuilderEvo2;
import RISK.ClientOperator.ClientOperationException;
import RISK.ClientOperator.ClientOperator;
import RISK.ClientOperator.ClientOperatorEvo2;
import RISK.Game.GameClient;
import RISK.Game.GameClientJSON;
import RISK.Order.OrderFactory;
import RISK.Order.OrderFactoryEvo2;
import RISK.TextUI.TextUI;

import java.util.Scanner;

public class RiskGameClientText {
    public static void run(Scanner scanner, boolean display) {
        // make client
        ClassBuilder classBuilder = new ClassBuilderEvo2();
        GameClient client = new GameClientJSON(classBuilder);
        // make client operator
        OrderFactory orderFactory = new OrderFactoryEvo2();
        ClientOperator clientOperator = new ClientOperatorEvo2(client, orderFactory);
        // make text GUI
        TextUI gameUI = new TextUI(clientOperator, scanner,display);
            try {
            gameUI.run();
        } catch(ClientOperationException e) {
            System.out.println(e.getMessage());
            System.out.println("exiting");
        }
    }
}
