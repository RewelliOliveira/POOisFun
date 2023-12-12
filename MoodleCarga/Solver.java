package MoodleCarga;
import java.util.*;

class Carga implements Pass {
    private String id;
    private float peso;

    public Carga(String id, float peso) {
        this.id   = id;
        this.peso = peso;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Carga )) return false;
        Carga carga = (Carga) object;
        return Objects.equals(getId(), carga.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public float getPeso() {
        return peso;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return getId() + ":" + String.format("%.1f", peso);
    }
}
enum Direcao {
    IN,
    OUT;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
class Movimento {
    private Pass pass;
    private Direcao dir;

    public Movimento(Pass pass, Direcao dir) {
        this.pass = pass;
        this.dir  = dir;
    }

    public Pass getPass() {
        return pass;
    }

    public void setPass(Pass pass) {
        this.pass = pass;
    }

    public Direcao getDir() {
        return dir;
    }

    public void setDir(Direcao dir) {
        this.dir = dir;
    }

    @Override
    public String toString() {
        if(pass instanceof Pessoa)
            return pass.getId() + " " + dir.toString();
        else
            return pass.getId() + ":" + String.format("%.1f", ((Carga) pass).getPeso()) + " " + dir.toString();
    }
}
class MsgException extends Exception {
    public MsgException(String msg) {
        super(msg);
    }
}
interface Pass {
    String getId();
}


class Pessoa implements Pass {
    private String id;

    public Pessoa(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Pessoa)) return false;
        Pessoa pessoa = (Pessoa) object;
        return Objects.equals(getId(), pessoa.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return getId();
    }
}

class Registro {
    private List<Pass> repPassageiros = new ArrayList<>();
    private List<Movimento> movimentacao = new ArrayList<>();

    public void cadastrar(Pass pass) {
        if(!repPassageiros.contains(pass))
            repPassageiros.add(pass);
    }

    public void movimentar(Movimento mov) {
        movimentacao.add(mov);
    }

    public void sort() {
        repPassageiros.sort(Comparator.comparing(Pass::getId));
    }

    public void showCadastro() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < repPassageiros.size(); i++) {
            sb.append(repPassageiros.get(i));
            if(i < repPassageiros.size() - 1) { sb.append("\n"); }
        }
        System.out.println(sb.toString());
    }

    public void showMovimentacao() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < movimentacao.size(); i++) {
            sb.append(movimentacao.get(i));
            if(i < movimentacao.size() - 1) { sb.append("\n"); }
        }
        System.out.println(sb.toString());
    }
}

class Trem {
    private int maxVagoes;
    private List<Vagao> vagoes = new ArrayList<>();
    private Registro reg = new Registro();

    public Trem(int maxVagoes) {
        this.maxVagoes = maxVagoes;
    }

    public void addVagao(Vagao vagao) throws MsgException {
        if(vagoes.size() == maxVagoes)
            throw new MsgException("fail: limite de vagões atingido");
        vagoes.add(vagao);
    }

    public Registro getReg() {
        return reg;
    }

    public boolean isFull() {
        long size = vagoes.stream()
                .filter(v -> v instanceof VagaoP).count();
        long aux  = vagoes.stream()
                .filter(v -> v instanceof VagaoP && !((VagaoP) v).hasVacancy()).count();
        return size == aux;
    }

    public boolean passengerExist(Pass pass) {
        long i = vagoes.stream()
                .filter(v -> v instanceof VagaoP && v.exists(pass)).count();
        return i > 0;
    }

    public void embarcar(Pass pass) throws MsgException {
        reg.cadastrar(pass);
        reg.sort();

        if(pass instanceof Pessoa) {
            if(isFull())
                throw new MsgException("fail: trem lotado");
            for(Vagao v : vagoes) {
                if(v.exists(pass))
                    throw new MsgException("fail: " + pass.getId() + " já está no trem");

                if(v instanceof VagaoP && ((VagaoP) v).hasVacancy()) {
                    v.embarcar(pass);
                    reg.movimentar(new Movimento(pass, Direcao.IN));
                    return;
                }
            }
        }
        else {
            for(Vagao v : vagoes) {
                if(v instanceof VagaoC) {
                    v.embarcar(pass);
                    reg.movimentar(new Movimento(pass, Direcao.IN));
                }
            }
        }
    }

    public void desembarcar(String idPass) throws MsgException {
        int i = 0;
        for(Vagao v : vagoes) {
            if (v instanceof VagaoP) {
                for (Pass p : v.getElementos()) {
                    if (p != null) {
                        if (p.getId().equals(idPass)) {
                            i++;
                            v.desembarcar(idPass);
                            reg.movimentar(new Movimento(new Pessoa(idPass), Direcao.OUT));
                        }
                    }
                }
            }
            else if (v.exists(new Carga(idPass, 0f))){
                Carga aux = (Carga) ((VagaoC) v).getPass(idPass);
                v.desembarcar(idPass);
                reg.movimentar(new Movimento(aux, Direcao.OUT));
                i++;
            }
        }
        if(i == 0)
            throw new MsgException("fail: " + idPass + " nao esta no trem");
    }

