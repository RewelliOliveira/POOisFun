package MoodleSalario2;

public abstract class Funcionario {
    protected int bonus, diarias, maxDiarias;
    protected String nome;

    public Funcionario(String nome){
        this.nome = nome;
        this.diarias = 0;
    }

    public void addDiaria() throws Exception{
        if(diarias == maxDiarias) throw new Exception("fail: max de diarias atingido");
        else diarias++;
    }

    public String getNome(){
        return nome;
    }
    public abstract int getSalario();

    public void setBonus(int bonus){
        this.bonus = bonus;
    }
}
