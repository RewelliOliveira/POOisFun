import java.util.*;

public class Solver{
    public static void main(String[] args){
        Time time = new Time(0,0,0);
        String line = sc.nextLine();
        System.out.println("$" + line);
        String[] ui = line.split(" ");

        while(true){
            if(ui[0].equals("show")) time.toString();
            break;
        }

    }
    static Scanner sc = new Scanner(System.in);
}