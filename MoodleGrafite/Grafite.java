package MoodleGrafite;

import java.text.DecimalFormat;
import java.util.Scanner;

class Lead {
    //1. Fiz tudo e passou em todos os testes
    //2. Fiz junto com o professor durante a aula
    //3. Aprendi o conceito de agregação
    //4. Demorei em torno de 1 hora, e ficou com uns erros, porem resolvi
    private float thickness;
    private String hardness;
    private int size;

    public Lead(float thickness, String hardness, int size) {
        this.thickness = thickness;
        this.hardness = hardness;
        this.size = size;
    }

    public float getThickness() {
        return this.thickness;
    }

    public String getHardness() {
        return this.hardness;
    }

    public int getSize() {
        return this.size;
    }
    public void setSize(int size) {
        this.size = size; // ou setSize( size );
    }

    public int usagePerSheet() {
        if(hardness.equals("HB"))
            return 1;
        else if(hardness.equals("2B"))
            return 2;
        else if(hardness.equals("4B"))
            return 4;
        else
            return 6;
    }

    public String toString() {
        DecimalFormat form = new DecimalFormat("0.0");
        return form.format(thickness) + ":" + hardness + ":" + size;
    }
}


class Pencil {
    private float thickness;
    private Lead tip;

    public Pencil(float thickness) {
        this.thickness = thickness;
        this.tip = null;
    }

    public float getThickness() {
        return this.thickness;
    }

    public void setThickness(float value) {
        return;
    }

    public boolean hasGrafite() {
        if(this.tip != null) {
            System.out.println("fail: ja existe grafite");
            return true;
        }
        return false;
    }

    public boolean insert(Lead grafite) {
        if(hasGrafite() ) {
            return false;
        }
        if(grafite.getThickness() != this.getThickness()){
            System.out.println("fail: calibre incompativel");
            return false;
        }
        this.tip = grafite;
        return true;
    }

    public Lead remove() {
        if(this.tip == null) {
            System.out.println("fail: nao existe grafite");
            return null;
        }
        Lead graf = this.tip;
        this.tip = null;
        return graf;
    }

    public void writePage() {
        if(this.tip == null) {
            System.out.println("fail: nao existe grafite");
            return;
        }
        if(this.tip.getSize() <= 10){
            System.out.println("fail: tamanho insuficiente");
            return;
        }
        if(this.tip.getSize() - 10 < this.tip.usagePerSheet()){
            System.out.println("fail: folha incompleta");
            this.tip.setSize(10);
            return;
        }
        this.tip.setSize(this.tip.getSize() - this.tip.usagePerSheet() );
    }

    public String toString() {
        String saida = "calibre: " + thickness + ", grafite: ";
        if (tip != null)
            saida += "[" + tip + "]";
        else
            saida += "null";
        return saida;
    }
}

public class Grafite {
    public static void main(String[] args) {
        Pencil pencil = new Pencil(0.5f);

        while (true) {
            String line = input();
            String[] argsL = line.split(" ");
            write('$' + line);

            if      ("end".equals(argsL[0])   ) { break;                                                                    }
            else if ("init".equals(argsL[0])  ) { pencil = new Pencil(number(argsL[1]));                                       }
            else if ("insert".equals(argsL[0])) { pencil.insert(new Lead(number(argsL[1]), argsL[2], (int) number(argsL[3]))); }
            else if ("remove".equals(argsL[0])) { pencil.remove();                                                             }
            else if ("write".equals(argsL[0]) ) { pencil.writePage();                                                          }
            else if ("show".equals(argsL[0])  ) { write(pencil.toString());                                                               }
        }
    }

    static Scanner scanner = new Scanner(System.in);

    public static String input()           { return scanner.nextLine();    }
    public static void write(String value) { System.out.println(value);    }
    public static float number(String str) { return Float.parseFloat(str); }
}