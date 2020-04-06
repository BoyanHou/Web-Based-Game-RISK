package RISK.GUI;

import RISK.Player.Player;
import RISK.Territory.Territory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TerritoryBlock {

    private Color color;
    private Territory territory;
    private ArrayList<Block> blocks;
    private ArrayList<Line> borders;

    public TerritoryBlock(Territory territory) {
        this.territory = territory;
    }

    public void  setBlocks(ArrayList<Block> blocks) {
        this.blocks = blocks;
        //calculate borders
    }

    /*
    @param: p: a point
    @return: boolean
    Return true if the point is in the territoryBlock.
    PLEASE MARK: Boundary sensitive or not.
     */
    public boolean check(Point p) {
        //TODO
        return false;
    }

    /*
    Update the color of the TerritoryBlock to be the same with the territory
     */
    private void update() {
        String colorStr = territory.getOwner().getName();
        switch (colorStr) {
            case "Green":
                color = Color.green;
                break;
            case "Red":
                color = Color.red;
                break;
            default:
                color = Color.gray;
        }
    }


    public void paint(Graphics g) {
        update();
        for (Block block: blocks) {
            g.setColor(color);
            g.fillRect(block.getX(), block.getY(),
                    block.getWeight(), block.getHeight());
        }
    }



}
