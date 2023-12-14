package MoodleCadastro;

class SavingsAccount extends Account {
    protected double monthlyInterest;

    public SavingsAccount(String clientId) {
        super(clientId, "CP");
        this.monthlyInterest = 0.01;
    }

    @Override
    public void updateMonthly() {
        this.balance += this.monthlyInterest * this.balance;
    }
}
