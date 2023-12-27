package MoodleTrem;

import java.util.ArrayList;
import java.util.Comparator;

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
