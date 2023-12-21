package MoodleSalario;

class Professor extends Funcionario {
    protected String classe;

    public Professor(String nome, String classe) {
        super(nome);
        this.maxDiarias = 2;
        this.classe = classe;
    }

    public String toString() {
        return "prof:" + this.nome + ":" + this.classe + ":" + getSalario();
    }

    public String getClasse() {
        return this.classe;
    }

    public int getSalario() {
        var value = 0;
        switch (classe) {
            case "A":
                value = 3000;
                break;
            case "B":
                value = 5000;
                break;
            case "C":
                value = 7000;
                break;
            case "D":
                value = 9000;
                break;
            default:
                value = 11000;
                break;
        }
        return value + diarias * 100 + bonus;
    }

}
