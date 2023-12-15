package MoodleCirculo;

class Ponto {
    float x;
    float y;

    void leitura() {
        x = Solver.in.nextFloat();
        y = Solver.in.nextFloat();
    }

    float distancia(Ponto ponto) {
        float cH = this.x - ponto.x;
        float cV = this.y - ponto.y;
        float dist = (float) Math.sqrt(cH * cH + cV * cV);
        return dist;
    }

    boolean estaDentro(Circulo circ) {
        if (this.distancia(circ.centro) <= circ.raio) {
            return true;
        } else {
            return false;
        }
    }

    void println(String str) {
        System.out.println(str);
    }
}
