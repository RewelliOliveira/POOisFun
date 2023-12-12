package MoodleTrem2;
import java.util.*;

class Movimento {
    String idPass;
    Direcao dir;

    public Movimento(String idPass, Direcao dir) {
        this.idPass = idPass;
        this.dir = dir;
    }
    @Override
    public String toString() {
        return this.idPass + " " + this.dir;
    }
}

class Cadastro {
    private ArrayList<Passageiro> cadastro;
    private ArrayList<Movimento> movimentacao;

    public Cadastro(){
        this.cadastro = new ArrayList<>();
        this.movimentacao = new ArrayList<>();
    }

    public ArrayList<Passageiro> getCadastro() {
        return cadastro;
    }

    public ArrayList<Movimento> getMovimentacao() {
        return movimentacao;
    }

    public void cadastrar(Passageiro pass){
        if(!exists(pass.getIdPass())){
            this.cadastro.add(pass);
        }
    }
    public void addMov(Movimento mov){
        this.movimentacao.add(mov);
    }

    private boolean exists(String idPass){
        for(Passageiro pass : this.cadastro){
            if(pass.getIdPass().equals(idPass)){
                return true; // idPass encontrado
            }
        }
        return false; // idPass não encontrado
    }

    public String showCad(){
        Collections.sort(this.cadastro, Comparator.comparing(Passageiro :: getIdPass));
        String s = "";

        for(Passageiro pass : cadastro){
            s += pass.getIdPass();
            s += "\n";
        }
        return s;
    }

    public String showMov(){
        String s = "";
        for(Movimento mov : this.movimentacao){
            s += mov;
            s += "\n";
        }
        return s;
    }
}

enum Direcao {
    IN, OUT;
    public String toString(){
        return this.name().toLowerCase();
    }
}

class Trem {
    private int maxVagoes;
    private ArrayList<Vagao> vagoes;
    private Cadastro registro;

    public Trem(int maxVagoes) {
        this.maxVagoes = maxVagoes;
        this.vagoes = new ArrayList<Vagao>();
        this.registro = new Cadastro();
    }
    public Cadastro getRegistro() {
        return registro;
    }

    public boolean addVagao(Vagao vagao) throws Exception{
        if(vagoes.size() == maxVagoes){
            throw new Exception("fail: limite de vagões atingido");
        }
        this.vagoes.add(vagao);
        return true;
    }

    public boolean embarcar(Passageiro pass) throws Exception{
        if(this.exists(pass.getIdPass())){
            throw new Exception("fail: " + pass + " já está no trem");
        }

        this.registro.cadastrar(pass);
        for(Vagao vagao : vagoes){
            if(vagao.embarcar(pass)){
                this.registro.addMov(new Movimento(pass.getIdPass(), Direcao.IN));
                return true;
            }
        }
        throw new Exception("fail: trem lotado");
    }

    public void desembarcar(String idPass) throws Exception{
        if (!exists(idPass)) {
            throw new Exception("fail: " + idPass + " nao esta no trem");
        }

        for(Vagao vagao : vagoes){
            if(vagao.desembarcar(idPass)){
                this.registro.addMov(new Movimento(idPass, Direcao.OUT));
                break;
            }
        }
    }

    private boolean exists(String idPass){
        for(Vagao vagao : vagoes){
            if(vagao.exists(idPass)){
                return true; // idPass encontrado
            }
        }
        return false; //idPass não encontrada
    }

    public Passageiro search(String idPass){
        return null;
    }

    @Override
    public String toString() {
        String s = "Trem ";
        for(Vagao vagao : this.vagoes){
            s += vagao;
        }
        return s;
    }
}

class Vagao {
    private ArrayList<Passageiro> cadeiras;
    private int capacidade;

    public Vagao(int capacidade) {
        this.capacidade = capacidade;
        this.cadeiras = new ArrayList<Passageiro>();
        for(int i = 0; i < this.capacidade; i++){
            this.cadeiras.add(null);
        }
    }

    public boolean embarcar(Passageiro pass){
        for(int i = 0; i < this.capacidade; i++){
            if(cadeiras.get(i) == null){
                cadeiras.set(i, pass);
                return true;
            }
        }
        return false;
    }

    public boolean desembarcar(String idPass){
        int k = search(idPass);
        if(k != -1){
            this.cadeiras.set(k, null);
            return true; // idPass encontrado
        }
        return false;
    }

    public boolean exists(String idPass){
        for(Passageiro pass : this.cadeiras){
            if(pass != null && pass.getIdPass().equals(idPass)){
                return true; // idPass encontrado
            }
        }
        return false; // idPass não encontrado
    }

    private int search(String idPass){
        for(int i = 0; i < this.cadeiras.size(); i++){
            if(this.cadeiras.get(i) != null && this.cadeiras.get(i).getIdPass().equals(idPass)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        String s = "[ ";
        for(Passageiro pass : this.cadeiras){
            if(pass != null){
                s += pass + " ";
            }else{
                s += "- ";
            }
        }
        s += "]";
        return s;
    }
}

class Passageiro {
    private String idPass;

    public Passageiro(String idPass) {
        this.idPass = idPass;
    }

    public String getIdPass() {
        return idPass;
    }

    @Override
    public String toString() {
        return idPass;
    }

}

public class Solver {
    public static void main(String[] args){

        Trem trem = new Trem(0);

        while (true) {
            String line = scanner.nextLine();
            System.out.println("$" + line);
            String[] cmd = line.split(" ");

            try{
                if(cmd[0].equals("end")){ break;}
                else if(cmd[0].equals("la")){ System.out.println(trem);}
                else if(cmd[0].equals("init")){ trem = new Trem(Integer.parseInt(cmd[1]));}
                else if(cmd[0].equals("nwvag")){ trem.addVagao(new Vagao (Integer.parseInt(cmd[1])));}
                else if(cmd[0].equals("entrar")){ trem.embarcar(new Passageiro(cmd[1]));}
                else if(cmd[0].equals("sair")){ trem.desembarcar(cmd[1]);}
                else if(cmd[0].equals("show")){ System.out.println(trem);}
                else if(cmd[0].equals("cadastro")){ System.out.print(trem.getRegistro().showCad());}
                else if(cmd[0].equals("movimentacao")){ System.out.print(trem.getRegistro().showMov());}
                else{ System.out.println("comando inválido!");}

            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
    private static Scanner scanner = new Scanner (System.in);

}
