package RISK.Tests;

import RISK.ClassBuilder.ClassBuilder;
import RISK.ClassBuilder.ClassBuilderEvo2;
import RISK.ClientOperator.ClientOperationException;
import RISK.ClientOperator.ClientOperator;
import RISK.ClientOperator.ClientOperatorEvo2;
import RISK.Game.GameClient;
import RISK.Game.GameClientJSON;
import RISK.Order.Order;
import RISK.Order.OrderFactory;
import RISK.Order.OrderFactoryEvo2;
import RISK.TextUI.TextUI;

import java.util.Scanner;

public class ClientTest {
    public static void main(String args[]) {
        // make client
        ClassBuilder classBuilder = new ClassBuilderEvo2();
        GameClient client = new GameClientJSON(classBuilder);
        // make client operator
        OrderFactory orderFactory = new OrderFactoryEvo2();
        ClientOperator clientOperator = new ClientOperatorEvo2(client, orderFactory);
        // make text GUI
        Scanner scanner = new Scanner(System.in);
        TextUI gameUI = new TextUI(clientOperator, scanner,true);
        try {
            gameUI.run();
        } catch(ClientOperationException e) {
            System.out.println(e.getMessage());
            System.out.println("exiting");
        }
    }
}
