package MoodleTomagochi;
import java.util.*;

class Pet{
    private boolean alive;
    private int energy, clean, hungry;
    private int cleanMax, energyMax, hungryMax;
    private int age, diamonds;
    //Construct
    public Pet(int energyMax, int hungryMax, int cleanMax){
        this.energy = energyMax;
        this.hungry = hungryMax;
        this.clean = cleanMax;
        this.energyMax = energyMax;
        this.hungryMax = hungryMax;
        this.cleanMax = cleanMax;

        this.diamonds = 0;
        this.age = 0;
        this.alive = true;
    }
    //methods
    private boolean testeAlive(){
        if(!this.alive) {
            System.out.println("fail: pet esta morto");
        }
            return this.alive;
    }

    public void eat(){
        if(!testeAlive()) {
            return;
        }
            setEnergy(this.energy - 1);
            setHungry(this.hungry + 4);
            setClean(this.clean - 2);
            this.age += 1;
    }
    public void play(){
        if(!testeAlive()) {
            return;
        }
            setEnergy(this.energy - 2);
            setHungry(this.hungry - 1);
            setClean(this.clean - 3);
            this.age += 1;
            this.diamonds += 1;
    }
    public void shower(){
        if(!testeAlive()) {
            return;
        }
            this.setEnergy(this.energy - 3);
            this.setHungry(this.hungry - 1);
            this.setClean(this.cleanMax);
            this.age  += 2;
    }
    public void sleep() {
        if (!testeAlive()) {
            return;
        }
        if(this.energyMax - this.energy < 5) {
            System.out.println("fail: nao esta com sono");
            return;
        }
            this.age += this.energyMax - this.energy;
            this.setEnergy(this.energyMax);
            this.setHungry(hungry - 1);
    }

    public void setClean(int clean) {
        if(clean <= 0){
            this.clean = 0;
            System.out.println("fail: pet morreu de sujeira");
            this.alive = false;
        }else if(clean > this.cleanMax) {
            this.clean = this.cleanMax;
        }
        else{
            this.clean = clean;
        }
    }

    public void setEnergy(int energy) {
        if(energy <= 0){
            this.energy = 0;
            System.out.println("fail: pet morreu de fraqueza");
            this.alive = false;
        }else if(energy > this.energyMax) {
            this.energy = this.energyMax;
        }else{
            this.energy = energy;
        }
    }
    public void setHungry(int hungry) {
        if(hungry <= 0){
            this.hungry = 0;
            System.out.println("fail: pet morreu de fome");
            this.alive = false;
        }else if(hungry > this.hungryMax) {
            this.hungry = this.hungryMax;
        }
        else {
            this.hungry = hungry;
        }
    }

    public String toString(){
        String ss = "";
        ss +=  "E:" + energy + "/" + energyMax + ", "
                +  "S:" + hungry + "/" + hungryMax + ", "
                +  "L:" + clean + "/" + cleanMax + ", "
                +  "D:" + diamonds + ", " + "I:"  + age;
        return ss;
    }
}

public class Solver {
    public static void main(String[] a) {
        Pet pet = new Pet(0, 0, 0);

        while (true) {
            var line = input();
            write("$" + line);
            var args = line.split(" ");

            if      (args[0].equals("end"))   { break;                                                                           }
            else if (args[0].equals("show"))  { write(pet.toString());                                                           }
            else if (args[0].equals("init"))  { pet = new Pet((int)number(args[1]), (int)number(args[2]), (int)number(args[3])); }
            else if (args[0].equals("play"))  { pet.play();                                                                      }
            else if (args[0].equals("eat"))   { pet.eat();                                                                       }
            else if (args[0].equals("sleep")) { pet.sleep();                                                                     }
            else if (args[0].equals("shower")){ pet.shower();                                                                    }
            else                              { write("fail: comando invalido");                                                 }
        }
    }

    private static Scanner scanner = new Scanner(System.in);
    private static String  input()              { return scanner.nextLine(); }
    private static double  number(String value) { return Double.parseDouble(value); }
    private static void    write(String value)  { System.out.println(value); }
}
