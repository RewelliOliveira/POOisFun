
package MoodleTrem;
import java.util.*;

class Passageiro {
    private String id;

    public Passageiro(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
}

class Vagao {
    private ArrayList<Passageiro> cadeiras;

    public Vagao(int capacidade) {
        this.cadeiras = new ArrayList<>(Collections.nCopies(capacidade, null));
    }

    public String toString() {
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < cadeiras.size(); i++) {
            if (i+1 == cadeiras.size()) {
                output.append((cadeiras.get(i)) != null ? cadeiras.get(i).getId() : "-");
            } else {
                output.append((cadeiras.get(i)) != null ? cadeiras.get(i).getId() + " " : "- ");
            }
        }

        return output.toString();
    }

    public boolean embarcar(Passageiro pass) {
        for (int i = 0; i < cadeiras.size(); i++) {
            if (cadeiras.get(i) == null) {

                cadeiras.set(i, pass);
                return true;
            }
        }

        return false;
    }
    public void desembarcar(String passId) {
        for (int i = 0; i < cadeiras.size(); i++) {
            if (cadeiras.get(i) == null) {
                continue;
            }

            if (cadeiras.get(i).getId().equals(passId)) {
                Solver.rg.movimentar(new Movimento(cadeiras.get(i), Direcao.OUT));
                cadeiras.set(i, null);
            }
        }
    }
    public boolean exists(String passId) {
        for (Passageiro pass : cadeiras) {
            if (pass == null) {
                continue;
            }

            if (pass.getId().equals(passId)) {
                return true;
            }
        }

        return false;
    }
}

class Trem {
    private int maxVagoes;
    private ArrayList<Vagao> vagoes;

    public Trem(int maxVagoes) {
        this.maxVagoes = maxVagoes;
        this.vagoes = new ArrayList<>();
    }

    public String toString() {
        StringBuilder output = new StringBuilder("Trem ");

        for (Vagao vag : vagoes) {
            output.append("[ ").append(vag.toString()).append(" ]");
        }

        return output.toString();
    }

    public void addVagao(Vagao vagao) throws Exception {
        if (vagoes.size() == maxVagoes) {
            throw new Exception("fail: limite de vagões atingido");
        }

        vagoes.add(vagao);
    }
    public void embarcar(Passageiro pass) throws Exception {
        if (exists(pass.getId())) {
            throw new Exception("fail: " + pass.getId() + " já está no trem");
        }
        Solver.rg.cadastrar(pass);

        for (Vagao vag : vagoes) {
            if (vag.embarcar(pass)) {
                Solver.rg.movimentar(new Movimento(pass, Direcao.IN));
                return;
            }
        }

        throw new Exception("fail: trem lotado");
    }
    public void desembarcar(String passId) throws Exception {
        if (!exists(passId)) {
            throw new Exception("fail: " + passId + " nao esta no trem");
        }

        for (Vagao vag : vagoes) {
            vag.desembarcar(passId);
        }
    }
    public boolean exists(String passId) {

        for (Vagao vag : vagoes) {
            if (vag.exists(passId)) {
                return true;
            }
        }

        return false;
    }
}

enum Direcao {
    IN,
    OUT;
}
class Movimento {
    private Passageiro pass;
    private Direcao direcao;

    public Movimento(Passageiro pass, Direcao direcao) {
        this.pass = pass;
        this.direcao = direcao;
    }

    public String toString() {
        return pass.getId() + " " + direcao.toString().toLowerCase();
    }
}

class Registro {
    private ArrayList<Passageiro> rPass;
    private ArrayList<Movimento> movimentacao;


    public Registro() {
        rPass = new ArrayList<>();
        movimentacao = new ArrayList<>();
    }

    public void cadastrar(Passageiro pass) {
        for (Passageiro passObj : rPass) {
            if (passObj.getId().equals(pass.getId())) {
                return;
            }
        }

        rPass.add(pass);
    }
    public void movimentar(Movimento mov) {
        movimentacao.add(mov);
    }

    public String getCadastro() {
        rPass.sort(new Comparator<Passageiro>() {
            @Override
            public int compare(Passageiro pass1, Passageiro pass2) {
                return pass1.getId().compareTo(pass2.getId());
            }
        });

        String output = "";

        for (Passageiro pass : rPass) {
            output += pass.getId() + "\n";
        }

        return output;
    }
    public String getMovimentos() {
        String output = "";

        for (Movimento mov : movimentacao) {
            output += mov.toString() + "\n";
        }

        return output;
    }
}

public class Solver {
    static Registro rg = new Registro();
    public static void main(String[] arg) {
        Scanner scanner = new Scanner(System.in);
        Trem train = new Trem(0);

        label:
        while (true) {
            String line = scanner.nextLine();
            print("$" + line);

            String[] ui = line.split(" ");

            try {
                switch (ui[0]) {
                    case "init":
                        train = new Trem(parseInt(ui[1]));
                        break;
                    case "nwvag":
                        train.addVagao(new Vagao(parseInt(ui[1])));
                        break;
                    case "la":
                        print(train.toString());
                        break;
                    case "show":
                        print(train.toString());
                        break;
                    case "entrar":
                        train.embarcar(new Passageiro(ui[1]));
                        break;
                    case "sair":
                        train.desembarcar(ui[1]);
                        break;
                    case "cadastro":
                        System.out.print(rg.getCadastro());
                        break;
                    case "movimentacao":
                        System.out.print(rg.getMovimentos());
                        break;
                    case "end":
                        break label;
                    default:
                        print("fail: comando invalido");
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public static void print(String str) { System.out.println(str); }
    public static int parseInt(String value) { return Integer.parseInt(value); }
}
