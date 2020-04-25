package RISK.GUI;

import java.util.ArrayList;
import javax.sql.rowset.spi.*;

public class Block {
    int width = 10;
    int height = 10;
    int x;
    int y;
    Point position;

    public Block(int x, int y) {
        this.x = x;
        this.y = y;
        position = new Point(x, y);
    }

    public Block(Point p) {
        position = p;
    }

    public Point getPosition() {
        return position;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean equals(Point p) {
        return position.equals(p);
    }


    /*
    @param: maxWidth: the maximum valid width
            maxHeight: the maximum valid height
    @return: ArrayList<Point>: all positions of valid neighbors
    Return the valid positions of neighbors. Only consider up, down, left and right.
    EXAMPLE:
        Assuming the location is the position of the upperLeft corner and (0, 0) is also at the upperLeft corner.
        For a box located at (5, 5) with width=5, height=5.
        The neighbors are [(0, 5), (5, 0),  (10, 5), (5, 10)].

     */
    public ArrayList<Point> getNeighbors() {
        Point up = new Point(x, y - height);
        Point down = new Point(x, y + height);
        Point left = new Point(x - width, y);
        Point right = new Point(x + width, y);
        ArrayList<Point> neiPoint = new ArrayList<Point>();
        // hard code to test the over boundary
        if (y - height >= 0) {
            neiPoint.add(up);
        }
        if (y + height <= 550) {
            neiPoint.add(down);
        }
        if (x - width >= 0) {
            neiPoint.add(left);
        }
        if (x + width <= 650) {
            neiPoint.add(right);
        }
        return neiPoint;
    }


    /*
    @param: p: the position of a Block
    @return Line: the Line shared by them. If no shared border or shared more than one(exactly the same), return null.
    Return the shared border.
    EXAMPLE:
        Assuming width=5, height=5.
        The block locates at (0,0) and p=(5, 0).
        Return a line starts from (5,0) and ends at (5, 5). Or (5, 5)->(0, 0).
     */
    public Line getSharedBorder(Point p) {
        int pX = p.getX();
        int pY = p.getY();
        ArrayList<Point> neighbors = getNeighbors();
        boolean ifNeiBlockPoint = false;
        for (Point neiP : neighbors) {
            int neiPX = p.getX();
            int neiPY = p.getY();
            if (pX == neiPX && pY == neiPY) {
                ifNeiBlockPoint = true;
            }
        }
        if (ifNeiBlockPoint == false) return null;
        // up
        if (pX == x && pY == y - height) {
            Line sharedBoard = new Line(new Point(x, y), new Point(x + width, y));
            return sharedBoard;
        }
        // down
        else if (pX == x && pY == y + height) {
            Line sharedBoard = new Line(new Point(x, y + height), new Point(x + width, y + height));
            return sharedBoard;
        }
        // left
        else if (pX == x - width && pY == y) {
            Line sharedBoard = new Line(new Point(x, y), new Point(x, y + height));
            return sharedBoard;
        }
        // right
        else if (pX == x + width && pY == y) {
            Line sharedBoard = new Line(new Point(x + width, y), new Point(x + width, y + height));
            return sharedBoard;
        }
        //return sharedBoard;
        System.out.println("Error: getSharedBorder error");
        return null;
    }
}
