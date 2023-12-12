package MoodleCirculo;

import java.util.Scanner;
import java.lang.Math;

class Ponto {
    float x;
    float y;

    void leitura() {
        x = Solver.in.nextFloat();
        y = Solver.in.nextFloat();
    }

    float distancia( Ponto ponto ) {
        float cH = this.x - ponto.x;
        float cV = this.y - ponto.y;
        float dist = (float) Math.sqrt( cH*cH + cV*cV );
        return dist;
    }

    boolean estaDentro( Circulo circ ) {
        if ( this.distancia( circ.centro ) <= circ.raio ) {
            return true;
        } else {
            return false;
        }
    }

    void println( String str ) { System.out.println( str ); }
}

class Circulo {
    Ponto centro;
    float raio;

    void ler() {
        centro = new Ponto();
        centro.x = Solver.in.nextFloat();
        centro.y = Solver.in.nextFloat();
        raio = Solver.in.nextFloat();
    }

    boolean contem( Ponto ponto ) {
        if ( this.centro.distancia( ponto ) <= this.raio ) {
            return true;
        } else {
            return false;
        }
    }

    int quantosDentro( Ponto[] vetor ) {
        int cont = 0;
        for ( Ponto ponto : vetor ) {
            if ( this.contem( ponto ) ) {
                cont++;
            }
        }
        return cont;
    }

    void println( String str ) { System.out.println( str ); }
}

class Solver {
    public static void main(String[] arg) {
        int n = in.nextInt();
        Ponto vetor[] = new Ponto[n];
        for (int i=0; i<vetor.length; i++) {
            vetor[i] = new Ponto();
            vetor[i].leitura();
        }
        Circulo circ = new Circulo();
        circ.ler();

        int cont = circ.quantosDentro( vetor );
        System.out.printf( "%d", cont );
    }

    static Scanner in = new Scanner(System.in);
    static String input() { return in.nextLine(); }
    static void println( String str ) { System.out.println( str ); }
}
