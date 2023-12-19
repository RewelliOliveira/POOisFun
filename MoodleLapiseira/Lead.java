package MoodleLapiseira;

import java.text.DecimalFormat;

class Lead {
    private float thickness;
    private String hardness;
    private int size;

    public Lead(float thickness, String hardness, int size) {
        this.thickness = thickness;
        this.hardness = hardness;
        this.size = size;
    }

    public int usagePerSheet() {
        if (this.hardness.equals("HB")) return 1;
        else if (this.hardness.equals("2B")) return 2;
        else if (this.hardness.equals("4B")) return 4;
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
