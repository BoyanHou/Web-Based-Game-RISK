package RISK.GUI;

import RISK.Territory.Territory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MapPanel extends JPanel {
    ArrayList<TerritoryBlock> territoryBlocks;

    public MapPanel(ArrayList<TerritoryBlock> territoryBlocks) {
        this.territoryBlocks = territoryBlocks;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        try{
            BufferedImage img = ImageIO.read(new File("./src/main/resources/frame4.png"));
            Image newImage =img.getScaledInstance(700, 500, Image.SCALE_DEFAULT);
            g.drawImage(newImage,0,0,null);   
        }

        catch(IOException ioe){
        }
        for (TerritoryBlock territoryBlock: territoryBlocks) {
            territoryBlock.paint(g);
        }

    }
}
