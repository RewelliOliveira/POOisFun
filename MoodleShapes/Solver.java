package MoodleShapes;

import java.util.*;
import java.text.DecimalFormat;

class Point2D {
    public double x;
    public double y;

    public Point2D( double x, double y ) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        return "(" + df.format(x) + ", " + df.format(y) + ")";
    }
}

abstract class Shape{
    public String name;

    public Shape(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract boolean inside(Point2D point);

    public abstract double getArea();

    public abstract double getPerimeter();

    public static double calc(Point2D ponto1, Point2D ponto2){
        return Math.pow((ponto2.x - ponto1.x), 2) + Math.pow((ponto2.y - ponto1.y), 2);
    }

    public String getInfo() {
        DecimalFormat d = new DecimalFormat("0.00");
        return this.name + ": A=" + d.format(getArea()) + " P=" + d.format(getPerimeter());
    }

    @Override
    public String toString(){
        return name;
    }
}

class Circle extends Shape {
    public Point2D center;
    public double radius;

    public Circle( Point2D center, double radius ) {
        super("Circ");
        this.center = center;
        this.radius = radius;
    }

    public boolean inside(Point2D point){
        if (calc(point, center) < radius) {
            return true;
        }
        return false;
    }

    public double getArea() {
        double area = Math.PI*Math.pow(radius, 2);
        return area;
    }
    public double getPerimeter() {
        double perimeter = 2*Math.PI*radius;
        return perimeter;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");

        return "Circ: C=(" + df.format(center.x) + ", " + df.format(center.y) + "), R=" + df.format(radius);
    }
}

class Rectangle extends Shape{
    public Point2D ponto1;
    public Point2D ponto2;

    public Rectangle( Point2D ponto1, Point2D ponto2) {
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
        double area = Math.abs(ponto2.x - ponto1.x)*Math.abs(ponto2.y - ponto1.y);
        return area;
    }
    public double getPerimeter() {
        double perimeter = 2*(Math.abs(ponto2.x - ponto1.x) + Math.abs(ponto2.y - ponto1.y));
        return perimeter;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");

        return "Rect: P1=(" + df.format(ponto1.x) + ", " + df.format(ponto1.y) + ") P2=(" + df.format(ponto2.x) + ", " + df.format(ponto2.y) + ")";
    }
}


public class Solver {
    public static void main(String[] arg) {

        ArrayList<Shape> shapes = new ArrayList<>();

        label:
        while (true) {
            String line = input();
            println("$" + line);
            String[] args = line.split(" ");

            switch (args[0]) {
                case "end":
                    break label;
                case "circle":
                    shapes.add(new Circle(new Point2D(number(args[1]), number(args[2])), number(args[3])));
                    break;
                case "rect":
                    shapes.add(new Rectangle(new Point2D(number(args[1]), number(args[2])), new Point2D(number(args[3]), number(args[4]))));
                    break;
                case "show":
                    showAll(shapes);
                    break;
                case "info":
                    infoAll(shapes);
                    break;
                default:
                    println("fail: comando invalido");
                    break;
            }
        }
    }

    private static Scanner scanner = new Scanner(System.in);
    private static String  input()                { return scanner.nextLine();        }
    private static double  number(String value)   { return Double.parseDouble(value); }
    public  static void    println(Object value)  { System.out.println(value);        }
    public  static void    print(Object value)    { System.out.print(value);          }
    public  static void showAll( ArrayList<Shape> shapes ) { for ( Shape s : shapes ) println( s ); }
    public  static void infoAll( ArrayList<Shape> shapes ) { for ( Shape s : shapes ) println( s.getInfo() ); }
}
