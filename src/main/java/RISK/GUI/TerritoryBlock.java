package RISK.GUI;

import RISK.Player.Player;
import RISK.Territory.Territory;
//import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;



public class TerritoryBlock {


    private Color color;
    private Territory territory;
    private ArrayList<Block> blocks;
    private ArrayList<Line> borders;
    private Point fogPos;
    private Point spyImgPos;
    private Point spyNumPos;
    private int width = 650;
    private int height = 500;

    public TerritoryBlock() {
    }

    public void setTerritory(Territory territory) {
        this.territory = territory;
        update();
    }

    public void setFogPos(Point p) {
        fogPos = p;
    }

    //TODO change
    public void setSpyPos(Point p) {
        spyImgPos = p;
        spyNumPos = new Point(p.getX(), p.getY() - 20);
    }

    public void setBlocks(ArrayList<Block> blocks) {
        this.blocks = blocks;

        //calculate borders
        borders = new ArrayList<>();
        for (Block block : blocks) {
            ArrayList<Point> neightbors = block.getNeighbors();
            for (Point p : neightbors) {
                if (!contains(p)) {
                    Line line = block.getSharedBorder(p);
                    if (line != null) {
                        borders.add(line);
                    }
                }
            }
        }
    }

    public Color getColor() {
        return this.color;
    }

    public String getTerrName() {
        return territory.getName();
    }

    public boolean contains(Point p) {
        for (Block block : blocks) {
            if (block.equals(p)) {
                return true;
            }
        }
        return false;
    }

    public void setColor(Color color) {
        if (color == Color.yellow) {
            //TODO bian qian
            this.color = this.color.darker();
            return ;

        }
        this.color = color;
        return ;
    }

    /*
    @param: p: a point
    @return: boolean
    Return true if the point is in the territoryBlock.
    PLEASE MARK: Boundary sensitive or not.
     */
    public boolean check(Point p) {

        int xPoint = p.getX();
        int yPoint = p.getY();
        int weight = 10;
        int height = 10;
        for (Block blk : blocks) {
            int xBlk = blk.getX();
            int yBlk = blk.getY();

            if (xPoint >= xBlk && xPoint <= xBlk + weight && yPoint >= yBlk && yPoint <= yBlk + height) {
                return true;
            }

        }
        return false;
    }

    /*
    Update the color of the TerritoryBlock to be the same with the territory
     */


    public void update() {
        int index = territory.getOwner().getPlayerID();
        switch (index) {
            case 1:
                color = Color.green;
                break;
            case 2:
                color = Color.red;
                break;
            case 3:
                color = Color.orange;
                break;
            case 4:
                color = Color.pink;
                break;
            case 5:
                color = Color.blue;
                break;
            default:
                color = Color.gray;
        }
    }


    public void paint(Graphics g) {
        for (Block block : blocks) {
            g.setColor(color);
            Point p = block.getPosition();
            g.fillRect(p.getX(), p.getY(),
                    block.getWidth(), block.getHeight());
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));

        for (Line line : borders) {
            Point start = line.getStart();
            Point end = line.getEnd();
            g2.draw(new Line2D.Float(start.getX(), start.getY(), end.getX(), end.getY()));

        }
    }


}
