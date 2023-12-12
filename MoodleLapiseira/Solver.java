package MoodleLapiseira;
import java.text.DecimalFormat;
import java.util.*;


class Lead{
    private float thickness;
    private String hardness;
    private int size;

    public Lead(float thickness, String hardness, int size){
                this.thickness = thickness;
                this.hardness = hardness;
                this.size = size;
    }
    public int usagePerSheet(){
        if(this.hardness.equals("HB")) return 1;
        else if(this.hardness.equals("2B")) return 2;
        else if(this.hardness.equals("4B")) return 4;
        else return 6;
    }

    //Getters
    public float getThickness() {
        return thickness;
    }
    public String getHardness() {
        return hardness;
    }
    public int getSize() {
        return size;
    }
    //Set
    public void setSize(int size) {
        this.size = size;
    }

    public String toString() {
        DecimalFormat form = new DecimalFormat("0.0");
        return form.format(thickness) + ":" + hardness + ":" + size;
    }
}

class Pencil{
    private float thickness;
    private Lead tip;
    private ArrayList<Lead> barrel;

    public Pencil(float thickness){
        barrel = new ArrayList<>();
        this.thickness = thickness;
        this.tip = null;
    }

    public boolean insert(Lead lead){
        if(this.thickness == lead.getThickness()){
            this.barrel.add(lead);
            return true;
        }else{
            System.out.println("fail: calibre incompatível");
            return false;
        }
    }

    public Lead remove(){
        if(this.tip != null){
            Lead grafiteRemovido = this.tip;
            this.tip = null;
            return grafiteRemovido;
        }else{
            System.out.println("fail: não existe grafite na ponta");
            return null;
        }
    }

    public boolean pull(){
        if(this.barrel.isEmpty()){
            System.out.println("fail: nao existe grafite no tambor");
            return false;
        }if(this.tip != null){
            System.out.println("fail: ja existe grafite no bico");
            return false;
        }else {
            this.tip = this.barrel.remove(0);
            return true;
        }
    }
    public void writePage(){
            if(this.tip == null){
                System.out.println("fail: nao existe grafite no bico");
                return;
            }if(this.tip.getSize() == 10){
                System.out.println("fail: tamanho insuficiente");
                return;
            }
        int aux = this.tip.getSize() - this.tip.usagePerSheet();
        if(aux >= 10) {
            this.tip.setSize(aux);
        } else {
            this.tip.setSize(10);
            System.out.println("fail: folha incompleta");
        }
    }
    public String toString() {
        String saida = "calibre: " + thickness + ", bico: ";
        if (this.tip != null)
            saida += "[" + this.tip + "]";
        else
            saida += "[]";
        saida += ", tambor: {";
        for (Lead g : barrel)
            saida += "[" + g + "]";
        return saida + "}";
    }
}

class Solver{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Pencil lapiseira = new Pencil(0.5f);
        while(true) {
            String line = scanner.nextLine();
            System.out.println("$" + line);
            String ui[] = line.split(" ");
            if(ui[0].equals("end")) {
                break;
            } else if(ui[0].equals("help")) {
                System.out.println("init _calibre; insert _calibre _dureza _tamanho; remove; writePage _folhas");
            } else if(ui[0].equals("init")) { //thickness
                lapiseira = new Pencil(Float.parseFloat(ui[1]));
            } else if(ui[0].equals("insert")) {//thickness hardness size
                float thickness = Float.parseFloat(ui[1]);
                String hardness  = ui[2];
                int size = Integer.parseInt(ui[3]);
                lapiseira.insert(new Lead(thickness, hardness, size));
            } else if(ui[0].equals("remove")) {
                lapiseira.remove();
            } else if(ui[0].equals("show")) {
                System.out.println(lapiseira);
            } else if (ui[0].equals("write")) {
                lapiseira.writePage();
            } else if (ui[0].equals("pull")) {
                lapiseira.pull();
            }  else {
                System.out.println("fail: comando invalido");
            }
        }
        scanner.close();
    }
}