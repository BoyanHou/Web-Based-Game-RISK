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
import org.json.JSONObject;

import java.util.Scanner;

public class RiskGameClientText {
  Scanner scanner;
  public RiskGameClientText(Scanner scanner) {
    this.scanner = scanner;
  }
  public void run( boolean display) {
    
      // make client
        ClassBuilderEvo2 classBuilder = new ClassBuilderEvo2();
        GameClientJSON client = new GameClientJSON(classBuilder);
        // make client operator
        OrderFactoryEvo2 orderFactory = new OrderFactoryEvo2();
        ClientOperator<JSONObject> clientOperator = new ClientOperatorEvo2<JSONObject>(client, orderFactory);
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
