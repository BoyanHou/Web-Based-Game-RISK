package RISK;

import RISK.GUI.TerritoryBlock;
import RISK.GUI.Point;
import RISK.GUI.TerritoryBlockInitial;
import RISK.Territory.Territory;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class testGUI extends baseGameMaker{
    @Test
    public void testTerritoryBlockInitial () {
        TerritoryBlockInitial tbi = new TerritoryBlockInitial();
        tbi.getTerrNamePos();
        tbi.getTerritoryBlockMap();
    }

    @Test
    public void testTerritoryBlock() {
        this.makeGame();
        Territory terr = this.game.getTerrMap().get(1);
        TerritoryBlock tb = new TerritoryBlock();
        tb.setTerritory(terr);
        tb.getColor();
        tb.getTerrName();
        tb.setColor(Color.yellow);
        tb.setBlocks(new ArrayList<>());

        // make a new Point
        Point point = new Point(1,1);
        point.getX();
        point.getY();
        assert(point.equals(new Point(1, 1)));

        // continue TerritoryBlock
        tb.check(point);
        tb.update();

        JPanel jPanel = new JPanel();
        Graphics2D grp2D = (Graphics2D)jPanel.getGraphics();
    }

}
