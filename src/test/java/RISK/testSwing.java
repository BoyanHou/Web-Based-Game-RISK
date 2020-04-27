package RISK;

import RISK.ClassBuilder.ClassBuilder;
import RISK.ClassBuilder.ClassBuilderEvo2;
import RISK.ClientOperator.ClientOperationException;
import RISK.ClientOperator.ClientOperator;
import RISK.ClientOperator.ClientOperatorEvo2;
import RISK.GUI.app;
import RISK.Game.GameClient;
import RISK.Game.GameClientJSON;
import RISK.Order.OrderFactory;
import RISK.Order.OrderFactoryEvo2;
import org.junit.jupiter.api.Test;

public class testSwing {

    @Test
    public void test() {
        ClassBuilder classBuilder = new ClassBuilderEvo2();
        GameClient client = new GameClientJSON(classBuilder);
        // make client operator
        OrderFactory orderFactory = new OrderFactoryEvo2();
        ClientOperator clientOperator = new ClientOperatorEvo2(client, orderFactory);
        // make text GUI
        try {
            clientOperator.initConnection("0.0.0.0", "9000");
        } catch (
                ClientOperationException ce) {

        }
        System.out.println("Finish Connection");
        new app(clientOperator);
        app.trigger("actionMove");
        app.trigger("moveCancel");

        app.trigger("actionAttack");
        app.trigger("attackCancel");

        app.trigger("actionUpgrade");
        app.trigger("upgradeCancel");

        app.trigger("actionFog");
        app.trigger("fogCancel");

        app.trigger("actionSpy");
        app.trigger("spyConvertSpy");
        app.trigger("convertSpyCancel");

        app.trigger("actionSpy");
        app.trigger("spyMoveSpy");
        app.trigger("moveSpyCancel");
        app.close();
    }
}
