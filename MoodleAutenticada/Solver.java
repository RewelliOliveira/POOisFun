package MoodleAutenticada;
import java.util.*;

class MsgException extends RuntimeException {
    public MsgException(String message) {
        super(message);
    }
}

abstract class Funcionario {
    protected String nome;
    protected int bonus;
    protected int diarias;
    protected int maxDiarias;

    public Funcionario(String nome) {
        this.nome = nome;
        this.bonus = 0;
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
    public void addDiaria() {
        if ( this.diarias >= this.maxDiarias ) {
            throw new MsgException("fail: limite de diarias atingido");
        }

        this.diarias ++;
    }

    //retorna bonus + diarias * 100
    public int getSalario() {
        return this.bonus + this.diarias * 100;
    }

}

class FuncionarioAutenticavel extends Funcionario implements Autenticavel {
    private String senha;
    private boolean logado;

    public FuncionarioAutenticavel(String nome) {
        super(nome);
        this.senha = "indefinida";
        this.logado = false;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getSenha() {
        return this.senha;
    }
    public void setLogado(boolean logado) {
        this.logado = logado;
    }
    public String getLogado() {
        return this.logado ? "online" : "offline";
    }

    public void logar( String senha ) {
        setLogado(true);
        setSenha(senha);
    }
    public void deslogar() {
        setLogado(false);
    }
}

class Professor extends FuncionarioAutenticavel {
    protected String classe;

    //inicializa classe e muda maxDiarias para 2
    public Professor(String nome, String classe) {
        super( nome );
        this.classe = classe;
        this.maxDiarias = 2;
    }

    public String getClasse() {
        return this.classe;
    }

    public int getValorClasse() {
        switch (this.getClasse()) {
            case "A":
                return 3000;
            case "B":
                return 5000;
            case "C":
                return 7000;
            case "D":
                return 9000;
            case "E":
                return 11000;
            default:
                return 0;
        }
    }

    //lógica do salário do professor
    //usa o super.getSalario() para pegar bonus e diarias
    @Override
    public int getSalario() {
        return super.getSalario() + getValorClasse();
    }

    @Override
    public String toString() {
        return "prof:" + this.getNome() + ":" + this.getClasse() + ":" + this.getSalario();
    }
}

class STA extends FuncionarioAutenticavel {
    protected int nivel;

    //inicializa nivel e muda maxDiarias para 1
    public STA(String nome, int nivel) {
        super( nome );
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
        return super.getSalario() + 3000 + 300 * this.getNivel();
    }

    @Override
    public String toString() {
        return "sta:" + this.getNome() + ":" + this.getNivel() + ":" + this.getSalario();
    }
}

class Terceirizado extends Funcionario {
    protected int horas;
    protected boolean isSalubre;

    public Terceirizado(String nome, int horas, String isSalubre) {
        super( nome );
        this.horas = horas;
        this.isSalubre = isSalubre.equals("sim");
    }

    public int getHoras() {
        return this.horas;
    }

    public String getIsSalubre() {
        return this.isSalubre ? "sim" : "nao";
    }

    //lance uma MsgException com um texto diferente
    @Override
    public void addDiaria() {
        throw new MsgException("fail: terc nao pode receber diaria");
    }

    //lógica do salário do terceirizado
    //usa o super.getSalario() para pegar bonus e diarias
    @Override
    public int getSalario() {
        return super.getSalario() + 4 * this.getHoras() + (this.isSalubre ? 500 : 0);
    }

    @Override
    public String toString() {
        return "ter:" + this.getNome() + ":" + this.getHoras() + ":" + this.getIsSalubre() + ":" + this.getSalario();
    }
}

class Aluno implements Autenticavel {
    private String nome;
    private String curso;
    private int bolsa;

    private String senha;
    private boolean logado;

    public Aluno(String nome, String curso, int bolsa) {
        //...
        this.bolsa = bolsa;
        this.curso = curso;
        this.nome = nome;
        this.senha = "indefinida";
        this.logado = false;
    }

    public String getNome() {
        return this.nome;
    }
    public String getCurso() {
        return this.curso;
    }
    public int getBolsa() {
        return this.bolsa;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getSenha() {
        return this.senha;
    }
    public void setLogado(boolean logado) {
        this.logado = logado;
    }
    public String getLogado() {
        return this.logado ? "online" : "offline";
    }

    public void logar( String senha ) {
        setLogado(true);
        setSenha(senha);
    }
    public void deslogar() {
        setLogado(false);
    }

    @Override
    public String toString() {
        return "alu:" + this.getNome() + ":" + this.getCurso() + ":" + this.getBolsa();
    }
}

class UFC {
    private Map<String, Funcionario> funcionarios;
    private Map<String, Aluno> alunos;

    public UFC() {
        this.funcionarios = new TreeMap<String, Funcionario>();
        this.alunos = new TreeMap<String, Aluno>();
    }

    @Override
    public String toString() {
        String s = "";
        for ( Funcionario f : this.funcionarios.values() ) {
            s += f + "\n";
        }
        for ( Aluno a : this.alunos.values() ) {
            s += a + "\n";
        }
        return s;
    }

    public Funcionario getFuncionario(String nome) {
        return this.funcionarios.get( nome );
    }

    public void addFuncionario(Funcionario funcionario) {
        this.funcionarios.put( funcionario.getNome(), funcionario );
    }

    public void rmFuncionario(String nome) {
        this.funcionarios.remove( nome );
    }

    //reparte o bonus para todos os funcionarios
    public void setBonus(int bonus) {
        if (this.funcionarios.isEmpty()) {
            throw new MsgException("fail: sem funcionarios");
        }

        int eachBonus = bonus / this.funcionarios.size();
        for ( Funcionario f : this.funcionarios.values() ) {
            f.setBonus( eachBonus );
        }
    }

