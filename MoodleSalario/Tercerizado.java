package MoodleSalario;

class Tercerizado extends Funcionario {
    protected int horas;
    protected boolean isSalubre;

    public Tercerizado(String nome, int horas, String isSalubre) {
        super(nome);
        this.horas = horas;
        this.isSalubre = isSalubre.equals("sim");
        this.maxDiarias = 0;
    }

    public int getHoras() {
        return this.horas;
    }

    public String isSalubre() {
        return (isSalubre) ? "sim" : "nao";
    }

    public int getSalario() {
        if (isSalubre) return 4 * this.horas + 500 + bonus;
        return 4 * this.horas;
    }

    public String toString() {
        return "ter:" + this.nome + ":" + this.horas + ":" + isSalubre() + ":" + getSalario();
    }
}
