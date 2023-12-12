package MoodleAgiota;

import java.util.ArrayList;

public class Client {
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
