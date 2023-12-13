package MoodleAutenticaveis;

import java.util.Map;
import java.util.TreeMap;

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
        if (ter instanceof Terceirizado) {
            throw new MsgException("fail: terc nao pode ser cadastrado no sistema");
        }

        Autenticavel user = (FuncionarioAutenticavel) this.getUFC().getFuncionario(nome);

        if (user == null) {
            user = this.getUFC().getAluno(nome);
        }

        if (user == null) {
            throw new MsgException("fail: " + nome + " nao encontrado");
        }
        user.setSenha(senha);
        usuarios.put(nome, user);
    }

    public void rmUsuario(String nome) {
        if (this.usuarios.get(nome) == null) {
            throw new MsgException("fail: usuario " + nome + " nao encontrado");
        }

        this.usuarios.remove(nome);
    }

    public void logar(String nome, String senha) {
        if (this.usuarios.get(nome) == null) {
            throw new MsgException("fail: usuario " + nome + " nao encontrado");
        }
        if (!this.usuarios.get(nome).getSenha().equals(senha)) {
            throw new MsgException("fail: senha invalida");
        }
        this.usuarios.get(nome).logar(senha);
    }

    public void deslogar(String nome) {
        if (this.usuarios.get(nome) == null) {
            throw new MsgException("fail: usuario " + nome + " nao encontrado");
        }
        this.usuarios.get(nome).deslogar();
    }

    public void deslogarTodos() {
        for (Autenticavel a : this.usuarios.values()) {
            a.deslogar();
        }
    }

    public String showUser(String nome) {
        if (this.usuarios.get(nome) == null) {
            throw new MsgException("fail: usuario " + nome + " nao encontrado");
        }

        return this.usuarios.get(nome) + ":" + this.usuarios.get(nome).getSenha() + ":" + this.usuarios.get(nome).getLogado();
    }

    @Override
    public String toString() {
        StringBuilder saida = new StringBuilder();
        for (Autenticavel u : this.usuarios.values()) {
            saida.append(u).append(":").append(u.getSenha()).append(":").append(u.getLogado()).append("\n");
        }
        return saida.toString();
    }
}
