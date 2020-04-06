package RISK.GUI;

import java.util.ArrayList;

public class Block {
    int weight = 50;
    int height = 50;
    int x;
    int y;
    Point position;
    public Block(int x, int y) {
        this.x = x;
        this.y = y;
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

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
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
    public ArrayList<Point> getNeighbors(int maxWidth, int maxHeight) {
        //TODO
        return null;
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
        //TODO
        return null;
    }
}
