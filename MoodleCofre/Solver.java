package MoodleCofre;
import java.util.*;
import java.util.stream.Collectors;

interface Valuable {
    public String getLabel();
    public double getValue();
    public int getVolume();

    public String toString();
}

enum Coin implements Valuable {
    M10 (0.10, 1, "M10"),
    M25 (0.25, 2, "M25"),
    M50 (0.50, 3, "M50"),
    M100(1.00, 4, "M100");

    private double value;
    private int volume;
    private String label;

    private Coin(double value, int volume, String label){
        this.value = value;
        this.volume = volume;
        this.label = label;
    }
    public double getValue(){
        return this.value;
    }
    public int getVolume(){
        return this.volume;
    }
    public String getLabel(){
        return this.label;
    }
    public String toString(){
        return String.format("%s:%.2f:%d", this.label, this.value, this.volume);
    }
}

class Item implements Valuable {
    private String label;
    private double value;
    private int volume;

    public Item(String label, double value, int volume) {
        this.label = label;
        this.value = value;
        this.volume = volume;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getVolume() {
        return this.volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public double getValue(){
        return this.value;
    }

    @Override
    public String toString() {
        return String.format("%s:%.2f:%d", this.label, this.value, this.volume);
    }
}

class Pig {
    private boolean broken;
    private ArrayList<Valuable> valuables;
    private int volumeMax;

    Pig(int volumeMax){
        this.broken = false;
        this.valuables = new ArrayList<>();
        this.volumeMax = volumeMax;
    }

    public void addValuable(Valuable valuable) throws Exception{
        int volume = getVolume();

        if(volume + valuable.getVolume() <= this.volumeMax){
            if(!this.broken){
                this.valuables.add(valuable);
            }else {
                throw new QuebradoException(true);
            }
        }else {
            throw new VolumeException();
        }
    }

    public boolean breakPig() throws Exception{
        if(!this.broken){
            this.broken = true;
            return true;
        }else {
            throw new QuebradoException(true);
        }
    }

    public ArrayList<Coin> getCoins() throws Exception{
        if(this.broken){
            ArrayList<Coin> auxiliarCoin = new ArrayList<>();
            for(int i = 0; i < this.valuables.size(); i++){
                if(this.valuables.get(i) instanceof Coin){
                    auxiliarCoin.add((Coin) this.valuables.get(i));
                    this.valuables.remove(i);
                    i--;
                }
            }
            return auxiliarCoin;
        }else {
            throw new QuebradoException(false);
        }
    }

    public ArrayList<Item> getItems() throws Exception{
        if(this.broken){
            ArrayList<Item> auxiliarItem = new ArrayList<>();
            for(int i = 0; i < this.valuables.size(); i++){
                if(this.valuables.get(i) instanceof Item){
                    auxiliarItem.add((Item) this.valuables.get(i));
                    this.valuables.remove(i);
                    i--;
                }
            }
            return auxiliarItem;
        }else {
            throw new QuebradoException(false);
        }
    }

    public double calcValue(){
        double soma = 0;
        for(Valuable v : valuables){
            soma += v.getValue();
        }
        return soma;
    }

    public int getVolume(){
        int volume = 0;
        if(this.broken){
            return volume;
        }else {
            for(Valuable v : valuables){
                volume += v.getVolume();
            }
        }
        return volume;
    }

    public int getVolumeMax(){
        return this.volumeMax;
    }

    public boolean isBroken(){
        return this.broken;
    }

    public String toString(){
        StringBuilder string = new StringBuilder("[");

        int i = 0;

        for(Valuable c : valuables){
            if(c instanceof Coin){
                string.append(c.toString());
            }else if(c instanceof Item){
                string.append(c.toString());
            }
            if(i + 1 != this.valuables.size()){
                string.append(", ");
            }
            i++;
        }
        string.append("] : ");
        string.append(String.format("%.2f", calcValue()));
        string.append("$ : ");
        string.append(getVolume());
        string.append("/");
        string.append(getVolumeMax());
        string.append(" : ");

        if(this.broken){
            string.append("broken");
        }else {
            string.append("intact");
        }
        return string.toString();
    }
}
class QuebradoException extends Exception {
    private boolean quebrado;

    public QuebradoException(boolean quebrado){
        this.quebrado = quebrado;
    }

    public String getMessage(){
        if(quebrado){
            return "fail: the pig is broken";
        }else {
            return "fail: you must break the pig first";
        }
    }
}
class VolumeException extends Exception {
    public VolumeException(){
    }
    public String getMessage(){
        return "fail: the pig is full";
    }
}

public class Solver {
    public static void main(String[] arg) {
        Pig pig = new Pig(5);

        while (true) {
            String line = input();
            println("$" + line);
            String[] args = line.split(" ");

            try{
                if      (args[0].equals("end"))          { break; }
                else if (args[0].equals("init"))         { pig = new Pig( (int) number(args[1]) ); }
                else if (args[0].equals("show"))         { println(pig); }
                else if(args[0].equals("addCoin")) {
                    String value = args[1];
                    if     (value.equals("10"))  pig.addValuable(Coin.M10);
                    else if(value.equals("25"))  pig.addValuable(Coin.M25);
                    else if(value.equals("50"))  pig.addValuable(Coin.M50);
                    else if(value.equals("100")) pig.addValuable(Coin.M100);
                }
                else if (args[0].equals("addItem"))      {pig.addValuable(new Item(args[1], number(args[2]), (int) number(args[3])));}
                else if (args[0].equals("break"))        { pig.breakPig(); }
                else if (args[0].equals("extractCoins")) { println("[" + pig.getCoins().stream().map(Coin::toString).collect(Collectors.joining(", ")) + "]"); }
                else if (args[0].equals("extractItems")) { println("[" + pig.getItems().stream().map(Item::toString).collect(Collectors.joining(", ")) + "]"); }
                else                                     { println("fail: comando invalido"); }
            }catch(Exception e){
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