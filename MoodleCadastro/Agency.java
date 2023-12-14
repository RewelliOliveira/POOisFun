package MoodleCadastro;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

class Agency {
    private Map<Integer, Account> accounts;
    private Map<String, Client> clients;

    private Account getAccount(int accountId) throws Exception {
        if (!this.accounts.containsKey(accountId)) {
            throw new Exception("fail: conta nao encontrada");
        }
        return this.accounts.get(accountId);
    }

    public Agency() {
        this.accounts = new HashMap<Integer, Account>();
        this.clients = new LinkedHashMap<String, Client>();
    }

    public void addClient(String clientId) {
        Client client = new Client(clientId);
        this.clients.put(clientId, client);

        Account cc = new CheckingAccount(clientId);
        Account cp = new SavingsAccount(clientId);
        this.accounts.put(cc.getAccId(), cc);
        this.accounts.put(cp.getAccId(), cp);

        client.addAccount(cc);
        client.addAccount(cp);
    }

    public void deposit(int accId, double value) throws Exception {
        this.getAccount(accId).deposit(value);
    }

    public void withdraw(int accId, double value) throws Exception {
        this.getAccount(accId).withdraw(value);
    }

    public void transfer(int fromAccId, int toAccId, double value) throws Exception {
        this.getAccount(fromAccId).transfer(this.getAccount(toAccId), value);
    }

    public void updateMonthly() {
        for (Account acc : this.accounts.values()) {
            acc.updateMonthly();
        }
    }

    @Override
    public String toString() {
        String s = "- Clients\n";
        for (Client client : this.clients.values()) {
            s += client;
        }
        s += "- Accounts\n";
        for (Account acc : this.accounts.values()) {
            s += acc;
        }
        return s;
    }
}
