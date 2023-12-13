package MoodleAutenticaveis;

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

    public void logar(String senha) {
        setLogado(true);
        setSenha(senha);
    }

    public void deslogar() {
        setLogado(false);
    }
}
