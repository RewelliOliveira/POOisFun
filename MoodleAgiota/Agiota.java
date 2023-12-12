package MoodleAgiota;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class Agiota {
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
