package RISK.GUI;

import RISK.Territory.Territory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class MapPanel extends JPanel {
    ArrayList<TerritoryBlock> territoryBlocks;

    public MapPanel(ArrayList<TerritoryBlock> territoryBlocks) {
        this.territoryBlocks = territoryBlocks;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (TerritoryBlock territoryBlock: territoryBlocks) {
            territoryBlock.paint(g);
        }
    }
}
