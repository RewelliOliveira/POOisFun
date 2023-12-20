package MoodleShapes;

import java.text.DecimalFormat;

class Circle extends Shape {
    public Point2D center;
    public double radius;

    public Circle(Point2D center, double radius) {
        super("Circ");
        this.center = center;
        this.radius = radius;
    }

    public boolean inside(Point2D point) {
        if (calc(point, center) < radius) {
            return true;
        }
        return false;
    }

    public double getArea() {
        double area = Math.PI * Math.pow(radius, 2);
        return area;
    }

    public double getPerimeter() {
        double perimeter = 2 * Math.PI * radius;
        return perimeter;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");

        return "Circ: C=(" + df.format(center.x) + ", " + df.format(center.y) + "), R=" + df.format(radius);
    }
}
