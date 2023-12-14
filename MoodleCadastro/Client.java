package MoodleCadastro;

import java.util.ArrayList;

class Client {
    private String clientId;
    private ArrayList<Account> accounts;

    public Client(String clientId) {
        accounts = new ArrayList<Account>();
        this.clientId = clientId;
    }

    public void addAccount(Account acc) {
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
