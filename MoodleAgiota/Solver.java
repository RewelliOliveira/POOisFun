package MoodleAgiota;
import java.util.*;

enum Label{
    GIVE,TAKE,PLUS;

    public String toString() {
        return this.name().toLowerCase();
    }
}

class Operation {
    private static int nextOpId = 0;
    private int id;
    private String name;
    private Label label;
    private int value;

    public Operation( String name, Label label, int value ) {
        this.id = Operation.nextOpId++;
        this.name = name;
        this.label =label;
        this.value = value;
    }
    //Getters
    public String getName() {
        return name;
    }
    public Label getLabel() {
        return label;
    }
    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("id:" + id + " " + label + ":" + name + " " + value);
    }
}

class Client {
    private String name;
    private int limite;
    ArrayList<Operation> operations;

    public Client(String name, int limite) {
        this.operations = new ArrayList<Operation>();
        this.name = name;
        this.limite = limite;
    }
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder(this.name + " " + this.getBalance() + "/" + this.limite + "\n");
        for(Operation operations : this.operations){
            string.append(operations).append("\n");
        }
        return String.format(string.toString());
    }

    public String getName() {
        return name;
    }
    public int getLimite() {
        return limite;
    }
    public ArrayList<Operation> getOperations() {
        return operations;
    }

    public void addOperation(Operation operation) {
        this.operations.add(operation);
    }

    public int getBalance() {
        int balance = 0;
        for(Operation operation :operations){
            if(operation.getLabel().equals(Label.GIVE) || operation.getLabel().equals(Label.PLUS)) {
                balance += operation.getValue();
            }else{
                balance -= operation.getValue();
            }
        }
        return balance;
    }
}


class Agiota {
    private ArrayList<Client> aliveList;
    private ArrayList<Client> deathList;
    private ArrayList<Operation> aliveOper;
    private ArrayList<Operation> deathOper;

    private int searchClient(String name) {
        for(int k = 0; k < this.aliveList.size(); k++){
            if(Objects.equals(name, this.aliveList.get(k).getName())){
                return k;
            }
        }
        return -1;
    }
    private void pushOperation(Client client, String name, Label label, int value) {
        Operation operation = new Operation(name, label, value);
        client.addOperation(operation);
        aliveOper.add(operation);
    }

    public Agiota() {
        aliveList = new ArrayList<Client>();
        deathList = new ArrayList<Client>();
        aliveOper = new ArrayList<Operation>();
        deathOper = new ArrayList<Operation>();
    }

    public Client getClient(String name) {
        int ind = searchClient(name);
        if(ind == -1) return null;
        return aliveList.get(ind);
    }

    public void addClient(String name, int limite) throws Exception{
        int ind = searchClient(name);
        if(ind != -1){
            throw new Exception("fail: cliente ja existe");
        }
        aliveList.add(new Client(name, limite));
        this.aliveList.sort(new Comparator<Client>() {
            public int compare(Client o1, Client o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    public void give(String name, int value) throws Exception{
        Client client = getClient(name);
        if(client == null) {
            throw new Exception("fail: cliente nao existe");
        }
        if(client.getBalance() + value > client.getLimite()){
            throw new Exception("fail: limite excedido");
        }
        pushOperation(client, name, Label.GIVE, value);
    }

    public void take(String name, int value) throws Exception{
        Client client = getClient(name);
        if(client == null){
            throw new Exception("fail: cliente nao existe");
        }
        pushOperation(client, name, Label.TAKE, value);
    }

    public void kill(String name) {
        int index = searchClient(name);
        if(index == -1) return;
        deathList.add(aliveList.remove(index));
        for(int k = 0; k < aliveOper.size(); k++){
            if ( this.aliveOper.get(k).getName().equals(name) ) {
                this.deathOper.add( this.aliveOper.remove(k) );
                k--;
            }
        }
    }

    public void plus() {
        for (Client client : this.aliveList) {
            this.pushOperation( client, client.getName(), Label.PLUS, (int) Math.ceil( 0.1*client.getBalance() ) );
        }
        for (int i=0; i<this.aliveList.size(); i++) {
            Client client = this.aliveList.get(i);
            if ( client.getBalance() > client.getLimite() ) {
                this.kill( client.getName() );
                i--;
            }
        }
    }

    @Override
    public String toString() {
        String ss = "";
        for ( Client client : this.aliveList ) {
            ss += ":) " + client.getName() + " " + client.getBalance() + "/" + client.getLimite() + "\n";
        }
        for ( Operation oper : this.aliveOper ) {
            ss += "+ " + oper + "\n";
        }
        for ( Client client : this.deathList ) {
            ss += ":( " + client.getName() + " " + client.getBalance() + "/" + client.getLimite() + "\n";
        }
        for ( Operation oper : this.deathOper ) {
            ss += "- " + oper + "\n";
        }
        return ss;
    }
}

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