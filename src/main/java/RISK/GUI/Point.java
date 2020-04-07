package RISK.GUI;

public class Point {
    private int x;
    private int y;

    public Point(int xx, int yy) {
        x = xx;
        y = yy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean equals(Point p) {
        return p.getX() == x && p.getY() == y;
    }
}
