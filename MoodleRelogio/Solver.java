package MoodleRelogio;
// umas 4 hrs, ajuda do miqueias, e do daniel christopher

import java.util.*;

class Time{
    private int hour;
    private int minute;
    private int second;

    public Time(int hour, int minute, int second){
        this.hour = 0;
        this.minute = 0;
        this.second = 0;
        setHour(hour);
        setMinute(minute);
        setSecond(second);
    }
    public String toString(){
        return String.format("%02d:%02d:%02d", getHour(), getMinute(), getSecond());
    }

    public int getHour() {
        return hour;
    }
    public void setHour(int hour) {
        if(hour < 24) this.hour = hour;
        else System.out.println("fail: hora invalida");
    }

    public int getMinute() {
        return minute;
    }
    public void setMinute(int minute) {
        if(minute < 60) this.minute = minute;
        else System.out.println("fail: minuto invalido");

    }

    public int getSecond() {
        return second;
    }
    public void setSecond(int second) {
        if(second < 60) this.second = second;
        else System.out.println("fail: segundo invalido");
    }

    public void nextSecond(){
        this.second += 1;
        if(getSecond() + 1 >= 60) {
            setSecond(0);
            if(getMinute() + 1 == 60){
                setMinute(0);
                if(getHour() + 1 == 24) {
                    setHour(0);
                    return;
                }
                setHour(getHour() + 1);
                return;
            }
            setMinute(getMinute() + 1);
        }
    }
}

public class Solver {
    public static void main(String[] arg) {
        Time time = new Time(0, 0, 0);

        while (true) {
            var line = input();
            write("$" + line);
            var args = line.split(" ");


            if (args[0].equals("show"))  {
                write("" + time);
            }
            else if (args[0].equals("init")) {
                time = new Time((int)number(args[1]), (int)number(args[2]), (int)number(args[3]));
            }
            else if (args[0].equals("set")) {
                time.setHour((int)number(args[1]));
                time.setMinute((int)number(args[2]));
                time.setSecond((int)number(args[3]));
            }
            else if (args[0].equals("end"))   {
                break;
            }
            else if (args[0].equals("next"))   {
                time.nextSecond();
            }
            else {
                write("fail: comando invalido");
            }
        }
    }

    private static Scanner scanner = new Scanner(System.in);
    private static String  input()              { return scanner.nextLine(); }
    private static double  number(String value) { return Double.parseDouble(value); }
    private static void    write(String value)  { System.out.println(value); }
}
