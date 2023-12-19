package MoodleLapiseira;

import java.util.ArrayList;

class Pencil {
    private float thickness;
    private Lead tip;
    private ArrayList<Lead> barrel;

    public Pencil(float thickness) {
        barrel = new ArrayList<>();
        this.thickness = thickness;
        this.tip = null;
    }

    public boolean insert(Lead lead) {
        if (this.thickness == lead.getThickness()) {
            this.barrel.add(lead);
            return true;
        } else {
            System.out.println("fail: calibre incompatível");
            return false;
        }
    }

    public Lead remove() {
        if (this.tip != null) {
            Lead grafiteRemovido = this.tip;
            this.tip = null;
            return grafiteRemovido;
        } else {
            System.out.println("fail: não existe grafite na ponta");
            return null;
        }
    }

    public boolean pull() {
        if (this.barrel.isEmpty()) {
            System.out.println("fail: nao existe grafite no tambor");
            return false;
        }
        if (this.tip != null) {
            System.out.println("fail: ja existe grafite no bico");
            return false;
        } else {
            this.tip = this.barrel.remove(0);
            return true;
        }
    }

    public void writePage() {
        if (this.tip == null) {
            System.out.println("fail: nao existe grafite no bico");
            return;
        }
        if (this.tip.getSize() == 10) {
            System.out.println("fail: tamanho insuficiente");
            return;
        }
        int aux = this.tip.getSize() - this.tip.usagePerSheet();
        if (aux >= 10) {
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
