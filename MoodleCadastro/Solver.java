package MoodleCadastro;
import java.util.*;
import java.text.DecimalFormat;

class Client {
    private String clientId;
    private ArrayList<Account> accounts;

    public Client( String clientId ) {
        accounts = new ArrayList<Account>();
        this.clientId = clientId;
    }

    public void addAccount( Account acc ) {
        this.accounts.add(acc);
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }
    public String getClientId() {
        return clientId;
    }

    @Override
    public String toString() {
        return String.format(this.clientId + " [" + this.accounts.get(0).getAccId() + ", " + this.accounts.get(1).getAccId() + "]" + "\n");
    }
}

abstract class Account {
    protected double balance;
    private static int nextId = 0;
    protected int accId;
    protected String clientId;
    protected String typeId;

    public Account( String clientId, String typeId ) {
        balance = 0;
        accId = nextId++;
        this.clientId = clientId;
        this.typeId = typeId;
    }

    public void deposit( double value ) {
        balance += value;
    }
    public void withdraw( double value ) throws Exception{
        if ( value > this.balance ) {
            throw new Exception("fail: saldo insuficiente");
        }
        this.balance -= value;
    }

    public void transfer( Account other, double value ) throws Exception{
        withdraw(value);
        other.deposit(value);
    }

    @Override
    public String toString() {
        DecimalFormat d = new DecimalFormat("0.00");
        return String.format(this.accId + ":" + this.clientId
                + ":" + d.format(this.balance)
                + ":" + this.typeId + "\n");
    }

    public double getBalance() {
        return balance;
    }
    public int getAccId() {
        return accId;
    }
    public String getClientId() {
        return clientId;
    }
    public String getTypeId() {
        return typeId;
    }

    public abstract void updateMonthly();
}

class Agency {
    private Map<Integer, Account> accounts;
    private Map<String, Client> clients;

    private Account getAccount(int accountId) throws Exception{
        if ( !this.accounts.containsKey( accountId ) ) {
            throw new Exception("fail: conta nao encontrada");
        }
        return this.accounts.get( accountId );
    }

    public Agency() {
        this.accounts = new HashMap<Integer,Account>();
        this.clients = new LinkedHashMap<String,Client>();
    }

    public void addClient(String clientId) {
        Client client = new Client( clientId );
        this.clients.put( clientId, client );

        Account cc = new CheckingAccount( clientId );
        Account cp = new SavingsAccount( clientId );
        this.accounts.put( cc.getAccId(), cc );
        this.accounts.put( cp.getAccId(), cp );

        client.addAccount( cc );
        client.addAccount( cp );
    }

    public void deposit(int accId, double value) throws Exception{
        this.getAccount( accId ).deposit( value );
    }

    public void withdraw(int accId, double value) throws Exception{
        this.getAccount( accId ).withdraw( value );
    }

    public void transfer(int fromAccId, int toAccId, double value) throws Exception{
        this.getAccount( fromAccId ).transfer(this.getAccount( toAccId ), value );
    }

    public void updateMonthly() {
        for ( Account acc : this.accounts.values() ) {
            acc.updateMonthly();
        }
    }

    @Override
    public String toString() {
        String s = "- Clients\n";
        for ( Client client : this.clients.values() ) {
            s += client;
        }
        s += "- Accounts\n";
        for ( Account acc : this.accounts.values() ) {
            s += acc;
        }
        return s;
    }
}
class CheckingAccount extends Account {
    protected double monthlyFee;

    public CheckingAccount( String clientId ) {
        super( clientId, "CC" );
        this.monthlyFee = 20.0;
    }

    @Override
    public void updateMonthly() {
        this.balance -= this.monthlyFee;
    }
}

class SavingsAccount extends Account {
    protected double monthlyInterest;

    public SavingsAccount( String clientId ) {
        super( clientId, "CP" );
        this.monthlyInterest = 0.01;
    }

    @Override
    public void updateMonthly() {
        this.balance += this.monthlyInterest * this.balance;
    }
}

public class Solver {
    public static void main(String[] arg) {
        println("side_by_side=080");

        Agency agency = new Agency();
        while (true) {
            String line = input();
            println("$" + line);
            String[] args = line.split(" ");

            try {
                if      (args[0].equals("end"))       { break; }
                else if (args[0].equals("show"))      { print(agency); }
                else if (args[0].equals("addCli"))    { agency.addClient( args[1] ); }
                else if (args[0].equals("deposito"))  { agency.deposit( (int) number(args[1]), number(args[2]) ); }
                else if (args[0].equals("saque"))     { agency.withdraw( (int) number(args[1]), number(args[2]) ); }
                else if (args[0].equals("transf"))    { agency.transfer( (int) number(args[1]), (int) number(args[2]), number(args[3]) ); }
                else if (args[0].equals("update"))    { agency.updateMonthly(); }
                else                                { println("fail: comando invalido"); }
            } catch (Exception e) {
                println( e.getMessage() );
            }
        }
    }

    private static Scanner scanner = new Scanner(System.in);
    private static String  input()                { return scanner.nextLine();        }
    private static double  number(String value)   { return Double.parseDouble(value); }
    public  static void    println(Object value)  { System.out.println(value);        }
    public  static void    print(Object value)    { System.out.print(value);          }
}