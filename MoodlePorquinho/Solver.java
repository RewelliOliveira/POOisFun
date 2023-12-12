package MoodlePorquinho;

import java.util.*;
enum Coin {
    C10 (0.10, 1, "C10"),
    C25 (0.25, 2, "C25"),
    C50 (0.50, 3, "C50"),
    C100(1.00, 4, "C100");

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
        return String.format("%.2f", this.value + ":" + this.volume);
    }
}

class Item {
    private String label;
    private int volume;

    public Item(String label, int volume) {
        this.label = label;
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

    @Override
    public String toString() {
        return String.format(this.label + ":" + this.volume);
    }
}

class Pig {
    private boolean broken;
    private ArrayList<Coin> coins;
    private ArrayList<Item> items;
    private int volumeMax;

    public Pig(int volumeMax) {
        this.volumeMax = volumeMax;
        this.coins = new ArrayList<Coin>();
        this.items = new ArrayList<Item>();
    }

    public Coin createCoin(String valor) {
        switch (valor) {
            case "10":
                return Coin.C10;
            case "25":
                return Coin.C25;
            case "50":
                return Coin.C50;
            case "100":
                return Coin.C100;
            default:
                return null;
        }
    }

    public boolean addCoin(Coin coin) throws Exception {
        if(!broken){
            int volumeAtual = 0;
            for (Coin value : this.coins) {
                volumeAtual += value.getVolume();
            }
            for (Item item : this.items) {
                volumeAtual += item.getVolume();
            }

            if(volumeMax >= coin.getVolume() + volumeAtual){
                this.coins.add(coin);
                return true;
            }else {
                throw new VolumeException(true);
            }
        }else {
            throw new QuebradoException(true);
        }
    }

    public void addItem(Item item) throws Exception {
        if(!broken){
            int volumeAtual = 0;
            for (Coin coin : this.coins) {
                volumeAtual += coin.getVolume();
            }
            for (Item value : this.items) {
                volumeAtual += value.getVolume();
            }

            if(volumeMax >= item.getVolume() + volumeAtual){
                this.items.add(item);
            }else {
                throw new VolumeException(true);
            }
        }else {
            throw new QuebradoException(true);
        }
    }

    public void breakPig() throws Exception{
        if(!broken){
            this.broken = true;
        }else {
            throw new QuebradoException(true);
        }
    }

    public ArrayList<String> extractCoins() throws Exception {
        if(broken){
            ArrayList<String> auxiliar = new ArrayList<String>();
            for(Coin coin : this.coins){
                auxiliar.add(String.format("%.2f:%d", coin.getValue(), coin.getVolume()));
            }
            this.coins = new ArrayList<Coin>();
            return auxiliar;
        }else {
            throw new QuebradoException(false);
        }
    }

    public ArrayList<String> extractItems() throws Exception {
        if(broken){
            ArrayList<String> auxiliar = new ArrayList<String>();
            for(Item item : this.items){
                auxiliar.add(item.getLabel() + ":" + item.getVolume());
            }
            this.items = new ArrayList<Item>();
            return auxiliar;
        }else {
            throw new QuebradoException(false);
        }
    }

    @Override
    public String toString() {
        String string = "state=";

        if(broken){
            string += "broken";
        }else {
            string += "intact";
        }

        string += " : coins=[";

        for(int i = 0; i < this.coins.size(); i++){
            string += String.format("%.2f:%d", this.coins.get(i).getValue(), this.coins.get(i).getVolume());

            if(i + 1 != this.coins.size()){
                string += ", ";
            }
        }

        string += "] : items=[";

        for(int i = 0; i < this.items.size(); i++){
            string += this.items.get(i).getLabel() + ":" + this.items.get(i).getVolume();

            if(i + 1 != this.items.size()){
                string += ", ";
            }
        }

        string += "] : value=";

        double value = 0;
        int volumeTotal = 0;
        for(Coin coin : this.coins){
            value += coin.getValue();
        }

        string += String.format("%.2f : volume=%d/%d", value, getVolume(), this.volumeMax);
        return string;
    }

    public int getVolume() {
        if(broken){
            return 0;
        }else {
            int volume = 0;
            for(Coin coin : this.coins){
                volume += coin.getVolume();
            }
            for(Item item : this.items){
                volume += item.getVolume();
            }
            return volume;
        }
    }

    public double getValue() {
        int value = 0;
        for(Coin coin : this.coins){
            value += (int) coin.getValue();
        }
        return value;
    }

    public int getVolumeMax() {
        return volumeMax;
    }

    public int getVolumeRestante() {
        return getVolumeMax() - getVolume();
    }

    public boolean isBroken() {
        return broken;
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
            return "fail: you must break the pig first\n[]";
        }
    }
}

class VolumeException extends Exception {
    private boolean cheio;

    public VolumeException(boolean cheio){
        this.cheio = cheio;
    }

    public String getMessage(){
        return "fail: the pig is full";
    }
}

public class Solver {
    public static void main(String[] arg) {
        Pig pig = new Pig(5);

        label:
        while (true) {
            String line = input();
            println("$" + line);
            String[] args = line.split(" ");

            try{
                switch (args[0]) {
                    case "end":
                        break label;
                    case "init":
                        pig = new Pig((int) number(args[1]));
                        break;
                    case "show":
                        println(pig);
                        break;
                    case "addCoin":
                        pig.addCoin(pig.createCoin(args[1]));
                        break;
                    case "addItem":
                        pig.addItem(new Item(args[1], (int) number(args[2])));
                        break;
                    case "break":
                        pig.breakPig();
                        break;
                    case "extractCoins":
                        println("[" + String.join(", ", pig.extractCoins()) + "]");
                        break;
                    case "extractItems":
                        println("[" + String.join(", ", pig.extractItems()) + "]");
                        break;
                    default:
                        println("fail: comando invalido");
                        break;
                }
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
