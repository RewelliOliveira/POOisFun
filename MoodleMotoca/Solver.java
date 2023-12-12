package MoodleMotoca;
import java.util.*;

class Person {
    private String name;
    private int age;
    public Person(String name, int age){
        this.name = name;
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }
    public String toString(){
        return String.format(getName() + ":" + getAge());
    }
}

class Motorcycle {
    private Person person;
    private int power;
    private int time;

    public Motorcycle(int power){
        this.power = power;
        this.time = 0;
        this.person = null;
    }
    //getters
    public int getPower() {
        return power;
    }
    public int getTime() {
        return time;
    }
    public Person getPerson() {
        return person;
    }


    public boolean enter(Person person) {
        if(getPerson() == null){
            this.person = person;
            return true;
        }else{
            System.out.println("fail: busy motorcycle");
            return false;
        }
    }

    public Person leave() {
        if(getPerson() != null){
            Person aux = this.person;
            this.person = null;
            return aux;
        }else{
            System.out.println("fail: empty motorcycle");
            return null;
        }
    }

    public void drive(int time){
        if(getTime() > 0){
            if(this.person != null){
                if(this.person.getAge() <= 10){
                    if (getTime() - time >= 0){
                        this.time -= time;
                    }else{
                        System.out.println("fail: time finished after " + (time - getTime()) + " minutes");
                        this.time = 0;
                    }
                }else{
                    System.out.println("fail: too old to drive");
                }
            }else{
                System.out.println("fail: empty motorcycle");
            }
        }else{
            System.out.println("fail: buy time first");
        }
    }

    public void buyTime(int time){
        this.time += time;
    }
    public void honk(){
        String pem = "P";
        for (int i = 0; i < this.power; i++) {
            pem += "e";
        }
        pem += "m";
        System.out.println(pem);
    }

    public String toString(){
        if(getPerson() != null)  return String.format("power:" + getPower() + ", time:" + getTime() + ", person:(" + getPerson() + ")");
        return String.format("power:%d, time:%d, person:(empty)", getPower(), getTime());
    }
}
class Solver{
    static Motorcycle motoca = new Motorcycle(1);

    public static void main(String[] args) {
        while(true) {
            String line = input();
            args = line.split(" ");
            write('$' + line);

            if      (args[0].equals("show"))     {System.out.println(motoca);                         }
            else if (args[0].equals("init"))     {motoca = new Motorcycle(number(args[1]));           }
            else if (args[0].equals("enter"))    {motoca.enter(new Person(args[1], number(args[2]))); }
            else if (args[0].equals("drive"))    {motoca.drive(number(args[1]));                       }
            else if (args[0].equals("buy"))      {motoca.buyTime(number(args[1]));                     }
            else if (args[0].equals("honk"))     {motoca.honk();                                       }
            else if (args[0].equals("end"))      { break;                                              }
            else if (args[0].equals("leave"))    {
                Person person = motoca.leave();
                if(person != null) {
                    System.out.println(person.toString());
                }
            }
            else {
                System.out.println("fail: comando invalido");
            }
        }
    }

    static Scanner scanner = new Scanner(System.in);
    public static String input()           { return scanner.nextLine();    }
    public static void write(String value) { System.out.println(value);    }
    public static int number(String str)   { return Integer.parseInt(str); }
}