    public boolean exists(Pass pass) {
        int i = 0;
        for(Vagao v : vagoes) {
            if(v instanceof VagaoP && v.exists(pass))
                i++;
        }
        return i > 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Trem ");

        for(Vagao v : vagoes) { sb.append(v); }
        return sb.toString();
    }
}
interface Vagao {
    List<Pass> getElementos();
    void embarcar(Pass pass) throws MsgException;
    void desembarcar(String idPass);
    boolean exists(Pass pass);
}

class VagaoC implements Vagao {
    private float cargaMax;
    private float cargaAtual = 0f;
    private List<Pass> elementos = new ArrayList<>();

    public VagaoC(float cargaMax) {
        this.cargaMax = cargaMax;
    }

    @Override
    public List<Pass> getElementos() {
        return elementos;
    }

    public Pass getPass(String idPass) {
        for(Pass p : elementos) {
            if(p.getId().equals(idPass))
                return p;
        }
        return null;
    }

    @Override
    public void embarcar(Pass pass) throws MsgException {
        if(cargaAtual + ((Carga) pass).getPeso() < cargaMax) {
            elementos.add(pass);
            cargaAtual += ((Carga) pass).getPeso();
        }
        else
            throw new MsgException("fail: trem lotado");
    }

    @Override
    public void desembarcar(String idPass) {
        for(Pass p : elementos) {
            if(p.getId().equals(idPass))
                cargaAtual -= ((Carga) p).getPeso();
        }
        elementos.removeIf(p -> p != null && p.getId().equals(idPass));
    }

    @Override
    public boolean exists(Pass pass) {
        return elementos.contains(pass);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("( ");

        if(elementos.isEmpty()) {
            sb.append(String.format("_%.1f )", cargaMax));
            return sb.toString();
        }

        for(Pass p: elementos)
            sb.append(p).append(" ");

        sb.append(String.format("_%.1f )", cargaMax - cargaAtual));
        return sb.toString();
    }
}
 class VagaoP implements Vagao {
    private int capacidade;
    private Pass[] elementos;

    public VagaoP(int capacidade) {
        this.capacidade = capacidade;
        elementos = new Pessoa[capacidade];
    }

    public boolean hasVacancy() {
        for(Pass p : elementos) {
            if(p == null)
                return true;
        }
        return false;
    }

    @Override
    public List<Pass> getElementos() {
        return Arrays.asList(elementos);
    }

    @Override
    public void embarcar(Pass pass) {
        for(int i = 0; i < elementos.length; i++) {
            if(elementos[i] == null) {
                elementos[i] = pass;
                return;
            }
        }
    }

    @Override
    public void desembarcar(String idPass) {
        for(int i = 0; i < elementos.length; i++) {
            if(elementos[i] != null && elementos[i].getId().equals(idPass))
                elementos[i] = null;
        }
    }

    @Override
    public boolean exists(Pass pass) {
        for (Pass e : elementos) {
            if (e != null && e.equals(pass))
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");

        for (Pass p : elementos) {
            if (p == null)
                sb.append("- ");
            else
                sb.append(p).append(" ");
        }
        sb.append("]");
        return sb.toString();
    }
}


public class Solver {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        Trem trem = new Trem(0);

        label:
        while(true) {
            var line = sc.nextLine();
            println("$" + line);
            var ui = line.split(" ");

            try {
                switch (ui[0]) {
                    case "end":
                        break label;
                    case "init":
                        trem = new Trem((int) converter(ui[1]));
                        break;
                    case "la":
                        println(trem);
                        break;
                    case "nwvp":
                        trem.addVagao(new VagaoP((int) converter(ui[1])));
                        break;
                    case "nwvc":
                        trem.addVagao(new VagaoC((float) converter(ui[1])));
                        break;
                    case "addp":
                        trem.embarcar(new Pessoa(ui[1]));
                        break;
                    case "addc":
                        trem.embarcar(new Carga(ui[1], (float) converter(ui[2])));
                        break;
                    case "sair":
                        trem.desembarcar(ui[1]);
                        break;
                    case "cadastro":
                        trem.getReg().showCadastro();
                        break;
                    case "movimentacao":
                        trem.getReg().showMovimentacao();
                        break;
                    default:
                        println("fail: invalid command");
                        break;
                }
            } catch (Exception e) {
                println(e.getMessage());
            }
        }
    }

    public static void println(Object obj) { System.out.println(obj); }
    public static double converter(String str) { return Double.parseDouble(str); }
}
