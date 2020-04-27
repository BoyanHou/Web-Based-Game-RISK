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

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public double distanceTo(Point p) {
        return Math.abs(p.getX() - x) +  Math.abs(p.getY() - y);
    }
}
