package MoodleAgiota;

public class Operation{
    private static int nextOpId = 0;
    private int id;
    private String name;
    private Label label;
    private int value;

    public Operation( String name, Label label, int value ) {
        this.id = Operation.nextOpId++;
        this.name = name;
        this.label =label;
        this.value = value;
    }
    //Getters
    public String getName() {
        return name;
    }
    public Label getLabel() {
        return label;
    }
    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("id:" + id + " " + label + ":" + name + " " + value);
    }
}