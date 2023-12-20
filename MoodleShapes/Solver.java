package MoodleShapes;

import java.util.*;
import java.text.DecimalFormat;


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
