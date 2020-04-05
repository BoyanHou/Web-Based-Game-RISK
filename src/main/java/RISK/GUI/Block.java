package RISK.GUI;

public class Block {
    int weight = 50;
    int height = 50;
    int x;
    int y;
    public Block(int x, int y) {
        this.x = x;
        this.y = y;
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
}