    public Aluno getAluno(String nome) {
        return this.alunos.get(nome);
    }

    public void addAluno(Aluno aluno) {
        alunos.put(aluno.getNome(), aluno);
    }

    public void rmAluno(String nome) {
        alunos.remove(nome);
    }
}

interface Autenticavel {
    public void logar( String senha );
    public void deslogar();

    public String getNome();
    public void setSenha(String senha);
    public String getSenha();
    public void setLogado(boolean logado);
    public String getLogado();
}

class Sistema {
    private UFC ufc;
    private Map<String, Autenticavel> usuarios;

    public Sistema() {
        this.ufc = new UFC();
        this.usuarios = new TreeMap<String, Autenticavel>();
    }

    public UFC getUFC() {
        return this.ufc;
    }

    public Autenticavel getUsuario(String nome) {
        return usuarios.get(nome);
    }

    public void addUsuario(String nome, String senha) {
        Funcionario ter = this.getUFC().getFuncionario(nome);
        if(ter instanceof Terceirizado) {
            throw new MsgException("fail: terc nao pode ser cadastrado no sistema");
        }

        Autenticavel user = (FuncionarioAutenticavel) this.getUFC().getFuncionario(nome);

        if(user == null) {
            user = this.getUFC().getAluno(nome);
        }

        if(user == null){
            throw new MsgException("fail: " + nome + " nao encontrado");
        }
        user.setSenha(senha);
        usuarios.put(nome, user);
    }

    public void rmUsuario(String nome) {
        if(this.usuarios.get(nome) == null){
            throw new MsgException("fail: usuario " + nome + " nao encontrado");
        }

        this.usuarios.remove(nome);
    }

    public void logar(String nome, String senha) {
        if(this.usuarios.get(nome) == null){
            throw new MsgException("fail: usuario " + nome + " nao encontrado");
        }
        if(!this.usuarios.get(nome).getSenha().equals(senha)){
            throw new MsgException("fail: senha invalida");
        }
        this.usuarios.get(nome).logar(senha);
    }
    public void deslogar(String nome) {
        if(this.usuarios.get(nome) == null){
            throw new MsgException("fail: usuario " + nome + " nao encontrado");
        }
        this.usuarios.get(nome).deslogar();
    }
    public void deslogarTodos() {
        for(Autenticavel a : this.usuarios.values()){
            a.deslogar();
        }
    }
    public String showUser( String nome ) {
        if(this.usuarios.get(nome) == null){
            throw new MsgException("fail: usuario " + nome + " nao encontrado");
        }

        return this.usuarios.get(nome) + ":" + this.usuarios.get(nome).getSenha() + ":" + this.usuarios.get(nome).getLogado();
    }

    @Override
    public String toString() {
        StringBuilder saida = new StringBuilder();
        for ( Autenticavel u : this.usuarios.values() ) {
            saida.append(u).append(":").append(u.getSenha()).append(":").append(u.getLogado()).append("\n");
        }
        return saida.toString();
    }
}

public class Solver {
    public static void main(String[] arg) {
        Sistema sis = new Sistema();

        label:
        while (true) {
            String line = input();
            println("$" + line);
            String[] args = line.split(" ");

            try {
                switch (args[0]) {
                    case "end":
                        break label;
                    case "addProf":
                        sis.getUFC().addFuncionario(new Professor(args[1], args[2]));
                        break;
                    case "addSta":
                        sis.getUFC().addFuncionario(new STA(args[1], (int) number(args[2])));
                        break;
                    case "addTer":
                        sis.getUFC().addFuncionario(new Terceirizado(args[1], (int) number(args[2]), args[3]));
                        break;
                    case "rm":
                        sis.getUFC().rmFuncionario(args[1]);
                        break;
                    case "rmFunc":
                        sis.getUFC().rmFuncionario(args[1]);
                        break;
                    case "showAll":
                        print(sis.getUFC());
                        break;
                    case "show":
                        println(sis.getUFC().getFuncionario(args[1]));
                        break;
                    case "showFunc":
                        println(sis.getUFC().getFuncionario(args[1]));
                        break;
                    case "addDiaria":
                        sis.getUFC().getFuncionario(args[1]).addDiaria();
                        break;
                    case "setBonus":
                        sis.getUFC().setBonus((int) number(args[1]));
                        break;
                    case "addAlu":
                        sis.getUFC().addAluno(new Aluno(args[1], args[2], (int) number(args[3])));
                        break;
                    case "rmAlu":
                        sis.getUFC().rmAluno(args[1]);
                        break;
                    case "showAlu":
                        println(sis.getUFC().getAluno(args[1]));
                        break;
                    case "addUser":
                        sis.addUsuario(args[1], args[2]);
                        break;
                    case "rmUser":
                        sis.rmUsuario(args[1]);
                        break;
                    case "showUser":
                        println(sis.showUser(args[1]));
                        break;
                    case "showAllUsers":
                        print(sis);
                        break;
                    case "logar":
                        sis.logar(args[1], args[2]);
                        break;
                    case "deslogar":
                        sis.deslogar(args[1]);
                        break;
                    case "deslogarTodos":
                        sis.deslogarTodos();
                        break;
                    default:
                        println("fail: comando invalido");
                        break;
                }
            } catch (MsgException me) {
                println(me.getMessage());
                // e.printStackTrace(System.out);
            }
        }
    }

    private static Scanner scanner = new Scanner(System.in);
    private static String  input()                { return scanner.nextLine();        }
    private static double  number(String value)   { return Double.parseDouble(value); }
    public  static void    println(Object value)  { System.out.println(value);        }
    public  static void    print(Object value)    { System.out.print(value);}
}