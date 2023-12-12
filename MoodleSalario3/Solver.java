package MoodleSalario3;

import java.util.*;

class MsgException extends RuntimeException {
    public MsgException(String message) {
        super(message);
        // this.detailMessage = message;
    }
}
abstract class Funcionario {
    protected String nome;
    protected int bonus;
    protected int diarias;
    protected int maxDiarias;
    public Funcionario(String nome, int maxDiarias){
        this.nome = nome;
        this.maxDiarias = maxDiarias;
        this.bonus = 0;

    }
    public String getNome(){
        return this.nome;
    }
    public void setBonus(int bonus){
        this.bonus = bonus;
    }
    //se não atingiu o máximo, adicione mais uma diária
    //se atingiu o máximo, lance uma MsgException
    public void addDiaria(){

        switch (this.maxDiarias){
            case 0:
                throw new MsgException("fail: terc nao pode receber diaria");
            case 2:
                if(this.diarias < 2){
                    this.diarias++;
                    return;
                } throw new MsgException("fail: limite de diarias atingido");
            case 1:
                if(this.diarias < 1){
                    this.diarias++;
                    return;
                } throw new MsgException("fail: limite de diarias atingido");
        }

    }
    //retorna bonus + diarias * 100
    public abstract int getSalario();
}
class Professor extends Funcionario {
    protected String classe;
    //inicializa classe e muda maxDiarias para 2
    public Professor(String nome, String classe){
        super(nome, 2);
        this.classe = classe;
    }
    public String getClasse(){
        return this.classe;
    }
    //lógica do salário do professor
    //usa o super.getSalario para pegar bonus e diarias
    public int getSalario(){

        switch (this.classe){
            case "A":
                return 3000 + this.diarias * 100 + this.bonus;
            case "B":
                return 5000 + this.diarias * 100 + this.bonus;
            case "C":
                return 7000 + this.diarias * 100 + this.bonus;
            case "D":
                return 9000 + this.diarias * 100 + this.bonus;
            case "E":
                return 11000 + this.diarias * 100 + this.bonus;
            default :
                return 0;
        }
    }
    public String toString(){
        return "prof:" + this.nome + ":" + getClasse() + ":" + getSalario() + '\n';
    }
}
class STA extends Funcionario {
    protected int nivel;
    public STA(String nome, int nivel){
        super(nome, 1);
        this.nivel = nivel;
    }
    public int getNivel(){
        return this.nivel;
    }
    public int getSalario(){
        return  3000 +  (300 * this.nivel) + (this.diarias * 100) +  this.bonus;
    }
    public String toString(){
        return "sta:" + this.nome + ":" + getNivel() + ":" + getSalario() + '\n';
    }
}
class Tercerizado extends Funcionario {
    protected int horas;
    protected boolean isSalubre = false;
    public Tercerizado(String nome, int horas, String isSalubre){
        super(nome, 0);
        this.horas = horas;
        this.isSalubre = (isSalubre.equals("sim")) ? true : false;

    }

    public int getHoras(){
        return this.horas;
    }
    public String getIsSalubre(){
        return (this.isSalubre) ? "sim" : "nao";
    }
    public int getSalario(){
        if(this.isSalubre){
            return (4 * this.horas + 500) + this.bonus;
        }

        return 4 * this.horas;
    }

    public String toString(){
        return "ter:" + this.nome + ":" + getHoras() + ":" + getIsSalubre() + ":" + getSalario() + '\n';
    }
}
class UFC {
    private Map<String, Funcionario> funcionarios = new TreeMap<>();
    public String toString(){
        String aux = "";

        for(Funcionario f : funcionarios.values()){
            aux += f.toString();
        }

        return aux;
    }


    public Funcionario getFuncionario(String nome){
        return funcionarios.get(nome);
    }
    public void addFuncionario(Funcionario funcionario){
        funcionarios.put(funcionario.getNome(), funcionario);

    }
    public void rmFuncionario(String nome){
        Funcionario f = getFuncionario(nome);

        if(f == null){
            throw new MsgException("fail:");
        }

        funcionarios.remove(nome);

    }
    //reparte o bonus para todos os funcionarios
    public void setBonus(int bonus){
        int aux = bonus / funcionarios.size();

        for(Funcionario f : funcionarios.values()){
            f.setBonus(aux);
        }

    }
}
class Solver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UFC ufc = new UFC();
        while (true) {
            try {
                String line = scanner.nextLine();
                System.out.println("$" + line);
                String ui[] = line.split(" ");
                if (ui[0].equals("end")) {
                    break;
                } else if (ui[0].equals("addProf")) {
                    ufc.addFuncionario(new Professor(ui[1], ui[2]));
                } else if (ui[0].equals("addSta")) {
                    ufc.addFuncionario(new STA(ui[1], Integer.parseInt(ui[2])));
                } else if (ui[0].equals("addTer")) {
                    ufc.addFuncionario(new Tercerizado(ui[1], Integer.parseInt(ui[2]), ui[3]));
                } else if (ui[0].equals("rm")) {
                    ufc.rmFuncionario(ui[1]);
                } else if (ui[0].equals("showAll")) {
                    System.out.print(ufc);
                } else if (ui[0].equals("show")) {
                    System.out.print(ufc.getFuncionario(ui[1]));
                } else if (ui[0].equals("addDiaria")) {
                    ufc.getFuncionario(ui[1]).addDiaria();
                } else if (ui[0].equals("setBonus")) {
                    ufc.setBonus(Integer.parseInt(ui[1]));
                } else {
                    System.out.print("fail: comando invalido");
                }
            } catch (MsgException me) {
                System.out.println(me.getMessage());
            }
        }
    }
}