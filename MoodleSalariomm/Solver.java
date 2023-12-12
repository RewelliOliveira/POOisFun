package MoodleSalariomm;
import java.util.*;

abstract class Funcionario {
    protected String nome;
    protected int bonus;
    protected int diarias;
    protected int maxDiarias;

    public Funcionario(String nome) {
        this.nome = nome;
        this.diarias = 0;
        this.maxDiarias = 0;
    }

    public String getNome() {
        return this.nome;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    //se atingiu o máximo, lance uma MsgException
    //se não atingiu o máximo, adicione mais uma diária
    public void addDiaria() throws Exception{
        if(diarias == maxDiarias) {
            throw new Exception("fail: limite de diarias atingido");
        }

        diarias++;
    }

    //retorna bonus + diarias * 100
    public int getSalario() {
        return this.bonus + this.diarias * 100;
    }

    @ Override
    public String toString() {
        // toString para pegar o nome para o toString dos demais de uma forma mais facil
        return this.nome;
    }
}

class Professor extends Funcionario {
    protected String classe;

    //inicializa classe e muda maxDiarias para 2
    public Professor(String nome, String classe) {
        super(nome);
        this.classe = classe;
        this.maxDiarias = 2;
    }

    public String getClasse() {
        return this.classe;
    }

    public int valClasse() {
        if(getClasse().equals("A")) return 3000;
        else if(getClasse().equals("B")) return 5000;
        else if(getClasse().equals("C")) return 7000;
        else if(getClasse().equals("D")) return 9000;
        else if(getClasse().equals("E")) return 11000;
        else return 0;
    }

    //lógica do salário do professor
    //usa o super.getSalario() para pegar bonus e diarias
    @Override
    public int getSalario() {
        return super.getSalario() + valClasse();
    }

    @Override
    public String toString() {
        // prof:elvis:D:9000
        return "prof:" + this.getNome() + ":" + this.getClasse() + ":" + this.getSalario();
    }
}

class STA extends Funcionario {
    protected int nivel;

    //inicializa nivel e muda maxDiarias para 1
    public STA(String nome, int nivel) {
        super(nome);
        this.nivel = nivel;
        this.maxDiarias = 1;
    }

    public int getNivel() {
        return this.nivel;
    }

    //lógica do salário do sta
    //usa o super.getSalario() para pegar bonus e diarias
    @Override
    public int getSalario() {
        return 3000 + 300 * getNivel() + this.bonus + (this.diarias * 100);
    }

    @Override
    public String toString() {
        // sta:gilmario:3:3900
        return "sta:" + super.toString() + ":" + this.getNivel() + ":" + this.getSalario();
    }
}

class Terceirizado extends Funcionario {
    protected int horas;
    protected boolean isSalubre;

    public Terceirizado(String nome, int horas, String isSalubre) {
        super(nome);
        this.horas = horas;
        this.isSalubre = (isSalubre.equals("sim") ? true : false);
    }

    public int getHoras() {
        return this.horas;
    }

    public String getIsSalubre() {
        return ((isSalubre) ? "sim" : "não");
    }

    //lance uma MsgException com um texto diferente
    @Override
    public void addDiaria() throws Exception{
        throw new Exception("fail: terc nao pode receber diaria");
    }

    //lógica do salário do terceirizado
    //usa o super.getSalario() para pegar bonus e diarias
    @Override
    public int getSalario() {
        return this.horas * 4 + (isSalubre ? 500 : 0);
    }

    @Override
    public String toString() {
        return "ter:" + super.toString() + ":" + this.horas + ":" + this.getIsSalubre() + ":" + this.getSalario();
    }
}

class UFC {
    private Map<String, Funcionario> funcionarios = new TreeMap<>();

    @Override
    public String toString() {
        String out = "";
        for(Funcionario f : funcionarios.values()) {
            out += f.toString() + "\n";
        }

        return out;
    }

    public Funcionario getFuncionario(String nome) throws Exception {
        if(funcionarios.get(nome) == null) {
            throw new Exception("fail: funcionario nao existe");
        }

        return funcionarios.get(nome);
    }

    public void addFuncionario(Funcionario funcionario) {
        funcionarios.put(funcionario.getNome(), funcionario);
    }

    public void rmFuncionario(String nome) throws Exception{
        if(funcionarios.get(nome) == null) {
            throw new Exception("fail: funcionaro nao existe");
        }

        funcionarios.remove(nome);
    }

    //reparte o bonus para todos os funcionarios
    public void setBonus(int bonus) throws Exception{
        if ( this.funcionarios.size() == 0 ) {
            throw new Exception("fail: sem funcionarios");
        }

        int bonusRepartido = bonus / funcionarios.size();
        for(Funcionario f : funcionarios.values()) {
            f.setBonus(bonusRepartido);
        }
    }
}

public class Solver {
    public static void main(String[] arg) {
        UFC ufc = new UFC();

        while (true) {
            String line = input();
            println("$" + line);
            String[] args = line.split(" ");

            try {
                if      (args[0].equals("end"))          { break; }
                else if (args[0].equals("addProf"))      { ufc.addFuncionario(new Professor(args[1], args[2])); }
                else if (args[0].equals("addSta"))       { ufc.addFuncionario(new STA(args[1], (int) number(args[2]))); }
                else if (args[0].equals("addTer"))       { ufc.addFuncionario(new Terceirizado(args[1], (int) number(args[2]), args[3])); }
                else if (args[0].equals("rm"))           { ufc.rmFuncionario(args[1]); }
                else if (args[0].equals("showAll"))      { print(ufc); }
                else if (args[0].equals("show"))         { println(ufc.getFuncionario(args[1])); }
                else if (args[0].equals("addDiaria"))    { ufc.getFuncionario(args[1]).addDiaria(); }
                else if (args[0].equals("setBonus"))     { ufc.setBonus((int) number(args[1])); }
                else                                     { println("fail: comando invalido"); }
            } catch (Exception e) {
                println(e.getMessage());
                // e.printStackTrace(System.out);
            }
        }
    }

    private static Scanner scanner = new Scanner(System.in);
    private static String  input()                { return scanner.nextLine();        }
    private static double  number(String value)   { return Double.parseDouble(value); }
    public  static void    println(Object value)  { System.out.println(value);        }
    public  static void    print(Object value)    { System.out.print(value);          }
}