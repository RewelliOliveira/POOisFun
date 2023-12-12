package MoodleMatricula;
import java.util.*;

class Aluno {
    private final String id;
    private final Map<String, Disciplina> disciplinas = new TreeMap<>();

    public Aluno(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void addDisciplina(Disciplina disciplina) {
        disciplinas.put(disciplina.getId(), disciplina);
    }

    public void rmDisciplina(String idDisciplina) {
        disciplinas.remove(idDisciplina);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(" [");

        if(disciplinas.isEmpty()) {
            sb.append("]");
            return sb.toString();
        }

        int i = 0;
        for(Disciplina d : disciplinas.values()) {
            sb.append(d.getId());
            if(i < disciplinas.size() - 1) { sb.append(", "); }
            i++;
        }

        sb.append("]");
        return sb.toString();
    }
}

class Disciplina {
    private final String id;
    private final Map<String, Aluno> alunos = new TreeMap<>();

    public Disciplina(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void addAluno(Aluno aluno) {
        alunos.put(aluno.getId(), aluno);
    }

    public void rmAluno(String idAluno) {
        alunos.remove(idAluno);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(" [");

        if(alunos.isEmpty()) {
            sb.append("]");
            return sb.toString();
        }

        int i = 0;
        for(Aluno a : alunos.values()) {
            sb.append(a.getId());
            if(i < alunos.size() - 1) { sb.append(", "); }
            i++;
        }

        sb.append("]");
        return sb.toString();
    }
}

class Sistema {
    private final Map<String, Disciplina> disciplinas = new TreeMap<>();
    private final Map<String, Aluno> alunos = new TreeMap<>();

    public Sistema() {}

    public void addAluno(String idAluno) {
        alunos.put(idAluno, new Aluno(idAluno));
    }

    public void addDisciplina(String idDisciplina) {
        disciplinas.put(idDisciplina, new Disciplina(idDisciplina));
    }

    public void matricular(String idAluno, String idDisciplina) {
        alunos.get(idAluno).addDisciplina(new Disciplina(idDisciplina));
        disciplinas.get(idDisciplina).addAluno(new Aluno(idAluno));
    }

    public void desmatricular(String idAluno, String idDisciplina) {
        alunos.get(idAluno).rmDisciplina(idDisciplina);
        disciplinas.get(idDisciplina).rmAluno(idAluno);
    }

    public void removerAluno(String idAluno) {
        for(Disciplina d : disciplinas.values())
            d.rmAluno(idAluno);
        alunos.remove(idAluno);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("- alunos\n");

        for(Aluno a : alunos.values()) {
            sb.append(a).append("\n");
        }

        sb.append("- discps\n");
        int i = 0;
        for(Disciplina d : disciplinas.values()) {
            sb.append(d);
            if(i < disciplinas.size() - 1) { sb.append("\n"); }
            i++;
        }

        return sb.toString();
    }
}












public class Solver {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Sistema sistema = new Sistema();

        label:
        while(true) {
            var line = sc.nextLine();
            println("$" + line);
            var ui = line.split(" ");

            switch (ui[0]) {
                case "end":
                    break label;
                case "nwalu":
                    for(int i = 1; i < ui.length; i++)
                        sistema.addAluno(ui[i]);
                    break;
                case "nwdis":
                    for(int i = 1; i < ui.length; i++)
                        sistema.addDisciplina(ui[i]);
                    break;
                case "tie":
                    for(int i = 2; i < ui.length; i++)
                        sistema.matricular(ui[1], ui[i]);
                    break;
                case "untie":
                    for(int i = 2; i < ui.length; i++)
                        sistema.desmatricular(ui[1], ui[i]);
                    break;
                case "rmalu":
                    sistema.removerAluno(ui[1]);
                    break;
                case "show":
                    println(sistema);
                    break;
                default:
                    System.out.println("fail: invalid command");
                    break;
            }
        }
    }

    public static void println(Object obj) {
        System.out.println(obj);
    }
}