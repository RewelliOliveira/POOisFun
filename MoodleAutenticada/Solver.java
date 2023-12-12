package MoodleAutenticada;
import java.util.*;


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

    static class STA extends FuncionarioAutenticavel {
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
}