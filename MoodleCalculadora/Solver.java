package MoodleCalculadora;
import java.util.*;

public class Solver {
    public static void main(String[] a) {
        Calculator calc = new Calculator(0);

        while (true) {
            var line = input();
            write("$" + line);
            var args = line.split(" ");

            if      (args[0].equals("end"))    { break;                                                      }
            else if (args[0].equals("init"))   { calc = new Calculator((int) number(args[1]));               }
            else if (args[0].equals("show"))   { System.out.println(calc);                                   }
            else if (args[0].equals("charge")) { calc.charge((int) number(args[1]));                  }
            else if (args[0].equals("sum"))    { calc.sum((int) number(args[1]),(int) number(args[2]));      }
            else if (args[0].equals("div"))    { calc.division((int) number(args[1]),(int) number(args[2])); }
            else                               { write("fail: comando invalido"); }
        }
    }

    private static Scanner scanner = new Scanner(System.in);
    private static String  input()              { return scanner.nextLine(); }
    private static double  number(String value) { return Double.parseDouble(value); }
    private static void    write(String value)  { System.out.println(value); }
}
