package MoodleCarga;

import java.util.ArrayList;
import java.util.List;

class Trem {
    private int maxVagoes;
    private List<Vagao> vagoes = new ArrayList<>();
    private Registro reg = new Registro();

    public Trem(int maxVagoes) {
        this.maxVagoes = maxVagoes;
    }

    public void addVagao(Vagao vagao) throws MsgException {
        if (vagoes.size() == maxVagoes)
            throw new MsgException("fail: limite de vagões atingido");
        vagoes.add(vagao);
    }

    public Registro getReg() {
        return reg;
    }

    public boolean isFull() {
        long size = vagoes.stream()
                .filter(v -> v instanceof VagaoP).count();
        long aux = vagoes.stream()
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

        if (pass instanceof Pessoa) {
            if (isFull())
                throw new MsgException("fail: trem lotado");
            for (Vagao v : vagoes) {
                if (v.exists(pass))
                    throw new MsgException("fail: " + pass.getId() + " já está no trem");

                if (v instanceof VagaoP && ((VagaoP) v).hasVacancy()) {
                    v.embarcar(pass);
                    reg.movimentar(new Movimento(pass, Direcao.IN));
                    return;
                }
            }
        } else {
            for (Vagao v : vagoes) {
                if (v instanceof VagaoC) {
                    v.embarcar(pass);
                    reg.movimentar(new Movimento(pass, Direcao.IN));
                }
            }
        }
    }

    public void desembarcar(String idPass) throws MsgException {
        int i = 0;
        for (Vagao v : vagoes) {
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
            } else if (v.exists(new Carga(idPass, 0f))) {
                Carga aux = (Carga) ((VagaoC) v).getPass(idPass);
                v.desembarcar(idPass);
                reg.movimentar(new Movimento(aux, Direcao.OUT));
                i++;
            }
        }
        if (i == 0)
            throw new MsgException("fail: " + idPass + " nao esta no trem");
    }

    public boolean exists(Pass pass) {
        int i = 0;
        for (Vagao v : vagoes) {
            if (v instanceof VagaoP && v.exists(pass))
                i++;
        }
        return i > 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Trem ");

        for (Vagao v : vagoes) {
            sb.append(v);
        }
        return sb.toString();
    }
}
