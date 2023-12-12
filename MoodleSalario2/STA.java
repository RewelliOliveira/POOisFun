package MoodleSalario2;

public class STA extends Funcionario {
    protected int nivel;

    public STA(String nome, int nivel) {
        super(nome);
        this.maxDiarias = 1;
        this.nivel = nivel;
    }

    public int getNivel() {
        return this.nivel;
    }

    public int getSalario() {
        return 3000 + (this.nivel * 300) + (diarias * 100) + bonus;
    }

    public String toString() {
        return "sta:" + this.nome + ":" + this.nivel + ":" + getSalario();
    }
}
