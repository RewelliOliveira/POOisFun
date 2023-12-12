import java.util.*;
class Time{
    private int hora, minuto, segundo;

    public Time(int hora, int minuto, int segundo){
        this.hora = hora;
        this.minuto = minuto;
        this.segundo = segundo;
    }

    //getters
    public int getHora(){
        return hora;
    }
    public int getMinuto(){
        return minuto;
    }
    public int getSegundo(){
        return segundo;
    }

    //setters
    public void setHora(int hora){
        if(hora < 24) setHora(hora);
        else System.out.println("Error: hora invalida");
    }
    public void setMinuto(int minuto){
        if(minuto < 24) setMinuto(minuto);
        else System.out.println("Error: minuto invalido");
    }
    public void setSegundo(int segundo){
        if(segundo < 24) setSegundo(segundo);
        else System.out.println("Error: segundo invalidao");
    }

    public String toString() {
        return String.format("%d:%d:%d", this.getHora(), this.getMinuto(), this.getSegundo());
    }


    public void nextSecond(){
    }
}

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