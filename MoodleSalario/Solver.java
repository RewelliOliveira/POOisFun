package MoodleSalario;
//1. Fiz tudo e passou em todos os testes
//2. Comecei a fazer sozinho, fiz boa parte, mas por conta do tempo pedi ajuda a meu amigo
//3. Aprendi como estruturar melhor o meu codigo e como usar de forma apropriada
//herença, mas ainda tenho um pouco de dificuldade
//4. Demorei mais de 4 horas juntando o tempo que eu fiquei fazendo na aula.

import java.util.*;

abstract class Funcionario{
    protected int bonus, diarias, maxDiarias;
    protected String nome;

    public Funcionario(String nome){
        this.nome = nome;
        this.diarias = 0;
    }

    public void addDiaria() throws Exception{
        if(this instanceof Tercerizado) throw new Exception("fail: terc nao pode receber diaria");
        if(diarias == maxDiarias) throw new Exception("fail: limite de diarias atingido");
        ++diarias;
    }
    public String getNome(){
        return this.nome;
    }
    public abstract int getSalario();

    public void setBonus(int value){
        this.bonus = value;
    }
}

class Professor extends Funcionario{
    protected String classe;

    public Professor(String nome, String classe){
        super(nome);
        this.maxDiarias = 2;
        this.classe = classe;
    }

    public String toString(){
        return "prof:" + this.nome + ":" + this.classe + ":" + getSalario();
    }

    public String getClasse(){
        return this.classe;
    }

    public int getSalario(){
        var value = 0;
        switch(classe){
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

class STA extends Funcionario{
    protected int nivel;

    public STA(String nome, int nivel){
        super(nome);
        this.maxDiarias = 1;
        this.nivel = nivel;
    }

    public String toString(){
        return "sta:" + this.nome + ":" + this.nivel + ":" + getSalario();
    }

    public int getNivel(){
        return this.nivel;
    }
    public int getSalario(){
        return 3000 + (this.nivel * 300) + (diarias * 100) + bonus;
    }
}

class Tercerizado extends Funcionario{
    protected int horas;
    protected boolean isSalubre;
    public Tercerizado(String nome, int horas, String isSalubre){
        super(nome);
        this.horas = horas;
        this.isSalubre = isSalubre.equals("sim");
        this.maxDiarias = 0;
    }

    public int getHoras(){
        return this.horas;
    }

    public String isSalubre(){
        return (isSalubre) ? "sim" : "nao";
    }
    public int getSalario(){
        if(isSalubre) return 4 * this.horas + 500 + bonus;
        return 4 * this.horas;
    }

    public String toString(){
        return "ter:" + this.nome + ":" + this.horas + ":" + isSalubre() + ":" + getSalario();
    }
}
class UFC{
    private final Map<String, Funcionario> funcionarios;

    public UFC(){
        this.funcionarios = new TreeMap<>();
    }

    public void addFuncionario(Funcionario funcionario){
        this.funcionarios.put(funcionario.getNome(), funcionario);
    }
    public Funcionario getFuncionario(String nome){
        return this.funcionarios.get(nome);
    }
    public void rmFuncionario(String nome){
        this.funcionarios.remove(nome);
    }
    public void setBonus(int bonus){
        for(Funcionario funcionario : funcionarios.values()){
            funcionario.setBonus(bonus / funcionarios.size());
        }
    }

    public String toString(){
        StringBuilder saida = new StringBuilder();

        for(Funcionario f : funcionarios.values()){
            saida.append(f).append("\n");
        }

        return saida.toString();
    }

}

class Solver {
    public static void main(String[] args){
        System.out.println("side_by_side=080");
        Scanner scanner = new Scanner(System.in);

        UFC ufc = new UFC();
        label:
        while(true){
            try{
                String line = scanner.nextLine();
                System.out.println("$" + line);
                String[] ui = line.split(" ");
                switch (ui[0]) {
                    case "end":
                        break label;
                    case "addProf":
                        ufc.addFuncionario(new Professor(ui[1], ui[2]));
                        break;
                    case "addSta":
                        ufc.addFuncionario(new STA(ui[1], Integer.parseInt(ui[2])));
                        break;
                    case "addTer":
                        ufc.addFuncionario(new Tercerizado(ui[1], Integer.parseInt(ui[2]), ui[3]));
                        break;
                    case "rm":
                        ufc.rmFuncionario(ui[1]);
                        break;
                    case "showAll":
                        System.out.print(ufc);
                        break;
                    case "show":
                        System.out.println(ufc.getFuncionario(ui[1]));
                        break;
                    case "addDiaria":
                        ufc.getFuncionario(ui[1]).addDiaria();
                        break;
                    case "setBonus":
                        ufc.setBonus(Integer.parseInt(ui[1]));
                        break;
                    default:
                        System.out.print("fail: comando invalido");
                        break;
                }
            }catch (Exception me){
                System.out.println(me.getMessage());
            }
        }
    }
}