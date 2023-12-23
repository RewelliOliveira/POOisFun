package MoodleTrem;

class Movimento {
    private Passageiro pass;
    private Direcao direcao;

    public Movimento(Passageiro pass, Direcao direcao) {
        this.pass = pass;
        this.direcao = direcao;
    }

    public String toString() {
        return pass.getId() + " " + direcao.toString().toLowerCase();
    }
}
