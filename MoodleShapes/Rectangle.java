package MoodleShapes;

import java.text.DecimalFormat;

class Rectangle extends Shape {
    public Point2D ponto1;
    public Point2D ponto2;

    public Rectangle(Point2D ponto1, Point2D ponto2) {
        super("Rect");
        this.ponto1 = ponto1;
        this.ponto2 = ponto2;
    }

    public boolean inside(Point2D ponto) {
        if (ponto1.x > ponto.x && ponto.x > ponto2.x) {
            if (ponto1.y > ponto.y && ponto.y > ponto2.y) {
                return true;
            }
        }
        return false;
    }

    public double getArea() {
        double area = Math.abs(ponto2.x - ponto1.x) * Math.abs(ponto2.y - ponto1.y);
        return area;
    }

    public double getPerimeter() {
        double perimeter = 2 * (Math.abs(ponto2.x - ponto1.x) + Math.abs(ponto2.y - ponto1.y));
        return perimeter;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");

        return "Rect: P1=(" + df.format(ponto1.x) + ", " + df.format(ponto1.y) + ") P2=(" + df.format(ponto2.x) + ", " + df.format(ponto2.y) + ")";
    }
}
