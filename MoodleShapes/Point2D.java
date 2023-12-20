package MoodleShapes;

import java.text.DecimalFormat;

class Point2D {
    public double x;
    public double y;

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        return "(" + df.format(x) + ", " + df.format(y) + ")";
    }
}
