package MoodleCirculo;

import java.util.Scanner;
import java.lang.Math;

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
