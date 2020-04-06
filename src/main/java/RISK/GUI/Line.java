package RISK.GUI;

import java.util.List;

public class Line {
    private Point start;
    private Point end;

    public Line(Point s, Point e) {
        start = s;
        end = e;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }
}
