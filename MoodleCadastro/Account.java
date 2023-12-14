package MoodleCadastro;

import java.text.DecimalFormat;

abstract class Account {
    protected double balance;
    private static int nextId = 0;
    protected int accId;
    protected String clientId;
    protected String typeId;

    public Account(String clientId, String typeId) {
        balance = 0;
        accId = nextId++;
        this.clientId = clientId;
        this.typeId = typeId;
    }

    public void deposit(double value) {
        balance += value;
    }

    public void withdraw(double value) throws Exception {
        if (value > this.balance) {
            throw new Exception("fail: saldo insuficiente");
        }
        this.balance -= value;
    }

    public void transfer(Account other, double value) throws Exception {
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
