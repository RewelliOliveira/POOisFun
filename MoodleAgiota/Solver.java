package MoodleAgiota;
import java.util.*;

public class Solver {
    public static void main(String[] arg) {
        println("side_by_side=080");

        Agiota agiota = new Agiota();

        label:
        while (true) {
            String line = input();
            println("$" + line);
            String[] args = line.split(" ");

            try {
                switch (args[0]) {
                    case "end":
                        break label;
                    case "init":
                        agiota = new Agiota();
                        break;
                    case "show":
                        print(agiota);
                        break;
                    case "showCli":
                        print(agiota.getClient(args[1]));
                        break;
                    case "addCli":
                        agiota.addClient(args[1], (int) number(args[2]));
                        break;
                    case "give":
                        agiota.give(args[1], (int) number(args[2]));
                        break;
                    case "take":
                        agiota.take(args[1], (int) number(args[2]));
                        break;
                    case "kill":
                        agiota.kill(args[1]);
                        break;
                    case "plus":
                        agiota.plus();
                        break;
                    default:
                        println("fail: comando invalido");
                        break;
                }
            } catch (Exception e) {
                println(e.getMessage());
            }
        }
    }

    private static Scanner scanner = new Scanner(System.in);
    private static String  input()                { return scanner.nextLine();        }
    private static double  number(String value)   { return Double.parseDouble(value); }
    public  static void    println(Object value)  { System.out.println(value);        }
    public  static void    print(Object value)    { System.out.print(value);          }
}