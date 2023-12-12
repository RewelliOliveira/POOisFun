package MoodleSalario2;

public class Professor extends Funcionario {
    protected String classe;

    public Professor(String nome, String classe){
        super(nome);
        this.maxDiarias = 2;
        this.classe = classe;
    }

    public String getClasse(){
        return classe;
    }

    public int getSalario(){
        var v = 0;

        switch (classe){
            case "A":
                v = 3000;
                break;
            case "B":
                v = 5000;
                break;
            case "C":
                v = 7000;
                break;
            case "D":
                v = 9000;
                break;
            default:
                v = 11000;
                break;
        }
        return v + (100 * diarias) + bonus;
    }

    public String toString(){
        return "prof:" + this.nome + ":" + this.classe + ":" + getSalario();
    }

}
