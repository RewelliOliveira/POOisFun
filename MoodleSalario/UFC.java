package MoodleSalario;

import java.util.Map;
import java.util.TreeMap;

class UFC {
    private final Map<String, Funcionario> funcionarios;

    public UFC() {
        this.funcionarios = new TreeMap<>();
    }

    public void addFuncionario(Funcionario funcionario) {
        this.funcionarios.put(funcionario.getNome(), funcionario);
    }

    public Funcionario getFuncionario(String nome) {
        return this.funcionarios.get(nome);
    }

    public void rmFuncionario(String nome) {
        this.funcionarios.remove(nome);
    }

    public void setBonus(int bonus) {
        for (Funcionario funcionario : funcionarios.values()) {
            funcionario.setBonus(bonus / funcionarios.size());
        }
    }

    public String toString() {
        StringBuilder saida = new StringBuilder();

        for (Funcionario f : funcionarios.values()) {
            saida.append(f).append("\n");
        }

        return saida.toString();
    }

}
