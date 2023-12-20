package MoodleShapes;

import java.text.DecimalFormat;

abstract class Shape {
    public String name;

    public Shape(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract boolean inside(Point2D point);

    public abstract double getArea();

    public abstract double getPerimeter();

    public static double calc(Point2D ponto1, Point2D ponto2) {
        return Math.pow((ponto2.x - ponto1.x), 2) + Math.pow((ponto2.y - ponto1.y), 2);
    }

    public String getInfo() {
        DecimalFormat d = new DecimalFormat("0.00");
        return this.name + ": A=" + d.format(getArea()) + " P=" + d.format(getPerimeter());
    }

    @Override
    public String toString() {
        return name;
    }
}
