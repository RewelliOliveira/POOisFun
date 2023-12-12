package MoodleCalculadora;
import java.util.*;

class Calculator{
    int battery;
    int batteryMax;
    float display;

    public Calculator(int batteryMax){
        this.batteryMax = batteryMax;
        this.battery = 0;
        this.display = 0.0f;
    }
    public void charge(int value){
        if(value < 0){
            return;
        }
        this.battery += value;
        if(this.battery >= this.batteryMax){
            this.battery = batteryMax;
        }
    }
    public void sum(int x, int y){
        if(useBattery()) this.display = x+y;
    }

    public void division(float num, float den){
        if(!useBattery()) return;
        if(den == 0) System.out.println("fail: divisao por zero");
        else this.display = num / den;
    }
    public boolean useBattery(){
        if(this.battery <= 0 ){
            System.out.println("fail: bateria insuficiente");
        return false;
        }else{
            this.battery -= 1;
            return true;
        }
    }
    public String toString(){
        return String.format("display = %.2f, battery = %d", this.display, this.battery);
    }
}

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
