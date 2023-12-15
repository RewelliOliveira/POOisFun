package MoodleCirculo;

class Circulo {
    Ponto centro;
    float raio;

    void ler() {
        centro = new Ponto();
        centro.x = Solver.in.nextFloat();
        centro.y = Solver.in.nextFloat();
        raio = Solver.in.nextFloat();
    }

    boolean contem(Ponto ponto) {
        if (this.centro.distancia(ponto) <= this.raio) {
            return true;
        } else {
            return false;
        }
    }

    int quantosDentro(Ponto[] vetor) {
        int cont = 0;
        for (Ponto ponto : vetor) {
            if (this.contem(ponto)) {
                cont++;
            }
        }
        return cont;
    }

    void println(String str) {
        System.out.println(str);
    }
}
