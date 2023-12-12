package MoodleSalario2;

public class Terceirizado extends Funcionario {
    protected int horas;
    protected boolean isSalubre;

    public Terceirizado(String nome, int horas, String isSalubre) {
        super(nome);
        this.isSalubre = false;
    }

    public void addDiaria() {

    }

    public int getHoras() {
        return 1;
    }

    public String getisSalubre() {
        return "";
    }

    public int getSalario() {
        return 1;
    }

    public String toString() {
        return "";
    }
}
