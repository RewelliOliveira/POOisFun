package MoodleSalario;

abstract class Funcionario {
    protected int bonus, diarias, maxDiarias;
    protected String nome;

    public Funcionario(String nome) {
        this.nome = nome;
        this.diarias = 0;
    }

    public void addDiaria() throws Exception {
        if (this instanceof Tercerizado) throw new Exception("fail: terc nao pode receber diaria");
        if (diarias == maxDiarias) throw new Exception("fail: limite de diarias atingido");
        ++diarias;
    }

    public String getNome() {
        return this.nome;
    }

    public abstract int getSalario();

    public void setBonus(int value) {
        this.bonus = value;
    }
}
