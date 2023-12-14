package MoodleCinema;

import java.util.ArrayList;
import java.util.List;

class Sala {
    private List<Client> cadeiras;

    private boolean verificarIndice(int ind) {
        if (ind >= 0 && ind < this.cadeiras.size()) return true;
        return false;
    }

    private int procurar(String nome) {
        for (int i = 0; i < this.cadeiras.size(); i++) {
            if (this.cadeiras.get(i) != null) {
                if (this.cadeiras.get(i).getId().equals(nome)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public Sala(int capacidade) {
        this.cadeiras = new ArrayList<Client>();
        for (int i = 0; i < capacidade; i++) {
            this.cadeiras.add(null);
        }

    }

    public List<Client> getCadeiras() {
        return null;
    }

    public boolean reservar(String id, String fone, int ind) {

        if (!this.verificarIndice(ind)) {
            System.out.println("fail: cadeira nao existe");
            return false;
        }
        if (this.cadeiras.get(ind) != null) {
            System.out.println("fail: cadeira esta oculpada");
            return false;
        }
        if (this.procurar(id) != -1) {
            System.out.println("fail: cliente ja esta no cinema");
            return false;
        }

        Client c = new Client(id, fone);
        this.cadeiras.set(ind, c);
        return true;
    }

    public void cancelar(String id) {
        int ind = this.procurar(id);
        if (ind == -1) {
            System.out.println("fail: cliente nao cancelou");
            return;
        }
        this.cadeiras.set(ind, null);
    }

    public String toString() {
        String saida = "[";
        int cont = 0;
        for (Client cliente : cadeiras) {
            if (cliente == null)
                saida += "- ";
            else
                saida += cliente + " ";
        }

        if (cont != this.cadeiras.size() - 1) {
            saida += " ";
        }
        cont++;
        return saida + "]";
    }
}
