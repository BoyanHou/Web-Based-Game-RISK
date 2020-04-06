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
import RISK.RiskGameClientText;
import RISK.TextUI.TextUI;

import java.util.Scanner;

public class ClientTest {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        RiskGameClientText.run(scanner, true); // run with text display
    }
}
