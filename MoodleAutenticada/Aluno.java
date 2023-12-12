package MoodleAutenticada;

public class Aluno {
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
