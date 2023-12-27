package MoodleTrem;

import java.util.ArrayList;
import java.util.Collections;

class Vagao {
    private ArrayList<Passageiro> cadeiras;

    public Vagao(int capacidade) {
        this.cadeiras = new ArrayList<>(Collections.nCopies(capacidade, null));
    }

    public String toString() {
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < cadeiras.size(); i++) {
            if (i + 1 == cadeiras.size()) {
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
